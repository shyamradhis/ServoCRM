package crm.repository;

import crm.domain.Contacts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Contacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactsRepository extends ReactiveMongoRepository<Contacts, String> {
    Flux<Contacts> findAllBy(Pageable pageable);
}
