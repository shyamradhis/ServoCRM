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

describe('Lead e2e test', () => {
  const leadPageUrl = '/lead';
  const leadPageUrlPattern = new RegExp('/lead(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const leadSample = { company: 'explain amalgamate before', first_name: 'Janice', email: 'Ayana80@gmail.com' };

  let lead;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/leads+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/leads').as('postEntityRequest');
    cy.intercept('DELETE', '/api/leads/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (lead) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/leads/${lead.id}`,
      }).then(() => {
        lead = undefined;
      });
    }
  });

  it('Leads menu should load Leads page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('lead');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Lead').should('exist');
    cy.url().should('match', leadPageUrlPattern);
  });

  describe('Lead page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(leadPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Lead page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/lead/new$'));
        cy.getEntityCreateUpdateHeading('Lead');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', leadPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/leads',
          body: leadSample,
        }).then(({ body }) => {
          lead = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/leads+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/leads?page=0&size=20>; rel="last",<http://localhost/api/leads?page=0&size=20>; rel="first"',
              },
              body: [lead],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(leadPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Lead page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('lead');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', leadPageUrlPattern);
      });

      it('edit button click should load edit Lead page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Lead');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', leadPageUrlPattern);
      });

      it('edit button click should load edit Lead page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Lead');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', leadPageUrlPattern);
      });

      it('last delete button click should delete instance of Lead', () => {
        cy.intercept('GET', '/api/leads/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('lead').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', leadPageUrlPattern);

        lead = undefined;
      });
    });
  });

  describe('new Lead page', () => {
    beforeEach(() => {
      cy.visit(`${leadPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Lead');
    });

    it('should create an instance of Lead', () => {
      cy.get(`[data-cy="company"]`).type('shrilly');
      cy.get(`[data-cy="company"]`).should('have.value', 'shrilly');

      cy.get(`[data-cy="first_name"]`).type('Dixie');
      cy.get(`[data-cy="first_name"]`).should('have.value', 'Dixie');

      cy.get(`[data-cy="last_name"]`).type('Wyman');
      cy.get(`[data-cy="last_name"]`).should('have.value', 'Wyman');

      cy.get(`[data-cy="email"]`).type('Laverna_Jacobs24@gmail.com');
      cy.get(`[data-cy="email"]`).should('have.value', 'Laverna_Jacobs24@gmail.com');

      cy.get(`[data-cy="phone"]`).type('20030');
      cy.get(`[data-cy="phone"]`).should('have.value', '20030');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        lead = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', leadPageUrlPattern);
    });
  });
});
