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

describe('Deals e2e test', () => {
  const dealsPageUrl = '/deals';
  const dealsPageUrlPattern = new RegExp('/deals(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const dealsSample = { deal_name: 'lasting under where', closing_date: '2024-08-07T03:25:11.679Z' };

  let deals;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/deals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/deals').as('postEntityRequest');
    cy.intercept('DELETE', '/api/deals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (deals) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/deals/${deals.id}`,
      }).then(() => {
        deals = undefined;
      });
    }
  });

  it('Deals menu should load Deals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('deals');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Deals').should('exist');
    cy.url().should('match', dealsPageUrlPattern);
  });

  describe('Deals page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(dealsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Deals page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/deals/new$'));
        cy.getEntityCreateUpdateHeading('Deals');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dealsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/deals',
          body: dealsSample,
        }).then(({ body }) => {
          deals = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/deals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/deals?page=0&size=20>; rel="last",<http://localhost/api/deals?page=0&size=20>; rel="first"',
              },
              body: [deals],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(dealsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Deals page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('deals');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dealsPageUrlPattern);
      });

      it('edit button click should load edit Deals page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Deals');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dealsPageUrlPattern);
      });

      it('edit button click should load edit Deals page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Deals');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dealsPageUrlPattern);
      });

      it('last delete button click should delete instance of Deals', () => {
        cy.intercept('GET', '/api/deals/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('deals').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dealsPageUrlPattern);

        deals = undefined;
      });
    });
  });

  describe('new Deals page', () => {
    beforeEach(() => {
      cy.visit(`${dealsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Deals');
    });

    it('should create an instance of Deals', () => {
      cy.get(`[data-cy="amount"]`).type('miserably mindless');
      cy.get(`[data-cy="amount"]`).should('have.value', 'miserably mindless');

      cy.get(`[data-cy="deal_name"]`).type('baggy hmph');
      cy.get(`[data-cy="deal_name"]`).should('have.value', 'baggy hmph');

      cy.get(`[data-cy="closing_date"]`).type('2024-08-06T17:42');
      cy.get(`[data-cy="closing_date"]`).blur();
      cy.get(`[data-cy="closing_date"]`).should('have.value', '2024-08-06T17:42');

      cy.get(`[data-cy="account_name"]`).type('Money Market Account');
      cy.get(`[data-cy="account_name"]`).should('have.value', 'Money Market Account');

      cy.get(`[data-cy="stage"]`).type('however yippee');
      cy.get(`[data-cy="stage"]`).should('have.value', 'however yippee');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        deals = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', dealsPageUrlPattern);
    });
  });
});
