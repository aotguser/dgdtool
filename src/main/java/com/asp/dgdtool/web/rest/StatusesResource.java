package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Statuses;
import com.asp.dgdtool.repository.StatusesRepository;
import com.asp.dgdtool.repository.search.StatusesSearchRepository;
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
 * REST controller for managing Statuses.
 */
@RestController
@RequestMapping("/api")
public class StatusesResource {

    private final Logger log = LoggerFactory.getLogger(StatusesResource.class);

    @Inject
    private StatusesRepository statusesRepository;

    @Inject
    private StatusesSearchRepository statusesSearchRepository;

    /**
     * POST  /statusess -> Create a new statuses.
     */
    @RequestMapping(value = "/statusess",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Statuses> createStatuses(@Valid @RequestBody Statuses statuses) throws URISyntaxException {
        log.debug("REST request to save Statuses : {}", statuses);
        if (statuses.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new statuses cannot already have an ID").body(null);
        }
        Statuses result = statusesRepository.save(statuses);
        statusesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/statusess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("statuses", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /statusess -> Updates an existing statuses.
     */
    @RequestMapping(value = "/statusess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Statuses> updateStatuses(@Valid @RequestBody Statuses statuses) throws URISyntaxException {
        log.debug("REST request to update Statuses : {}", statuses);
        if (statuses.getId() == null) {
            return createStatuses(statuses);
        }
        Statuses result = statusesRepository.save(statuses);
        statusesSearchRepository.save(statuses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("statuses", statuses.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statusess -> get all the statusess.
     */
    @RequestMapping(value = "/statusess",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Statuses>> getAllStatusess(Pageable pageable)
        throws URISyntaxException {
        Page<Statuses> page = statusesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statusess");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /statusess/:id -> get the "id" statuses.
     */
    @RequestMapping(value = "/statusess/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Statuses> getStatuses(@PathVariable Long id) {
        log.debug("REST request to get Statuses : {}", id);
        return Optional.ofNullable(statusesRepository.findOne(id))
            .map(statuses -> new ResponseEntity<>(
                statuses,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /statusess/:id -> delete the "id" statuses.
     */
    @RequestMapping(value = "/statusess/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatuses(@PathVariable Long id) {
        log.debug("REST request to delete Statuses : {}", id);
        statusesRepository.delete(id);
        statusesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("statuses", id.toString())).build();
    }

    /**
     * SEARCH  /_search/statusess/:query -> search for the statuses corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/statusess/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Statuses> searchStatusess(@PathVariable String query) {
        return StreamSupport
            .stream(statusesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
