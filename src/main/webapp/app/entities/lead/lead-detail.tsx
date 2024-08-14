import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lead.reducer';

export const LeadDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leadEntity = useAppSelector(state => state.lead.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leadDetailsHeading">Lead</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leadEntity.id}</dd>
          <dt>
            <span id="company">Company</span>
          </dt>
          <dd>{leadEntity.company}</dd>
          <dt>
            <span id="first_name">First Name</span>
          </dt>
          <dd>{leadEntity.first_name}</dd>
          <dt>
            <span id="last_name">Last Name</span>
          </dt>
          <dd>{leadEntity.last_name}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{leadEntity.email}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{leadEntity.phone}</dd>
        </dl>
        <Button tag={Link} to="/lead" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lead/${leadEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeadDetail;
