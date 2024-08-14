package crm.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Accounts.
 */
@Document(collection = "accounts")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Field("account_name")
    private String account_name;

    @NotNull(message = "must not be null")
    @Field("phone")
    private Long phone;

    @NotNull(message = "must not be null")
    @Field("website")
    private String website;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Accounts id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount_name() {
        return this.account_name;
    }

    public Accounts account_name(String account_name) {
        this.setAccount_name(account_name);
        return this;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public Long getPhone() {
        return this.phone;
    }

    public Accounts phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return this.website;
    }

    public Accounts website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accounts)) {
            return false;
        }
        return getId() != null && getId().equals(((Accounts) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accounts{" +
            "id=" + getId() +
            ", account_name='" + getAccount_name() + "'" +
            ", phone=" + getPhone() +
            ", website='" + getWebsite() + "'" +
            "}";
    }
}
