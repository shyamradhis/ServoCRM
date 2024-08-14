package crm.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Meeting.
 */
@Document(collection = "meeting")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @NotNull(message = "must not be null")
    @Field("from")
    private ZonedDateTime from;

    @Field("to")
    private ZonedDateTime to;

    @NotNull(message = "must not be null")
    @Field("related_to")
    private String related_to;

    @Field("host")
    private String host;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Meeting id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Meeting title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getFrom() {
        return this.from;
    }

    public Meeting from(ZonedDateTime from) {
        this.setFrom(from);
        return this;
    }

    public void setFrom(ZonedDateTime from) {
        this.from = from;
    }

    public ZonedDateTime getTo() {
        return this.to;
    }

    public Meeting to(ZonedDateTime to) {
        this.setTo(to);
        return this;
    }

    public void setTo(ZonedDateTime to) {
        this.to = to;
    }

    public String getRelated_to() {
        return this.related_to;
    }

    public Meeting related_to(String related_to) {
        this.setRelated_to(related_to);
        return this;
    }

    public void setRelated_to(String related_to) {
        this.related_to = related_to;
    }

    public String getHost() {
        return this.host;
    }

    public Meeting host(String host) {
        this.setHost(host);
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meeting)) {
            return false;
        }
        return getId() != null && getId().equals(((Meeting) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meeting{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", related_to='" + getRelated_to() + "'" +
            ", host='" + getHost() + "'" +
            "}";
    }
}
