package crm.service;

import crm.repository.MeetingRepository;
import crm.service.dto.MeetingDTO;
import crm.service.mapper.MeetingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link crm.domain.Meeting}.
 */
@Service
public class MeetingService {

    private static final Logger log = LoggerFactory.getLogger(MeetingService.class);

    private final MeetingRepository meetingRepository;

    private final MeetingMapper meetingMapper;

    public MeetingService(MeetingRepository meetingRepository, MeetingMapper meetingMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
    }

    /**
     * Save a meeting.
     *
     * @param meetingDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<MeetingDTO> save(MeetingDTO meetingDTO) {
        log.debug("Request to save Meeting : {}", meetingDTO);
        return meetingRepository.save(meetingMapper.toEntity(meetingDTO)).map(meetingMapper::toDto);
    }

    /**
     * Update a meeting.
     *
     * @param meetingDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<MeetingDTO> update(MeetingDTO meetingDTO) {
        log.debug("Request to update Meeting : {}", meetingDTO);
        return meetingRepository.save(meetingMapper.toEntity(meetingDTO)).map(meetingMapper::toDto);
    }

    /**
     * Partially update a meeting.
     *
     * @param meetingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<MeetingDTO> partialUpdate(MeetingDTO meetingDTO) {
        log.debug("Request to partially update Meeting : {}", meetingDTO);

        return meetingRepository
            .findById(meetingDTO.getId())
            .map(existingMeeting -> {
                meetingMapper.partialUpdate(existingMeeting, meetingDTO);

                return existingMeeting;
            })
            .flatMap(meetingRepository::save)
            .map(meetingMapper::toDto);
    }

    /**
     * Get all the meetings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<MeetingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Meetings");
        return meetingRepository.findAllBy(pageable).map(meetingMapper::toDto);
    }

    /**
     * Returns the number of meetings available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return meetingRepository.count();
    }

    /**
     * Get one meeting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<MeetingDTO> findOne(String id) {
        log.debug("Request to get Meeting : {}", id);
        return meetingRepository.findById(id).map(meetingMapper::toDto);
    }

    /**
     * Delete the meeting by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Meeting : {}", id);
        return meetingRepository.deleteById(id);
    }
}
