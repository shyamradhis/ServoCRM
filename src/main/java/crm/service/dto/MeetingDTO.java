package crm.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link crm.domain.Meeting} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MeetingDTO implements Serializable {

    private String id;

    private String title;

    @NotNull(message = "must not be null")
    private ZonedDateTime from;

    private ZonedDateTime to;

    @NotNull(message = "must not be null")
    private String related_to;

    private String host;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getFrom() {
        return from;
    }

    public void setFrom(ZonedDateTime from) {
        this.from = from;
    }

    public ZonedDateTime getTo() {
        return to;
    }

    public void setTo(ZonedDateTime to) {
        this.to = to;
    }

    public String getRelated_to() {
        return related_to;
    }

    public void setRelated_to(String related_to) {
        this.related_to = related_to;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeetingDTO)) {
            return false;
        }

        MeetingDTO meetingDTO = (MeetingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, meetingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeetingDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", related_to='" + getRelated_to() + "'" +
            ", host='" + getHost() + "'" +
            "}";
    }
}
