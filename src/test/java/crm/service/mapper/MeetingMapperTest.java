package crm.service.mapper;

import static crm.domain.MeetingAsserts.*;
import static crm.domain.MeetingTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeetingMapperTest {

    private MeetingMapper meetingMapper;

    @BeforeEach
    void setUp() {
        meetingMapper = new MeetingMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMeetingSample1();
        var actual = meetingMapper.toEntity(meetingMapper.toDto(expected));
        assertMeetingAllPropertiesEquals(expected, actual);
    }
}
