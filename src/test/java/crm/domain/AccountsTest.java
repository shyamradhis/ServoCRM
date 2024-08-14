package crm.domain;

import static crm.domain.AccountsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accounts.class);
        Accounts accounts1 = getAccountsSample1();
        Accounts accounts2 = new Accounts();
        assertThat(accounts1).isNotEqualTo(accounts2);

        accounts2.setId(accounts1.getId());
        assertThat(accounts1).isEqualTo(accounts2);

        accounts2 = getAccountsSample2();
        assertThat(accounts1).isNotEqualTo(accounts2);
    }
}
