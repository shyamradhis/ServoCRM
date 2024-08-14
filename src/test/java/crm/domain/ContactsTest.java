package crm.domain;

import static crm.domain.ContactsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contacts.class);
        Contacts contacts1 = getContactsSample1();
        Contacts contacts2 = new Contacts();
        assertThat(contacts1).isNotEqualTo(contacts2);

        contacts2.setId(contacts1.getId());
        assertThat(contacts1).isEqualTo(contacts2);

        contacts2 = getContactsSample2();
        assertThat(contacts1).isNotEqualTo(contacts2);
    }
}
