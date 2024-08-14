import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Lead from './lead';
import Contacts from './contacts';
import Accounts from './accounts';
import Deals from './deals';
import Task from './task';
import Meeting from './meeting';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="lead/*" element={<Lead />} />
        <Route path="contacts/*" element={<Contacts />} />
        <Route path="accounts/*" element={<Accounts />} />
        <Route path="deals/*" element={<Deals />} />
        <Route path="task/*" element={<Task />} />
        <Route path="meeting/*" element={<Meeting />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
