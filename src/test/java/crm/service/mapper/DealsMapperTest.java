package crm.service.mapper;

import static crm.domain.DealsAsserts.*;
import static crm.domain.DealsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DealsMapperTest {

    private DealsMapper dealsMapper;

    @BeforeEach
    void setUp() {
        dealsMapper = new DealsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDealsSample1();
        var actual = dealsMapper.toEntity(dealsMapper.toDto(expected));
        assertDealsAllPropertiesEquals(expected, actual);
    }
}
