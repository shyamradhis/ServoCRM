import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Nav, NavItem, NavLink } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import './Lead.scss';
import { getEntities } from './lead.reducer';

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

export const Lead = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const leadList = useAppSelector(state => state.lead.entities);
  const loading = useAppSelector(state => state.lead.loading);
  const totalItems = useAppSelector(state => state.lead.totalItems);

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
    <div className="lead-page">
      <Sidebar />
      <div className="lead-content">
        <h3 id="lead-heading" data-cy="LeadHeading">
          Leads
          <div className="d-flex justify-content-end">
            <Button className="me-2" color="" onClick={handleSyncList} disabled={loading}>
              <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
            </Button>
            <Link to="/lead/new" className="btn btn-primary jh-create-entity" color="" id="jh-create-entity" data-cy="entityCreateButton">
              <FontAwesomeIcon icon="plus" />
              &nbsp; Create a new Lead
            </Link>
          </div>
        </h3>
        <div className="lead-container">
          {leadList && leadList.length > 0
            ? leadList.map((lead, i) => (
                <div key={`entity-${i}`} className="lead-box" data-cy="entityTable">
                  <div className="lead-box-header">{lead.company}</div>
                  <div className="lead-box-content">
                    <p>
                      <strong>ID:</strong> {lead.id}
                    </p>
                    <p>
                      <strong>First Name:</strong> {lead.first_name}
                    </p>
                    <p>
                      <strong>Last Name:</strong> {lead.last_name}
                    </p>
                    <p>
                      <strong>Email:</strong> {lead.email}
                    </p>
                    <p>
                      <strong>Phone:</strong> {lead.phone}
                    </p>
                  </div>
                  <div className="lead-box-actions">
                    <Button tag={Link} to={`/lead/${lead.id}`} color="" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                    </Button>
                    {/* <Button
                    tag={Link}
                    to={`/lead/${lead.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                    color="primary"
                    size="sm"
                    data-cy="entityEditButton"
                  >
                    <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                  </Button> */}
                    <Button
                      onClick={() =>
                        (window.location.href = `/lead/${lead.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                      }
                      color="danger"
                      size="sm"
                      data-cy="entityDeleteButton"
                    >
                      <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                    </Button>
                  </div>
                </div>
              ))
            : !loading && <div className="alert alert-warning">No Leads found</div>}
        </div>
        {totalItems ? (
          <div className={leadList && leadList.length > 0 ? '' : 'd-none'}>
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

export default Lead;
