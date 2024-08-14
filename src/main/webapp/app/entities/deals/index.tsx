import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Deals from './deals';
import DealsDetail from './deals-detail';
import DealsUpdate from './deals-update';
import DealsDeleteDialog from './deals-delete-dialog';

const DealsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Deals />} />
    <Route path="new" element={<DealsUpdate />} />
    <Route path=":id">
      <Route index element={<DealsDetail />} />
      <Route path="edit" element={<DealsUpdate />} />
      <Route path="delete" element={<DealsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DealsRoutes;
