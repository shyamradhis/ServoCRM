package crm.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Deals.
 */
@Document(collection = "deals")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Deals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("amount")
    private String amount;

    @NotNull(message = "must not be null")
    @Field("deal_name")
    private String deal_name;

    @NotNull(message = "must not be null")
    @Field("closing_date")
    private ZonedDateTime closing_date;

    @Field("account_name")
    private String account_name;

    @Field("stage")
    private String stage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Deals id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return this.amount;
    }

    public Deals amount(String amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeal_name() {
        return this.deal_name;
    }

    public Deals deal_name(String deal_name) {
        this.setDeal_name(deal_name);
        return this;
    }

    public void setDeal_name(String deal_name) {
        this.deal_name = deal_name;
    }

    public ZonedDateTime getClosing_date() {
        return this.closing_date;
    }

    public Deals closing_date(ZonedDateTime closing_date) {
        this.setClosing_date(closing_date);
        return this;
    }

    public void setClosing_date(ZonedDateTime closing_date) {
        this.closing_date = closing_date;
    }

    public String getAccount_name() {
        return this.account_name;
    }

    public Deals account_name(String account_name) {
        this.setAccount_name(account_name);
        return this;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getStage() {
        return this.stage;
    }

    public Deals stage(String stage) {
        this.setStage(stage);
        return this;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deals)) {
            return false;
        }
        return getId() != null && getId().equals(((Deals) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deals{" +
            "id=" + getId() +
            ", amount='" + getAmount() + "'" +
            ", deal_name='" + getDeal_name() + "'" +
            ", closing_date='" + getClosing_date() + "'" +
            ", account_name='" + getAccount_name() + "'" +
            ", stage='" + getStage() + "'" +
            "}";
    }
}
