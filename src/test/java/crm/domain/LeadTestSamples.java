package crm.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LeadTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Lead getLeadSample1() {
        return new Lead().id("id1").company("company1").first_name("first_name1").last_name("last_name1").email("email1").phone(1L);
    }

    public static Lead getLeadSample2() {
        return new Lead().id("id2").company("company2").first_name("first_name2").last_name("last_name2").email("email2").phone(2L);
    }

    public static Lead getLeadRandomSampleGenerator() {
        return new Lead()
            .id(UUID.randomUUID().toString())
            .company(UUID.randomUUID().toString())
            .first_name(UUID.randomUUID().toString())
            .last_name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(longCount.incrementAndGet());
    }
}
