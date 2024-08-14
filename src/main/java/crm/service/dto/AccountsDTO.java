package crm.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link crm.domain.Accounts} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountsDTO implements Serializable {

    private String id;

    @NotNull(message = "must not be null")
    private String account_name;

    @NotNull(message = "must not be null")
    private Long phone;

    @NotNull(message = "must not be null")
    private String website;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountsDTO)) {
            return false;
        }

        AccountsDTO accountsDTO = (AccountsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, accountsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountsDTO{" +
            "id='" + getId() + "'" +
            ", account_name='" + getAccount_name() + "'" +
            ", phone=" + getPhone() +
            ", website='" + getWebsite() + "'" +
            "}";
    }
}
