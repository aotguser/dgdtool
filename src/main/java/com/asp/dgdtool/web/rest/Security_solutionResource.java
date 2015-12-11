package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Security_solution;
import com.asp.dgdtool.repository.Security_solutionRepository;
import com.asp.dgdtool.repository.search.Security_solutionSearchRepository;
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
 * REST controller for managing Security_solution.
 */
@RestController
@RequestMapping("/api")
public class Security_solutionResource {

    private final Logger log = LoggerFactory.getLogger(Security_solutionResource.class);

    @Inject
    private Security_solutionRepository security_solutionRepository;

    @Inject
    private Security_solutionSearchRepository security_solutionSearchRepository;

    /**
     * POST  /security_solutions -> Create a new security_solution.
     */
    @RequestMapping(value = "/security_solutions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Security_solution> createSecurity_solution(@Valid @RequestBody Security_solution security_solution) throws URISyntaxException {
        log.debug("REST request to save Security_solution : {}", security_solution);
        if (security_solution.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new security_solution cannot already have an ID").body(null);
        }
        Security_solution result = security_solutionRepository.save(security_solution);
        security_solutionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/security_solutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("security_solution", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /security_solutions -> Updates an existing security_solution.
     */
    @RequestMapping(value = "/security_solutions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Security_solution> updateSecurity_solution(@Valid @RequestBody Security_solution security_solution) throws URISyntaxException {
        log.debug("REST request to update Security_solution : {}", security_solution);
        if (security_solution.getId() == null) {
            return createSecurity_solution(security_solution);
        }
        Security_solution result = security_solutionRepository.save(security_solution);
        security_solutionSearchRepository.save(security_solution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("security_solution", security_solution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /security_solutions -> get all the security_solutions.
     */
    @RequestMapping(value = "/security_solutions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Security_solution>> getAllSecurity_solutions(Pageable pageable)
        throws URISyntaxException {
        Page<Security_solution> page = security_solutionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/security_solutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /security_solutions/:id -> get the "id" security_solution.
     */
    @RequestMapping(value = "/security_solutions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Security_solution> getSecurity_solution(@PathVariable Long id) {
        log.debug("REST request to get Security_solution : {}", id);
        return Optional.ofNullable(security_solutionRepository.findOne(id))
            .map(security_solution -> new ResponseEntity<>(
                security_solution,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /security_solutions/:id -> delete the "id" security_solution.
     */
    @RequestMapping(value = "/security_solutions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSecurity_solution(@PathVariable Long id) {
        log.debug("REST request to delete Security_solution : {}", id);
        security_solutionRepository.delete(id);
        security_solutionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("security_solution", id.toString())).build();
    }

    /**
     * SEARCH  /_search/security_solutions/:query -> search for the security_solution corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/security_solutions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Security_solution> searchSecurity_solutions(@PathVariable String query) {
        return StreamSupport
            .stream(security_solutionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
