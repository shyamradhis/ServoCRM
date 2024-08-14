package crm.domain;

import java.io.Serializable;
import java.time.Duration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Task.
 */
@Document(collection = "task")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("task_owner")
    private String task_owner;

    @Field("subject")
    private String subject;

    @Field("due_date")
    private String due_date;

    @Field("contact")
    private String contact;

    @Field("account")
    private String account;

    @Field("status")
    private String status;

    @Field("priority")
    private String priority;

    @Field("reminder")
    private Duration reminder;

    @Field("repeat")
    private Duration repeat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Task id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_owner() {
        return this.task_owner;
    }

    public Task task_owner(String task_owner) {
        this.setTask_owner(task_owner);
        return this;
    }

    public void setTask_owner(String task_owner) {
        this.task_owner = task_owner;
    }

    public String getSubject() {
        return this.subject;
    }

    public Task subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDue_date() {
        return this.due_date;
    }

    public Task due_date(String due_date) {
        this.setDue_date(due_date);
        return this;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getContact() {
        return this.contact;
    }

    public Task contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAccount() {
        return this.account;
    }

    public Task account(String account) {
        this.setAccount(account);
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return this.status;
    }

    public Task status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return this.priority;
    }

    public Task priority(String priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Duration getReminder() {
        return this.reminder;
    }

    public Task reminder(Duration reminder) {
        this.setReminder(reminder);
        return this;
    }

    public void setReminder(Duration reminder) {
        this.reminder = reminder;
    }

    public Duration getRepeat() {
        return this.repeat;
    }

    public Task repeat(Duration repeat) {
        this.setRepeat(repeat);
        return this;
    }

    public void setRepeat(Duration repeat) {
        this.repeat = repeat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return getId() != null && getId().equals(((Task) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", task_owner='" + getTask_owner() + "'" +
            ", subject='" + getSubject() + "'" +
            ", due_date='" + getDue_date() + "'" +
            ", contact='" + getContact() + "'" +
            ", account='" + getAccount() + "'" +
            ", status='" + getStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", reminder='" + getReminder() + "'" +
            ", repeat='" + getRepeat() + "'" +
            "}";
    }
}
