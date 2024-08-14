package crm.service;

import crm.repository.AccountsRepository;
import crm.service.dto.AccountsDTO;
import crm.service.mapper.AccountsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link crm.domain.Accounts}.
 */
@Service
public class AccountsService {

    private static final Logger log = LoggerFactory.getLogger(AccountsService.class);

    private final AccountsRepository accountsRepository;

    private final AccountsMapper accountsMapper;

    public AccountsService(AccountsRepository accountsRepository, AccountsMapper accountsMapper) {
        this.accountsRepository = accountsRepository;
        this.accountsMapper = accountsMapper;
    }

    /**
     * Save a accounts.
     *
     * @param accountsDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<AccountsDTO> save(AccountsDTO accountsDTO) {
        log.debug("Request to save Accounts : {}", accountsDTO);
        return accountsRepository.save(accountsMapper.toEntity(accountsDTO)).map(accountsMapper::toDto);
    }

    /**
     * Update a accounts.
     *
     * @param accountsDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<AccountsDTO> update(AccountsDTO accountsDTO) {
        log.debug("Request to update Accounts : {}", accountsDTO);
        return accountsRepository.save(accountsMapper.toEntity(accountsDTO)).map(accountsMapper::toDto);
    }

    /**
     * Partially update a accounts.
     *
     * @param accountsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<AccountsDTO> partialUpdate(AccountsDTO accountsDTO) {
        log.debug("Request to partially update Accounts : {}", accountsDTO);

        return accountsRepository
            .findById(accountsDTO.getId())
            .map(existingAccounts -> {
                accountsMapper.partialUpdate(existingAccounts, accountsDTO);

                return existingAccounts;
            })
            .flatMap(accountsRepository::save)
            .map(accountsMapper::toDto);
    }

    /**
     * Get all the accounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<AccountsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accounts");
        return accountsRepository.findAllBy(pageable).map(accountsMapper::toDto);
    }

    /**
     * Returns the number of accounts available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return accountsRepository.count();
    }

    /**
     * Get one accounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<AccountsDTO> findOne(String id) {
        log.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id).map(accountsMapper::toDto);
    }

    /**
     * Delete the accounts by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Accounts : {}", id);
        return accountsRepository.deleteById(id);
    }
}
