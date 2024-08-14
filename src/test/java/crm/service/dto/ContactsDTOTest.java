package crm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactsDTO.class);
        ContactsDTO contactsDTO1 = new ContactsDTO();
        contactsDTO1.setId("id1");
        ContactsDTO contactsDTO2 = new ContactsDTO();
        assertThat(contactsDTO1).isNotEqualTo(contactsDTO2);
        contactsDTO2.setId(contactsDTO1.getId());
        assertThat(contactsDTO1).isEqualTo(contactsDTO2);
        contactsDTO2.setId("id2");
        assertThat(contactsDTO1).isNotEqualTo(contactsDTO2);
        contactsDTO1.setId(null);
        assertThat(contactsDTO1).isNotEqualTo(contactsDTO2);
    }
}
