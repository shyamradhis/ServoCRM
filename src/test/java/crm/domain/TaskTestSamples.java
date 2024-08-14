package crm.domain;

import java.util.UUID;

public class TaskTestSamples {

    public static Task getTaskSample1() {
        return new Task()
            .id("id1")
            .task_owner("task_owner1")
            .subject("subject1")
            .due_date("due_date1")
            .contact("contact1")
            .account("account1")
            .status("status1")
            .priority("priority1");
    }

    public static Task getTaskSample2() {
        return new Task()
            .id("id2")
            .task_owner("task_owner2")
            .subject("subject2")
            .due_date("due_date2")
            .contact("contact2")
            .account("account2")
            .status("status2")
            .priority("priority2");
    }

    public static Task getTaskRandomSampleGenerator() {
        return new Task()
            .id(UUID.randomUUID().toString())
            .task_owner(UUID.randomUUID().toString())
            .subject(UUID.randomUUID().toString())
            .due_date(UUID.randomUUID().toString())
            .contact(UUID.randomUUID().toString())
            .account(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .priority(UUID.randomUUID().toString());
    }
}
