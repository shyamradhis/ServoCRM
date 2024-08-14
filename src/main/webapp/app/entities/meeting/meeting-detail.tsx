import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './meeting.reducer';

export const MeetingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const meetingEntity = useAppSelector(state => state.meeting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="meetingDetailsHeading">Meeting</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{meetingEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{meetingEntity.title}</dd>
          <dt>
            <span id="from">From</span>
          </dt>
          <dd>{meetingEntity.from ? <TextFormat value={meetingEntity.from} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="to">To</span>
          </dt>
          <dd>{meetingEntity.to ? <TextFormat value={meetingEntity.to} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="related_to">Related To</span>
          </dt>
          <dd>{meetingEntity.related_to}</dd>
          <dt>
            <span id="host">Host</span>
          </dt>
          <dd>{meetingEntity.host}</dd>
        </dl>
        <Button tag={Link} to="/meeting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meeting/${meetingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MeetingDetail;
