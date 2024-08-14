package crm.repository;

import crm.domain.Accounts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Accounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountsRepository extends ReactiveMongoRepository<Accounts, String> {
    Flux<Accounts> findAllBy(Pageable pageable);
}
