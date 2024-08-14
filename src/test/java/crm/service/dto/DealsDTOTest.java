package crm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealsDTO.class);
        DealsDTO dealsDTO1 = new DealsDTO();
        dealsDTO1.setId("id1");
        DealsDTO dealsDTO2 = new DealsDTO();
        assertThat(dealsDTO1).isNotEqualTo(dealsDTO2);
        dealsDTO2.setId(dealsDTO1.getId());
        assertThat(dealsDTO1).isEqualTo(dealsDTO2);
        dealsDTO2.setId("id2");
        assertThat(dealsDTO1).isNotEqualTo(dealsDTO2);
        dealsDTO1.setId(null);
        assertThat(dealsDTO1).isNotEqualTo(dealsDTO2);
    }
}
