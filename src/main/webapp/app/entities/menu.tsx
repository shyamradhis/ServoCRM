import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/lead">
        Lead
      </MenuItem>
      <MenuItem icon="asterisk" to="/contacts">
        Contacts
      </MenuItem>
      <MenuItem icon="asterisk" to="/accounts">
        Accounts
      </MenuItem>
      <MenuItem icon="asterisk" to="/deals">
        Deals
      </MenuItem>
      <MenuItem icon="asterisk" to="/task">
        Task
      </MenuItem>
      <MenuItem icon="asterisk" to="/meeting">
        Meeting
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
