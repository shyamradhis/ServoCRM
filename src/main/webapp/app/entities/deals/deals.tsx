import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';

import { Button, Table, Nav, NavItem, NavLink } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import './Deals.scss';
import { getEntities } from './deals.reducer';

const Sidebar = () => {
  return (
    <div className="sidebar">
      <h5></h5>
      <Nav vertical>
        <NavItem>
          <NavLink href="/Dashboard">Dashboard</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/lead">Leads</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/Contacts">Contacts</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/Accounts">Accounts</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/Deals">Deals</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/task">Task</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/Meeting">Meeting</NavLink>
        </NavItem>
      </Nav>
    </div>
  );
};

export const Deals = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const dealsList = useAppSelector(state => state.deals.entities);
  const loading = useAppSelector(state => state.deals.loading);
  const totalItems = useAppSelector(state => state.deals.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div className="deals-page">
      <Sidebar />
      <div className="deals-content">
        <h2 id="deals-heading" data-cy="DealsHeading">
          Deals
          <div className="d-flex justify-content-end">
            <Button className="me-2" color="" onClick={handleSyncList} disabled={loading}>
              <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
            </Button>
            <Link to="/deals/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
              <FontAwesomeIcon icon="plus" />
              &nbsp; Create a new Deals
            </Link>
          </div>
        </h2>
        <div className="table-responsive">
          {dealsList && dealsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    Amount
                  </th>
                  <th className="hand" onClick={sort('deal_name')}>
                    Deal Name
                  </th>
                  <th className="hand" onClick={sort('closing_date')}>
                    Closing Date
                  </th>
                  <th className="hand" onClick={sort('account_name')}>
                    Account Name
                  </th>
                  <th className="hand" onClick={sort('stage')}>
                    Stage
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {dealsList.map((deals, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/deals/${deals.id}`} color="link" size="sm">
                        {deals.id}
                      </Button>
                    </td>
                    <td>{deals.amount}</td>
                    <td>{deals.deal_name}</td>
                    <td>{deals.closing_date ? <TextFormat type="date" value={deals.closing_date} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{deals.account_name}</td>
                    <td>{deals.stage}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/deals/${deals.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/deals/${deals.id}/edit?page={paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button
                          onClick={() =>
                            (window.location.href = `/deals/${deals.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                          }
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Deals found</div>
          )}
        </div>
        {totalItems ? (
          <div className={dealsList && dealsList.length > 0 ? '' : 'd-none'}>
            <div className="justify-content-center d-flex"></div>
            <div className="justify-content-center d-flex"></div>
          </div>
        ) : (
          ''
        )}
      </div>
    </div>
  );
};

export default Deals;
