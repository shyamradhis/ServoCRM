package crm.service;

import crm.repository.ContactsRepository;
import crm.service.dto.ContactsDTO;
import crm.service.mapper.ContactsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link crm.domain.Contacts}.
 */
@Service
public class ContactsService {

    private static final Logger log = LoggerFactory.getLogger(ContactsService.class);

    private final ContactsRepository contactsRepository;

    private final ContactsMapper contactsMapper;

    public ContactsService(ContactsRepository contactsRepository, ContactsMapper contactsMapper) {
        this.contactsRepository = contactsRepository;
        this.contactsMapper = contactsMapper;
    }

    /**
     * Save a contacts.
     *
     * @param contactsDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ContactsDTO> save(ContactsDTO contactsDTO) {
        log.debug("Request to save Contacts : {}", contactsDTO);
        return contactsRepository.save(contactsMapper.toEntity(contactsDTO)).map(contactsMapper::toDto);
    }

    /**
     * Update a contacts.
     *
     * @param contactsDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ContactsDTO> update(ContactsDTO contactsDTO) {
        log.debug("Request to update Contacts : {}", contactsDTO);
        return contactsRepository.save(contactsMapper.toEntity(contactsDTO)).map(contactsMapper::toDto);
    }

    /**
     * Partially update a contacts.
     *
     * @param contactsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ContactsDTO> partialUpdate(ContactsDTO contactsDTO) {
        log.debug("Request to partially update Contacts : {}", contactsDTO);

        return contactsRepository
            .findById(contactsDTO.getId())
            .map(existingContacts -> {
                contactsMapper.partialUpdate(existingContacts, contactsDTO);

                return existingContacts;
            })
            .flatMap(contactsRepository::save)
            .map(contactsMapper::toDto);
    }

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<ContactsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactsRepository.findAllBy(pageable).map(contactsMapper::toDto);
    }

    /**
     * Returns the number of contacts available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return contactsRepository.count();
    }

    /**
     * Get one contacts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<ContactsDTO> findOne(String id) {
        log.debug("Request to get Contacts : {}", id);
        return contactsRepository.findById(id).map(contactsMapper::toDto);
    }

    /**
     * Delete the contacts by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Contacts : {}", id);
        return contactsRepository.deleteById(id);
    }
}
