package crm.repository;

import crm.domain.Deals;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Deals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealsRepository extends ReactiveMongoRepository<Deals, String> {
    Flux<Deals> findAllBy(Pageable pageable);
}
