import lead from 'app/entities/lead/lead.reducer';
import contacts from 'app/entities/contacts/contacts.reducer';
import accounts from 'app/entities/accounts/accounts.reducer';
import deals from 'app/entities/deals/deals.reducer';
import task from 'app/entities/task/task.reducer';
import meeting from 'app/entities/meeting/meeting.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  lead,
  contacts,
  accounts,
  deals,
  task,
  meeting,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
