package crm.service;

import crm.repository.LeadRepository;
import crm.service.dto.LeadDTO;
import crm.service.mapper.LeadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link crm.domain.Lead}.
 */
@Service
public class LeadService {

    private static final Logger log = LoggerFactory.getLogger(LeadService.class);

    private final LeadRepository leadRepository;

    private final LeadMapper leadMapper;

    public LeadService(LeadRepository leadRepository, LeadMapper leadMapper) {
        this.leadRepository = leadRepository;
        this.leadMapper = leadMapper;
    }

    /**
     * Save a lead.
     *
     * @param leadDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<LeadDTO> save(LeadDTO leadDTO) {
        log.debug("Request to save Lead : {}", leadDTO);
        return leadRepository.save(leadMapper.toEntity(leadDTO)).map(leadMapper::toDto);
    }

    /**
     * Update a lead.
     *
     * @param leadDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<LeadDTO> update(LeadDTO leadDTO) {
        log.debug("Request to update Lead : {}", leadDTO);
        return leadRepository.save(leadMapper.toEntity(leadDTO)).map(leadMapper::toDto);
    }

    /**
     * Partially update a lead.
     *
     * @param leadDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<LeadDTO> partialUpdate(LeadDTO leadDTO) {
        log.debug("Request to partially update Lead : {}", leadDTO);

        return leadRepository
            .findById(leadDTO.getId())
            .map(existingLead -> {
                leadMapper.partialUpdate(existingLead, leadDTO);

                return existingLead;
            })
            .flatMap(leadRepository::save)
            .map(leadMapper::toDto);
    }

    /**
     * Get all the leads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<LeadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Leads");
        return leadRepository.findAllBy(pageable).map(leadMapper::toDto);
    }

    /**
     * Returns the number of leads available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return leadRepository.count();
    }

    /**
     * Get one lead by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<LeadDTO> findOne(String id) {
        log.debug("Request to get Lead : {}", id);
        return leadRepository.findById(id).map(leadMapper::toDto);
    }

    /**
     * Delete the lead by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Lead : {}", id);
        return leadRepository.deleteById(id);
    }
}
