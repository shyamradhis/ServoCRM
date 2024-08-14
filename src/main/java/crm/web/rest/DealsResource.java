package crm.web.rest;

import crm.repository.DealsRepository;
import crm.service.DealsService;
import crm.service.dto.DealsDTO;
import crm.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ForwardedHeaderUtils;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link crm.domain.Deals}.
 */
@RestController
@RequestMapping("/api/deals")
public class DealsResource {

    private static final Logger log = LoggerFactory.getLogger(DealsResource.class);

    private static final String ENTITY_NAME = "deals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealsService dealsService;

    private final DealsRepository dealsRepository;

    public DealsResource(DealsService dealsService, DealsRepository dealsRepository) {
        this.dealsService = dealsService;
        this.dealsRepository = dealsRepository;
    }

    /**
     * {@code POST  /deals} : Create a new deals.
     *
     * @param dealsDTO the dealsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealsDTO, or with status {@code 400 (Bad Request)} if the deals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<DealsDTO>> createDeals(@Valid @RequestBody DealsDTO dealsDTO) throws URISyntaxException {
        log.debug("REST request to save Deals : {}", dealsDTO);
        if (dealsDTO.getId() != null) {
            throw new BadRequestAlertException("A new deals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return dealsService
            .save(dealsDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/deals/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /deals/:id} : Updates an existing deals.
     *
     * @param id the id of the dealsDTO to save.
     * @param dealsDTO the dealsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealsDTO,
     * or with status {@code 400 (Bad Request)} if the dealsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<DealsDTO>> updateDeals(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody DealsDTO dealsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Deals : {}, {}", id, dealsDTO);
        if (dealsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dealsRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return dealsService
                    .update(dealsDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(
                        result ->
                            ResponseEntity.ok()
                                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId()))
                                .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /deals/:id} : Partial updates given fields of an existing deals, field will ignore if it is null
     *
     * @param id the id of the dealsDTO to save.
     * @param dealsDTO the dealsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealsDTO,
     * or with status {@code 400 (Bad Request)} if the dealsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dealsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<DealsDTO>> partialUpdateDeals(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody DealsDTO dealsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Deals partially : {}, {}", id, dealsDTO);
        if (dealsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return dealsRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DealsDTO> result = dealsService.partialUpdate(dealsDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(
                        res ->
                            ResponseEntity.ok()
                                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, res.getId()))
                                .body(res)
                    );
            });
    }

    /**
     * {@code GET  /deals} : get all the deals.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deals in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<DealsDTO>>> getAllDeals(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Deals");
        return dealsService
            .countAll()
            .zipWith(dealsService.findAll(pageable).collectList())
            .map(
                countWithEntities ->
                    ResponseEntity.ok()
                        .headers(
                            PaginationUtil.generatePaginationHttpHeaders(
                                ForwardedHeaderUtils.adaptFromForwardedHeaders(request.getURI(), request.getHeaders()),
                                new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                            )
                        )
                        .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /deals/:id} : get the "id" deals.
     *
     * @param id the id of the dealsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<DealsDTO>> getDeals(@PathVariable("id") String id) {
        log.debug("REST request to get Deals : {}", id);
        Mono<DealsDTO> dealsDTO = dealsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealsDTO);
    }

    /**
     * {@code DELETE  /deals/:id} : delete the "id" deals.
     *
     * @param id the id of the dealsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteDeals(@PathVariable("id") String id) {
        log.debug("REST request to delete Deals : {}", id);
        return dealsService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
                        .build()
                )
            );
    }
}
