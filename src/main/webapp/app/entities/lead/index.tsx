import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Lead from './lead';
import LeadDetail from './lead-detail';
import LeadUpdate from './lead-update';
import LeadDeleteDialog from './lead-delete-dialog';

const LeadRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Lead />} />
    <Route path="new" element={<LeadUpdate />} />
    <Route path=":id">
      <Route index element={<LeadDetail />} />
      <Route path="edit" element={<LeadUpdate />} />
      <Route path="delete" element={<LeadDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeadRoutes;
