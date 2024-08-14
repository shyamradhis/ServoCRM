package crm.repository;

import crm.domain.Meeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Meeting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingRepository extends ReactiveMongoRepository<Meeting, String> {
    Flux<Meeting> findAllBy(Pageable pageable);
}
