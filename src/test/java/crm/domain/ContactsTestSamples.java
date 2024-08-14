package crm.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContactsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Contacts getContactsSample1() {
        return new Contacts()
            .id("id1")
            .first_name("first_name1")
            .last_name("last_name1")
            .account_name("account_name1")
            .email("email1")
            .phone(1L);
    }

    public static Contacts getContactsSample2() {
        return new Contacts()
            .id("id2")
            .first_name("first_name2")
            .last_name("last_name2")
            .account_name("account_name2")
            .email("email2")
            .phone(2L);
    }

    public static Contacts getContactsRandomSampleGenerator() {
        return new Contacts()
            .id(UUID.randomUUID().toString())
            .first_name(UUID.randomUUID().toString())
            .last_name(UUID.randomUUID().toString())
            .account_name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(longCount.incrementAndGet());
    }
}
