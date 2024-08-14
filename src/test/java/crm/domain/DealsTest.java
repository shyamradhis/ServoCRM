package crm.domain;

import static crm.domain.DealsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deals.class);
        Deals deals1 = getDealsSample1();
        Deals deals2 = new Deals();
        assertThat(deals1).isNotEqualTo(deals2);

        deals2.setId(deals1.getId());
        assertThat(deals1).isEqualTo(deals2);

        deals2 = getDealsSample2();
        assertThat(deals1).isNotEqualTo(deals2);
    }
}
