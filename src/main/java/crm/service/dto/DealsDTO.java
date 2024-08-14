package crm.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link crm.domain.Deals} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealsDTO implements Serializable {

    private String id;

    private String amount;

    @NotNull(message = "must not be null")
    private String deal_name;

    @NotNull(message = "must not be null")
    private ZonedDateTime closing_date;

    private String account_name;

    private String stage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeal_name() {
        return deal_name;
    }

    public void setDeal_name(String deal_name) {
        this.deal_name = deal_name;
    }

    public ZonedDateTime getClosing_date() {
        return closing_date;
    }

    public void setClosing_date(ZonedDateTime closing_date) {
        this.closing_date = closing_date;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealsDTO)) {
            return false;
        }

        DealsDTO dealsDTO = (DealsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dealsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealsDTO{" +
            "id='" + getId() + "'" +
            ", amount='" + getAmount() + "'" +
            ", deal_name='" + getDeal_name() + "'" +
            ", closing_date='" + getClosing_date() + "'" +
            ", account_name='" + getAccount_name() + "'" +
            ", stage='" + getStage() + "'" +
            "}";
    }
}
