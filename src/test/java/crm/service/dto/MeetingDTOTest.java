package crm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeetingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeetingDTO.class);
        MeetingDTO meetingDTO1 = new MeetingDTO();
        meetingDTO1.setId("id1");
        MeetingDTO meetingDTO2 = new MeetingDTO();
        assertThat(meetingDTO1).isNotEqualTo(meetingDTO2);
        meetingDTO2.setId(meetingDTO1.getId());
        assertThat(meetingDTO1).isEqualTo(meetingDTO2);
        meetingDTO2.setId("id2");
        assertThat(meetingDTO1).isNotEqualTo(meetingDTO2);
        meetingDTO1.setId(null);
        assertThat(meetingDTO1).isNotEqualTo(meetingDTO2);
    }
}
