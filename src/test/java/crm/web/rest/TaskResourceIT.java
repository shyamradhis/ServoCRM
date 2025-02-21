package crm.web.rest;

import static crm.domain.TaskAsserts.*;
import static crm.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import crm.IntegrationTest;
import crm.domain.Task;
import crm.repository.TaskRepository;
import crm.service.dto.TaskDTO;
import crm.service.mapper.TaskMapper;
import java.time.Duration;
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
 * Integration tests for the {@link TaskResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TaskResourceIT {

    private static final String DEFAULT_TASK_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_TASK_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_DUE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DUE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final Duration DEFAULT_REMINDER = Duration.ofHours(6);
    private static final Duration UPDATED_REMINDER = Duration.ofHours(12);

    private static final Duration DEFAULT_REPEAT = Duration.ofHours(6);
    private static final Duration UPDATED_REPEAT = Duration.ofHours(12);

    private static final String ENTITY_API_URL = "/api/tasks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Task task;

    private Task insertedTask;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createEntity() {
        Task task = new Task()
            .task_owner(DEFAULT_TASK_OWNER)
            .subject(DEFAULT_SUBJECT)
            .due_date(DEFAULT_DUE_DATE)
            .contact(DEFAULT_CONTACT)
            .account(DEFAULT_ACCOUNT)
            .status(DEFAULT_STATUS)
            .priority(DEFAULT_PRIORITY)
            .reminder(DEFAULT_REMINDER)
            .repeat(DEFAULT_REPEAT);
        return task;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity() {
        Task task = new Task()
            .task_owner(UPDATED_TASK_OWNER)
            .subject(UPDATED_SUBJECT)
            .due_date(UPDATED_DUE_DATE)
            .contact(UPDATED_CONTACT)
            .account(UPDATED_ACCOUNT)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .reminder(UPDATED_REMINDER)
            .repeat(UPDATED_REPEAT);
        return task;
    }

    @BeforeEach
    public void initTest() {
        task = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTask != null) {
            taskRepository.delete(insertedTask).block();
            insertedTask = null;
        }
    }

    @Test
    void createTask() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);
        var returnedTaskDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(taskDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(TaskDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Task in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTask = taskMapper.toEntity(returnedTaskDTO);
        assertTaskUpdatableFieldsEquals(returnedTask, getPersistedTask(returnedTask));

        insertedTask = returnedTask;
    }

    @Test
    void createTaskWithExistingId() throws Exception {
        // Create the Task with an existing ID
        task.setId("existing_id");
        TaskDTO taskDTO = taskMapper.toDto(task);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(taskDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Task in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTasks() {
        // Initialize the database
        insertedTask = taskRepository.save(task).block();

        // Get all the taskList
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
            .value(hasItem(task.getId()))
            .jsonPath("$.[*].task_owner")
            .value(hasItem(DEFAULT_TASK_OWNER))
            .jsonPath("$.[*].subject")
            .value(hasItem(DEFAULT_SUBJECT))
            .jsonPath("$.[*].due_date")
            .value(hasItem(DEFAULT_DUE_DATE))
            .jsonPath("$.[*].contact")
            .value(hasItem(DEFAULT_CONTACT))
            .jsonPath("$.[*].account")
            .value(hasItem(DEFAULT_ACCOUNT))
            .jsonPath("$.[*].status")
            .value(hasItem(DEFAULT_STATUS))
            .jsonPath("$.[*].priority")
            .value(hasItem(DEFAULT_PRIORITY))
            .jsonPath("$.[*].reminder")
            .value(hasItem(DEFAULT_REMINDER.toString()))
            .jsonPath("$.[*].repeat")
            .value(hasItem(DEFAULT_REPEAT.toString()));
    }

    @Test
    void getTask() {
        // Initialize the database
        insertedTask = taskRepository.save(task).block();

        // Get the task
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, task.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(task.getId()))
            .jsonPath("$.task_owner")
            .value(is(DEFAULT_TASK_OWNER))
            .jsonPath("$.subject")
            .value(is(DEFAULT_SUBJECT))
            .jsonPath("$.due_date")
            .value(is(DEFAULT_DUE_DATE))
            .jsonPath("$.contact")
            .value(is(DEFAULT_CONTACT))
            .jsonPath("$.account")
            .value(is(DEFAULT_ACCOUNT))
            .jsonPath("$.status")
            .value(is(DEFAULT_STATUS))
            .jsonPath("$.priority")
            .value(is(DEFAULT_PRIORITY))
            .jsonPath("$.reminder")
            .value(is(DEFAULT_REMINDER.toString()))
            .jsonPath("$.repeat")
            .value(is(DEFAULT_REPEAT.toString()));
    }

    @Test
    void getNonExistingTask() {
        // Get the task
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTask() throws Exception {
        // Initialize the database
        insertedTask = taskRepository.save(task).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).block();
        updatedTask
            .task_owner(UPDATED_TASK_OWNER)
            .subject(UPDATED_SUBJECT)
            .due_date(UPDATED_DUE_DATE)
            .contact(UPDATED_CONTACT)
            .account(UPDATED_ACCOUNT)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .reminder(UPDATED_REMINDER)
            .repeat(UPDATED_REPEAT);
        TaskDTO taskDTO = taskMapper.toDto(updatedTask);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, taskDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(taskDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Task in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTaskToMatchAllProperties(updatedTask);
    }

    @Test
    void putNonExistingTask() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        task.setId(UUID.randomUUID().toString());

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, taskDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(taskDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Task in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTask() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        task.setId(UUID.randomUUID().toString());

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(taskDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Task in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTask() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        task.setId(UUID.randomUUID().toString());

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(taskDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Task in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTaskWithPatch() throws Exception {
        // Initialize the database
        insertedTask = taskRepository.save(task).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the task using partial update
        Task partialUpdatedTask = new Task();
        partialUpdatedTask.setId(task.getId());

        partialUpdatedTask.due_date(UPDATED_DUE_DATE).status(UPDATED_STATUS).priority(UPDATED_PRIORITY).reminder(UPDATED_REMINDER);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTask.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTask))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Task in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaskUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTask, task), getPersistedTask(task));
    }

    @Test
    void fullUpdateTaskWithPatch() throws Exception {
        // Initialize the database
        insertedTask = taskRepository.save(task).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the task using partial update
        Task partialUpdatedTask = new Task();
        partialUpdatedTask.setId(task.getId());

        partialUpdatedTask
            .task_owner(UPDATED_TASK_OWNER)
            .subject(UPDATED_SUBJECT)
            .due_date(UPDATED_DUE_DATE)
            .contact(UPDATED_CONTACT)
            .account(UPDATED_ACCOUNT)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .reminder(UPDATED_REMINDER)
            .repeat(UPDATED_REPEAT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTask.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTask))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Task in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaskUpdatableFieldsEquals(partialUpdatedTask, getPersistedTask(partialUpdatedTask));
    }

    @Test
    void patchNonExistingTask() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        task.setId(UUID.randomUUID().toString());

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, taskDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(taskDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Task in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTask() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        task.setId(UUID.randomUUID().toString());

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(taskDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Task in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTask() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        task.setId(UUID.randomUUID().toString());

        // Create the Task
        TaskDTO taskDTO = taskMapper.toDto(task);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(taskDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Task in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTask() {
        // Initialize the database
        insertedTask = taskRepository.save(task).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the task
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, task.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return taskRepository.count().block();
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

    protected Task getPersistedTask(Task task) {
        return taskRepository.findById(task.getId()).block();
    }

    protected void assertPersistedTaskToMatchAllProperties(Task expectedTask) {
        assertTaskAllPropertiesEquals(expectedTask, getPersistedTask(expectedTask));
    }

    protected void assertPersistedTaskToMatchUpdatableProperties(Task expectedTask) {
        assertTaskAllUpdatablePropertiesEquals(expectedTask, getPersistedTask(expectedTask));
    }
}
