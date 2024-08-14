package crm.domain;

import java.util.UUID;

public class MeetingTestSamples {

    public static Meeting getMeetingSample1() {
        return new Meeting().id("id1").title("title1").related_to("related_to1").host("host1");
    }

    public static Meeting getMeetingSample2() {
        return new Meeting().id("id2").title("title2").related_to("related_to2").host("host2");
    }

    public static Meeting getMeetingRandomSampleGenerator() {
        return new Meeting()
            .id(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .related_to(UUID.randomUUID().toString())
            .host(UUID.randomUUID().toString());
    }
}
