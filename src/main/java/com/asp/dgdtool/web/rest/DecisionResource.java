package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Decision;
import com.asp.dgdtool.repository.DecisionRepository;
import com.asp.dgdtool.repository.search.DecisionSearchRepository;
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
 * REST controller for managing Decision.
 */
@RestController
@RequestMapping("/api")
public class DecisionResource {

    private final Logger log = LoggerFactory.getLogger(DecisionResource.class);

    @Inject
    private DecisionRepository decisionRepository;

    @Inject
    private DecisionSearchRepository decisionSearchRepository;

    /**
     * POST  /decisions -> Create a new decision.
     */
    @RequestMapping(value = "/decisions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Decision> createDecision(@Valid @RequestBody Decision decision) throws URISyntaxException {
        log.debug("REST request to save Decision : {}", decision);
        if (decision.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new decision cannot already have an ID").body(null);
        }
        Decision result = decisionRepository.save(decision);
        decisionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/decisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("decision", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /decisions -> Updates an existing decision.
     */
    @RequestMapping(value = "/decisions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Decision> updateDecision(@Valid @RequestBody Decision decision) throws URISyntaxException {
        log.debug("REST request to update Decision : {}", decision);
        if (decision.getId() == null) {
            return createDecision(decision);
        }
        Decision result = decisionRepository.save(decision);
        decisionSearchRepository.save(decision);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("decision", decision.getId().toString()))
            .body(result);
    }

    /**
     * GET  /decisions -> get all the decisions.
     */
    @RequestMapping(value = "/decisions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Decision>> getAllDecisions(Pageable pageable)
        throws URISyntaxException {
        Page<Decision> page = decisionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/decisions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /decisions/:id -> get the "id" decision.
     */
    @RequestMapping(value = "/decisions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Decision> getDecision(@PathVariable Long id) {
        log.debug("REST request to get Decision : {}", id);
        return Optional.ofNullable(decisionRepository.findOne(id))
            .map(decision -> new ResponseEntity<>(
                decision,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /decisions/:id -> delete the "id" decision.
     */
    @RequestMapping(value = "/decisions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDecision(@PathVariable Long id) {
        log.debug("REST request to delete Decision : {}", id);
        decisionRepository.delete(id);
        decisionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("decision", id.toString())).build();
    }

    /**
     * SEARCH  /_search/decisions/:query -> search for the decision corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/decisions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Decision> searchDecisions(@PathVariable String query) {
        return StreamSupport
            .stream(decisionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
