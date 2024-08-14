package crm.service;

import crm.repository.DealsRepository;
import crm.service.dto.DealsDTO;
import crm.service.mapper.DealsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link crm.domain.Deals}.
 */
@Service
public class DealsService {

    private static final Logger log = LoggerFactory.getLogger(DealsService.class);

    private final DealsRepository dealsRepository;

    private final DealsMapper dealsMapper;

    public DealsService(DealsRepository dealsRepository, DealsMapper dealsMapper) {
        this.dealsRepository = dealsRepository;
        this.dealsMapper = dealsMapper;
    }

    /**
     * Save a deals.
     *
     * @param dealsDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<DealsDTO> save(DealsDTO dealsDTO) {
        log.debug("Request to save Deals : {}", dealsDTO);
        return dealsRepository.save(dealsMapper.toEntity(dealsDTO)).map(dealsMapper::toDto);
    }

    /**
     * Update a deals.
     *
     * @param dealsDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<DealsDTO> update(DealsDTO dealsDTO) {
        log.debug("Request to update Deals : {}", dealsDTO);
        return dealsRepository.save(dealsMapper.toEntity(dealsDTO)).map(dealsMapper::toDto);
    }

    /**
     * Partially update a deals.
     *
     * @param dealsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<DealsDTO> partialUpdate(DealsDTO dealsDTO) {
        log.debug("Request to partially update Deals : {}", dealsDTO);

        return dealsRepository
            .findById(dealsDTO.getId())
            .map(existingDeals -> {
                dealsMapper.partialUpdate(existingDeals, dealsDTO);

                return existingDeals;
            })
            .flatMap(dealsRepository::save)
            .map(dealsMapper::toDto);
    }

    /**
     * Get all the deals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<DealsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Deals");
        return dealsRepository.findAllBy(pageable).map(dealsMapper::toDto);
    }

    /**
     * Returns the number of deals available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return dealsRepository.count();
    }

    /**
     * Get one deals by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<DealsDTO> findOne(String id) {
        log.debug("Request to get Deals : {}", id);
        return dealsRepository.findById(id).map(dealsMapper::toDto);
    }

    /**
     * Delete the deals by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Deals : {}", id);
        return dealsRepository.deleteById(id);
    }
}
