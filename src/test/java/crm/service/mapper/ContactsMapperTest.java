package crm.service.mapper;

import static crm.domain.ContactsAsserts.*;
import static crm.domain.ContactsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactsMapperTest {

    private ContactsMapper contactsMapper;

    @BeforeEach
    void setUp() {
        contactsMapper = new ContactsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContactsSample1();
        var actual = contactsMapper.toEntity(contactsMapper.toDto(expected));
        assertContactsAllPropertiesEquals(expected, actual);
    }
}
