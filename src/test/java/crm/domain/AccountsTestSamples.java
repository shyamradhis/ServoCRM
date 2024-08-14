package crm.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AccountsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Accounts getAccountsSample1() {
        return new Accounts().id("id1").account_name("account_name1").phone(1L).website("website1");
    }

    public static Accounts getAccountsSample2() {
        return new Accounts().id("id2").account_name("account_name2").phone(2L).website("website2");
    }

    public static Accounts getAccountsRandomSampleGenerator() {
        return new Accounts()
            .id(UUID.randomUUID().toString())
            .account_name(UUID.randomUUID().toString())
            .phone(longCount.incrementAndGet())
            .website(UUID.randomUUID().toString());
    }
}
