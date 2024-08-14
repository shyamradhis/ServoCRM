import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Meeting e2e test', () => {
  const meetingPageUrl = '/meeting';
  const meetingPageUrlPattern = new RegExp('/meeting(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const meetingSample = { from: '2024-08-06T19:36:35.843Z', related_to: 'periodic omnivore clause' };

  let meeting;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/meetings+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/meetings').as('postEntityRequest');
    cy.intercept('DELETE', '/api/meetings/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (meeting) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/meetings/${meeting.id}`,
      }).then(() => {
        meeting = undefined;
      });
    }
  });

  it('Meetings menu should load Meetings page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('meeting');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Meeting').should('exist');
    cy.url().should('match', meetingPageUrlPattern);
  });

  describe('Meeting page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(meetingPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Meeting page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/meeting/new$'));
        cy.getEntityCreateUpdateHeading('Meeting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', meetingPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/meetings',
          body: meetingSample,
        }).then(({ body }) => {
          meeting = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/meetings+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/meetings?page=0&size=20>; rel="last",<http://localhost/api/meetings?page=0&size=20>; rel="first"',
              },
              body: [meeting],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(meetingPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Meeting page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('meeting');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', meetingPageUrlPattern);
      });

      it('edit button click should load edit Meeting page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Meeting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', meetingPageUrlPattern);
      });

      it('edit button click should load edit Meeting page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Meeting');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', meetingPageUrlPattern);
      });

      it('last delete button click should delete instance of Meeting', () => {
        cy.intercept('GET', '/api/meetings/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('meeting').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', meetingPageUrlPattern);

        meeting = undefined;
      });
    });
  });

  describe('new Meeting page', () => {
    beforeEach(() => {
      cy.visit(`${meetingPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Meeting');
    });

    it('should create an instance of Meeting', () => {
      cy.get(`[data-cy="title"]`).type('joshingly consequently');
      cy.get(`[data-cy="title"]`).should('have.value', 'joshingly consequently');

      cy.get(`[data-cy="from"]`).type('2024-08-06T11:20');
      cy.get(`[data-cy="from"]`).blur();
      cy.get(`[data-cy="from"]`).should('have.value', '2024-08-06T11:20');

      cy.get(`[data-cy="to"]`).type('2024-08-07T01:14');
      cy.get(`[data-cy="to"]`).blur();
      cy.get(`[data-cy="to"]`).should('have.value', '2024-08-07T01:14');

      cy.get(`[data-cy="related_to"]`).type('dime phooey');
      cy.get(`[data-cy="related_to"]`).should('have.value', 'dime phooey');

      cy.get(`[data-cy="host"]`).type('despite');
      cy.get(`[data-cy="host"]`).should('have.value', 'despite');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        meeting = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', meetingPageUrlPattern);
    });
  });
});
