import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { DurationFormat } from 'app/shared/DurationFormat';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './task.reducer';

export const TaskDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const taskEntity = useAppSelector(state => state.task.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taskDetailsHeading">Task</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{taskEntity.id}</dd>
          <dt>
            <span id="task_owner">Task Owner</span>
          </dt>
          <dd>{taskEntity.task_owner}</dd>
          <dt>
            <span id="subject">Subject</span>
          </dt>
          <dd>{taskEntity.subject}</dd>
          <dt>
            <span id="due_date">Due Date</span>
          </dt>
          <dd>{taskEntity.due_date}</dd>
          <dt>
            <span id="contact">Contact</span>
          </dt>
          <dd>{taskEntity.contact}</dd>
          <dt>
            <span id="account">Account</span>
          </dt>
          <dd>{taskEntity.account}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{taskEntity.status}</dd>
          <dt>
            <span id="priority">Priority</span>
          </dt>
          <dd>{taskEntity.priority}</dd>
          <dt>
            <span id="reminder">Reminder</span>
          </dt>
          <dd>
            {taskEntity.reminder ? <DurationFormat value={taskEntity.reminder} /> : null} ({taskEntity.reminder})
          </dd>
          <dt>
            <span id="repeat">Repeat</span>
          </dt>
          <dd>
            {taskEntity.repeat ? <DurationFormat value={taskEntity.repeat} /> : null} ({taskEntity.repeat})
          </dd>
        </dl>
        <Button tag={Link} to="/task" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task/${taskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaskDetail;
