package crm.web.rest;

import static crm.domain.MeetingAsserts.*;
import static crm.web.rest.TestUtil.createUpdateProxyForBean;
import static crm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import crm.IntegrationTest;
import crm.domain.Meeting;
import crm.repository.MeetingRepository;
import crm.service.dto.MeetingDTO;
import crm.service.mapper.MeetingMapper;
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
 * Integration tests for the {@link MeetingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class MeetingResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_RELATED_TO = "AAAAAAAAAA";
    private static final String UPDATED_RELATED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/meetings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Meeting meeting;

    private Meeting insertedMeeting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meeting createEntity() {
        Meeting meeting = new Meeting()
            .title(DEFAULT_TITLE)
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .related_to(DEFAULT_RELATED_TO)
            .host(DEFAULT_HOST);
        return meeting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meeting createUpdatedEntity() {
        Meeting meeting = new Meeting()
            .title(UPDATED_TITLE)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .related_to(UPDATED_RELATED_TO)
            .host(UPDATED_HOST);
        return meeting;
    }

    @BeforeEach
    public void initTest() {
        meeting = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMeeting != null) {
            meetingRepository.delete(insertedMeeting).block();
            insertedMeeting = null;
        }
    }

    @Test
    void createMeeting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Meeting
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);
        var returnedMeetingDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(MeetingDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Meeting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMeeting = meetingMapper.toEntity(returnedMeetingDTO);
        assertMeetingUpdatableFieldsEquals(returnedMeeting, getPersistedMeeting(returnedMeeting));

        insertedMeeting = returnedMeeting;
    }

    @Test
    void createMeetingWithExistingId() throws Exception {
        // Create the Meeting with an existing ID
        meeting.setId("existing_id");
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkFromIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        meeting.setFrom(null);

        // Create the Meeting, which fails.
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkRelated_toIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        meeting.setRelated_to(null);

        // Create the Meeting, which fails.
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllMeetings() {
        // Initialize the database
        insertedMeeting = meetingRepository.save(meeting).block();

        // Get all the meetingList
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
            .value(hasItem(meeting.getId()))
            .jsonPath("$.[*].title")
            .value(hasItem(DEFAULT_TITLE))
            .jsonPath("$.[*].from")
            .value(hasItem(sameInstant(DEFAULT_FROM)))
            .jsonPath("$.[*].to")
            .value(hasItem(sameInstant(DEFAULT_TO)))
            .jsonPath("$.[*].related_to")
            .value(hasItem(DEFAULT_RELATED_TO))
            .jsonPath("$.[*].host")
            .value(hasItem(DEFAULT_HOST));
    }

    @Test
    void getMeeting() {
        // Initialize the database
        insertedMeeting = meetingRepository.save(meeting).block();

        // Get the meeting
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, meeting.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(meeting.getId()))
            .jsonPath("$.title")
            .value(is(DEFAULT_TITLE))
            .jsonPath("$.from")
            .value(is(sameInstant(DEFAULT_FROM)))
            .jsonPath("$.to")
            .value(is(sameInstant(DEFAULT_TO)))
            .jsonPath("$.related_to")
            .value(is(DEFAULT_RELATED_TO))
            .jsonPath("$.host")
            .value(is(DEFAULT_HOST));
    }

    @Test
    void getNonExistingMeeting() {
        // Get the meeting
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingMeeting() throws Exception {
        // Initialize the database
        insertedMeeting = meetingRepository.save(meeting).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meeting
        Meeting updatedMeeting = meetingRepository.findById(meeting.getId()).block();
        updatedMeeting.title(UPDATED_TITLE).from(UPDATED_FROM).to(UPDATED_TO).related_to(UPDATED_RELATED_TO).host(UPDATED_HOST);
        MeetingDTO meetingDTO = meetingMapper.toDto(updatedMeeting);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, meetingDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMeetingToMatchAllProperties(updatedMeeting);
    }

    @Test
    void putNonExistingMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(UUID.randomUUID().toString());

        // Create the Meeting
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, meetingDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(UUID.randomUUID().toString());

        // Create the Meeting
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(UUID.randomUUID().toString());

        // Create the Meeting
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMeetingWithPatch() throws Exception {
        // Initialize the database
        insertedMeeting = meetingRepository.save(meeting).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meeting using partial update
        Meeting partialUpdatedMeeting = new Meeting();
        partialUpdatedMeeting.setId(meeting.getId());

        partialUpdatedMeeting.to(UPDATED_TO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMeeting.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedMeeting))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Meeting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeetingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMeeting, meeting), getPersistedMeeting(meeting));
    }

    @Test
    void fullUpdateMeetingWithPatch() throws Exception {
        // Initialize the database
        insertedMeeting = meetingRepository.save(meeting).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meeting using partial update
        Meeting partialUpdatedMeeting = new Meeting();
        partialUpdatedMeeting.setId(meeting.getId());

        partialUpdatedMeeting.title(UPDATED_TITLE).from(UPDATED_FROM).to(UPDATED_TO).related_to(UPDATED_RELATED_TO).host(UPDATED_HOST);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMeeting.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedMeeting))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Meeting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeetingUpdatableFieldsEquals(partialUpdatedMeeting, getPersistedMeeting(partialUpdatedMeeting));
    }

    @Test
    void patchNonExistingMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(UUID.randomUUID().toString());

        // Create the Meeting
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, meetingDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(UUID.randomUUID().toString());

        // Create the Meeting
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMeeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meeting.setId(UUID.randomUUID().toString());

        // Create the Meeting
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(meetingDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Meeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMeeting() {
        // Initialize the database
        insertedMeeting = meetingRepository.save(meeting).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the meeting
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, meeting.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return meetingRepository.count().block();
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

    protected Meeting getPersistedMeeting(Meeting meeting) {
        return meetingRepository.findById(meeting.getId()).block();
    }

    protected void assertPersistedMeetingToMatchAllProperties(Meeting expectedMeeting) {
        assertMeetingAllPropertiesEquals(expectedMeeting, getPersistedMeeting(expectedMeeting));
    }

    protected void assertPersistedMeetingToMatchUpdatableProperties(Meeting expectedMeeting) {
        assertMeetingAllUpdatablePropertiesEquals(expectedMeeting, getPersistedMeeting(expectedMeeting));
    }
}
