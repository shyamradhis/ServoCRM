package crm.domain;

import static crm.domain.MeetingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeetingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meeting.class);
        Meeting meeting1 = getMeetingSample1();
        Meeting meeting2 = new Meeting();
        assertThat(meeting1).isNotEqualTo(meeting2);

        meeting2.setId(meeting1.getId());
        assertThat(meeting1).isEqualTo(meeting2);

        meeting2 = getMeetingSample2();
        assertThat(meeting1).isNotEqualTo(meeting2);
    }
}
