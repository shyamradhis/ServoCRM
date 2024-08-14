import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Contacts from './contacts';
import ContactsDetail from './contacts-detail';
import ContactsUpdate from './contacts-update';
import ContactsDeleteDialog from './contacts-delete-dialog';

const ContactsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Contacts />} />
    <Route path="new" element={<ContactsUpdate />} />
    <Route path=":id">
      <Route index element={<ContactsDetail />} />
      <Route path="edit" element={<ContactsUpdate />} />
      <Route path="delete" element={<ContactsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ContactsRoutes;
