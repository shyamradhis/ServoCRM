import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deals.reducer';

export const DealsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dealsEntity = useAppSelector(state => state.deals.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dealsDetailsHeading">Deals</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dealsEntity.id}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{dealsEntity.amount}</dd>
          <dt>
            <span id="deal_name">Deal Name</span>
          </dt>
          <dd>{dealsEntity.deal_name}</dd>
          <dt>
            <span id="closing_date">Closing Date</span>
          </dt>
          <dd>{dealsEntity.closing_date ? <TextFormat value={dealsEntity.closing_date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="account_name">Account Name</span>
          </dt>
          <dd>{dealsEntity.account_name}</dd>
          <dt>
            <span id="stage">Stage</span>
          </dt>
          <dd>{dealsEntity.stage}</dd>
        </dl>
        <Button tag={Link} to="/deals" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deals/${dealsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DealsDetail;
