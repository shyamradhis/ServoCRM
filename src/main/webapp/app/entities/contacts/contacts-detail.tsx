import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contacts.reducer';

export const ContactsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contactsEntity = useAppSelector(state => state.contacts.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactsDetailsHeading">Contacts</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{contactsEntity.id}</dd>
          <dt>
            <span id="first_name">First Name</span>
          </dt>
          <dd>{contactsEntity.first_name}</dd>
          <dt>
            <span id="last_name">Last Name</span>
          </dt>
          <dd>{contactsEntity.last_name}</dd>
          <dt>
            <span id="account_name">Account Name</span>
          </dt>
          <dd>{contactsEntity.account_name}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{contactsEntity.email}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{contactsEntity.phone}</dd>
        </dl>
        <Button tag={Link} to="/contacts" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contacts/${contactsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactsDetail;
