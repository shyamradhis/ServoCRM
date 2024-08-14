package crm.domain;

import java.util.UUID;

public class DealsTestSamples {

    public static Deals getDealsSample1() {
        return new Deals().id("id1").amount("amount1").deal_name("deal_name1").account_name("account_name1").stage("stage1");
    }

    public static Deals getDealsSample2() {
        return new Deals().id("id2").amount("amount2").deal_name("deal_name2").account_name("account_name2").stage("stage2");
    }

    public static Deals getDealsRandomSampleGenerator() {
        return new Deals()
            .id(UUID.randomUUID().toString())
            .amount(UUID.randomUUID().toString())
            .deal_name(UUID.randomUUID().toString())
            .account_name(UUID.randomUUID().toString())
            .stage(UUID.randomUUID().toString());
    }
}
