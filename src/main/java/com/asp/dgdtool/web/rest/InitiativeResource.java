package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Initiative;
import com.asp.dgdtool.repository.InitiativeRepository;
import com.asp.dgdtool.repository.search.InitiativeSearchRepository;
import com.asp.dgdtool.web.rest.util.HeaderUtil;
import com.asp.dgdtool.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Initiative.
 */
@RestController
@RequestMapping("/api")
public class InitiativeResource {

    private final Logger log = LoggerFactory.getLogger(InitiativeResource.class);

    @Inject
    private InitiativeRepository initiativeRepository;

    @Inject
    private InitiativeSearchRepository initiativeSearchRepository;

    /**
     * POST  /initiatives -> Create a new initiative.
     */
    @RequestMapping(value = "/initiatives",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Initiative> createInitiative(@Valid @RequestBody Initiative initiative) throws URISyntaxException {
        log.debug("REST request to save Initiative : {}", initiative);
        if (initiative.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new initiative cannot already have an ID").body(null);
        }
        Initiative result = initiativeRepository.save(initiative);
        initiativeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/initiatives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("initiative", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /initiatives -> Updates an existing initiative.
     */
    @RequestMapping(value = "/initiatives",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Initiative> updateInitiative(@Valid @RequestBody Initiative initiative) throws URISyntaxException {
        log.debug("REST request to update Initiative : {}", initiative);
        if (initiative.getId() == null) {
            return createInitiative(initiative);
        }
        Initiative result = initiativeRepository.save(initiative);
        initiativeSearchRepository.save(initiative);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("initiative", initiative.getId().toString()))
            .body(result);
    }

    /**
     * GET  /initiatives -> get all the initiatives.
     */
    @RequestMapping(value = "/initiatives",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Initiative>> getAllInitiatives(Pageable pageable)
        throws URISyntaxException {
        Page<Initiative> page = initiativeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/initiatives");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /initiatives/:id -> get the "id" initiative.
     */
    @RequestMapping(value = "/initiatives/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Initiative> getInitiative(@PathVariable Long id) {
        log.debug("REST request to get Initiative : {}", id);
        return Optional.ofNullable(initiativeRepository.findOne(id))
            .map(initiative -> new ResponseEntity<>(
                initiative,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /initiatives/:id -> delete the "id" initiative.
     */
    @RequestMapping(value = "/initiatives/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInitiative(@PathVariable Long id) {
        log.debug("REST request to delete Initiative : {}", id);
        initiativeRepository.delete(id);
        initiativeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("initiative", id.toString())).build();
    }

    /**
     * SEARCH  /_search/initiatives/:query -> search for the initiative corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/initiatives/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Initiative> searchInitiatives(@PathVariable String query) {
        return StreamSupport
            .stream(initiativeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
