import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContacts } from 'app/shared/model/contacts.model';
import { getEntity, updateEntity, createEntity, reset } from './contacts.reducer';

export const ContactsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactsEntity = useAppSelector(state => state.contacts.entity);
  const loading = useAppSelector(state => state.contacts.loading);
  const updating = useAppSelector(state => state.contacts.updating);
  const updateSuccess = useAppSelector(state => state.contacts.updateSuccess);

  const handleClose = () => {
    navigate('/contacts' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.phone !== undefined && typeof values.phone !== 'number') {
      values.phone = Number(values.phone);
    }

    const entity = {
      ...contactsEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...contactsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmApp.contacts.home.createOrEditLabel" data-cy="ContactsCreateUpdateHeading">
            Create or edit a Contacts
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="contacts-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="First Name"
                id="contacts-first_name"
                name="first_name"
                data-cy="first_name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Last Name" id="contacts-last_name" name="last_name" data-cy="last_name" type="text" />
              <ValidatedField label="Account Name" id="contacts-account_name" name="account_name" data-cy="account_name" type="text" />
              <ValidatedField label="Email" id="contacts-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Phone" id="contacts-phone" name="phone" data-cy="phone" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/contacts" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ContactsUpdate;
