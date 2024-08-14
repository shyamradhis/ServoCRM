package crm.web.rest;

import static crm.domain.DealsAsserts.*;
import static crm.web.rest.TestUtil.createUpdateProxyForBean;
import static crm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import crm.IntegrationTest;
import crm.domain.Deals;
import crm.repository.DealsRepository;
import crm.service.dto.DealsDTO;
import crm.service.mapper.DealsMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link DealsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DealsResourceIT {

    private static final String DEFAULT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DEAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CLOSING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CLOSING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STAGE = "AAAAAAAAAA";
    private static final String UPDATED_STAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DealsRepository dealsRepository;

    @Autowired
    private DealsMapper dealsMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Deals deals;

    private Deals insertedDeals;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deals createEntity() {
        Deals deals = new Deals()
            .amount(DEFAULT_AMOUNT)
            .deal_name(DEFAULT_DEAL_NAME)
            .closing_date(DEFAULT_CLOSING_DATE)
            .account_name(DEFAULT_ACCOUNT_NAME)
            .stage(DEFAULT_STAGE);
        return deals;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deals createUpdatedEntity() {
        Deals deals = new Deals()
            .amount(UPDATED_AMOUNT)
            .deal_name(UPDATED_DEAL_NAME)
            .closing_date(UPDATED_CLOSING_DATE)
            .account_name(UPDATED_ACCOUNT_NAME)
            .stage(UPDATED_STAGE);
        return deals;
    }

    @BeforeEach
    public void initTest() {
        deals = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDeals != null) {
            dealsRepository.delete(insertedDeals).block();
            insertedDeals = null;
        }
    }

    @Test
    void createDeals() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Deals
        DealsDTO dealsDTO = dealsMapper.toDto(deals);
        var returnedDealsDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(DealsDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Deals in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDeals = dealsMapper.toEntity(returnedDealsDTO);
        assertDealsUpdatableFieldsEquals(returnedDeals, getPersistedDeals(returnedDeals));

        insertedDeals = returnedDeals;
    }

    @Test
    void createDealsWithExistingId() throws Exception {
        // Create the Deals with an existing ID
        deals.setId("existing_id");
        DealsDTO dealsDTO = dealsMapper.toDto(deals);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Deals in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkDeal_nameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        deals.setDeal_name(null);

        // Create the Deals, which fails.
        DealsDTO dealsDTO = dealsMapper.toDto(deals);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkClosing_dateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        deals.setClosing_date(null);

        // Create the Deals, which fails.
        DealsDTO dealsDTO = dealsMapper.toDto(deals);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllDeals() {
        // Initialize the database
        insertedDeals = dealsRepository.save(deals).block();

        // Get all the dealsList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(deals.getId()))
            .jsonPath("$.[*].amount")
            .value(hasItem(DEFAULT_AMOUNT))
            .jsonPath("$.[*].deal_name")
            .value(hasItem(DEFAULT_DEAL_NAME))
            .jsonPath("$.[*].closing_date")
            .value(hasItem(sameInstant(DEFAULT_CLOSING_DATE)))
            .jsonPath("$.[*].account_name")
            .value(hasItem(DEFAULT_ACCOUNT_NAME))
            .jsonPath("$.[*].stage")
            .value(hasItem(DEFAULT_STAGE));
    }

    @Test
    void getDeals() {
        // Initialize the database
        insertedDeals = dealsRepository.save(deals).block();

        // Get the deals
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, deals.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(deals.getId()))
            .jsonPath("$.amount")
            .value(is(DEFAULT_AMOUNT))
            .jsonPath("$.deal_name")
            .value(is(DEFAULT_DEAL_NAME))
            .jsonPath("$.closing_date")
            .value(is(sameInstant(DEFAULT_CLOSING_DATE)))
            .jsonPath("$.account_name")
            .value(is(DEFAULT_ACCOUNT_NAME))
            .jsonPath("$.stage")
            .value(is(DEFAULT_STAGE));
    }

    @Test
    void getNonExistingDeals() {
        // Get the deals
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDeals() throws Exception {
        // Initialize the database
        insertedDeals = dealsRepository.save(deals).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deals
        Deals updatedDeals = dealsRepository.findById(deals.getId()).block();
        updatedDeals
            .amount(UPDATED_AMOUNT)
            .deal_name(UPDATED_DEAL_NAME)
            .closing_date(UPDATED_CLOSING_DATE)
            .account_name(UPDATED_ACCOUNT_NAME)
            .stage(UPDATED_STAGE);
        DealsDTO dealsDTO = dealsMapper.toDto(updatedDeals);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, dealsDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Deals in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDealsToMatchAllProperties(updatedDeals);
    }

    @Test
    void putNonExistingDeals() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deals.setId(UUID.randomUUID().toString());

        // Create the Deals
        DealsDTO dealsDTO = dealsMapper.toDto(deals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, dealsDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Deals in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDeals() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deals.setId(UUID.randomUUID().toString());

        // Create the Deals
        DealsDTO dealsDTO = dealsMapper.toDto(deals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Deals in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDeals() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deals.setId(UUID.randomUUID().toString());

        // Create the Deals
        DealsDTO dealsDTO = dealsMapper.toDto(deals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Deals in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDealsWithPatch() throws Exception {
        // Initialize the database
        insertedDeals = dealsRepository.save(deals).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deals using partial update
        Deals partialUpdatedDeals = new Deals();
        partialUpdatedDeals.setId(deals.getId());

        partialUpdatedDeals.amount(UPDATED_AMOUNT).deal_name(UPDATED_DEAL_NAME).account_name(UPDATED_ACCOUNT_NAME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDeals.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDeals))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Deals in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDealsUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDeals, deals), getPersistedDeals(deals));
    }

    @Test
    void fullUpdateDealsWithPatch() throws Exception {
        // Initialize the database
        insertedDeals = dealsRepository.save(deals).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deals using partial update
        Deals partialUpdatedDeals = new Deals();
        partialUpdatedDeals.setId(deals.getId());

        partialUpdatedDeals
            .amount(UPDATED_AMOUNT)
            .deal_name(UPDATED_DEAL_NAME)
            .closing_date(UPDATED_CLOSING_DATE)
            .account_name(UPDATED_ACCOUNT_NAME)
            .stage(UPDATED_STAGE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDeals.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDeals))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Deals in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDealsUpdatableFieldsEquals(partialUpdatedDeals, getPersistedDeals(partialUpdatedDeals));
    }

    @Test
    void patchNonExistingDeals() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deals.setId(UUID.randomUUID().toString());

        // Create the Deals
        DealsDTO dealsDTO = dealsMapper.toDto(deals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, dealsDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Deals in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDeals() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deals.setId(UUID.randomUUID().toString());

        // Create the Deals
        DealsDTO dealsDTO = dealsMapper.toDto(deals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Deals in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDeals() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deals.setId(UUID.randomUUID().toString());

        // Create the Deals
        DealsDTO dealsDTO = dealsMapper.toDto(deals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(dealsDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Deals in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDeals() {
        // Initialize the database
        insertedDeals = dealsRepository.save(deals).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the deals
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, deals.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dealsRepository.count().block();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Deals getPersistedDeals(Deals deals) {
        return dealsRepository.findById(deals.getId()).block();
    }

    protected void assertPersistedDealsToMatchAllProperties(Deals expectedDeals) {
        assertDealsAllPropertiesEquals(expectedDeals, getPersistedDeals(expectedDeals));
    }

    protected void assertPersistedDealsToMatchUpdatableProperties(Deals expectedDeals) {
        assertDealsAllUpdatablePropertiesEquals(expectedDeals, getPersistedDeals(expectedDeals));
    }
}
