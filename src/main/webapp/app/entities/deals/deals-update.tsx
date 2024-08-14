import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDeals } from 'app/shared/model/deals.model';
import { getEntity, updateEntity, createEntity, reset } from './deals.reducer';

export const DealsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dealsEntity = useAppSelector(state => state.deals.entity);
  const loading = useAppSelector(state => state.deals.loading);
  const updating = useAppSelector(state => state.deals.updating);
  const updateSuccess = useAppSelector(state => state.deals.updateSuccess);

  const handleClose = () => {
    navigate('/deals' + location.search);
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
    values.closing_date = convertDateTimeToServer(values.closing_date);

    const entity = {
      ...dealsEntity,
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
      ? {
          closing_date: displayDefaultDateTime(),
        }
      : {
          ...dealsEntity,
          closing_date: convertDateTimeFromServer(dealsEntity.closing_date),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmApp.deals.home.createOrEditLabel" data-cy="DealsCreateUpdateHeading">
            Create or edit a Deals
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="deals-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Amount" id="deals-amount" name="amount" data-cy="amount" type="text" />
              <ValidatedField
                label="Deal Name"
                id="deals-deal_name"
                name="deal_name"
                data-cy="deal_name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Closing Date"
                id="deals-closing_date"
                name="closing_date"
                data-cy="closing_date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Account Name" id="deals-account_name" name="account_name" data-cy="account_name" type="text" />
              <ValidatedField label="Stage" id="deals-stage" name="stage" data-cy="stage" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deals" replace color="info">
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

export default DealsUpdate;
