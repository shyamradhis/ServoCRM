package crm.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaskAllPropertiesEquals(Task expected, Task actual) {
        assertTaskAutoGeneratedPropertiesEquals(expected, actual);
        assertTaskAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaskAllUpdatablePropertiesEquals(Task expected, Task actual) {
        assertTaskUpdatableFieldsEquals(expected, actual);
        assertTaskUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaskAutoGeneratedPropertiesEquals(Task expected, Task actual) {
        assertThat(expected)
            .as("Verify Task auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaskUpdatableFieldsEquals(Task expected, Task actual) {
        assertThat(expected)
            .as("Verify Task relevant properties")
            .satisfies(e -> assertThat(e.getTask_owner()).as("check task_owner").isEqualTo(actual.getTask_owner()))
            .satisfies(e -> assertThat(e.getSubject()).as("check subject").isEqualTo(actual.getSubject()))
            .satisfies(e -> assertThat(e.getDue_date()).as("check due_date").isEqualTo(actual.getDue_date()))
            .satisfies(e -> assertThat(e.getContact()).as("check contact").isEqualTo(actual.getContact()))
            .satisfies(e -> assertThat(e.getAccount()).as("check account").isEqualTo(actual.getAccount()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getPriority()).as("check priority").isEqualTo(actual.getPriority()))
            .satisfies(e -> assertThat(e.getReminder()).as("check reminder").isEqualTo(actual.getReminder()))
            .satisfies(e -> assertThat(e.getRepeat()).as("check repeat").isEqualTo(actual.getRepeat()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaskUpdatableRelationshipsEquals(Task expected, Task actual) {}
}
