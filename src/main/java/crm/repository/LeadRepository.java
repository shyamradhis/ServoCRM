package crm.repository;

import crm.domain.Lead;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Lead entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadRepository extends ReactiveMongoRepository<Lead, String> {
    Flux<Lead> findAllBy(Pageable pageable);
}
