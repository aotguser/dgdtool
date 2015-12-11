package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Status_type;
import com.asp.dgdtool.repository.Status_typeRepository;
import com.asp.dgdtool.repository.search.Status_typeSearchRepository;
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
 * REST controller for managing Status_type.
 */
@RestController
@RequestMapping("/api")
public class Status_typeResource {

    private final Logger log = LoggerFactory.getLogger(Status_typeResource.class);

    @Inject
    private Status_typeRepository status_typeRepository;

    @Inject
    private Status_typeSearchRepository status_typeSearchRepository;

    /**
     * POST  /status_types -> Create a new status_type.
     */
    @RequestMapping(value = "/status_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_type> createStatus_type(@Valid @RequestBody Status_type status_type) throws URISyntaxException {
        log.debug("REST request to save Status_type : {}", status_type);
        if (status_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new status_type cannot already have an ID").body(null);
        }
        Status_type result = status_typeRepository.save(status_type);
        status_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/status_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status_types -> Updates an existing status_type.
     */
    @RequestMapping(value = "/status_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_type> updateStatus_type(@Valid @RequestBody Status_type status_type) throws URISyntaxException {
        log.debug("REST request to update Status_type : {}", status_type);
        if (status_type.getId() == null) {
            return createStatus_type(status_type);
        }
        Status_type result = status_typeRepository.save(status_type);
        status_typeSearchRepository.save(status_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status_type", status_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status_types -> get all the status_types.
     */
    @RequestMapping(value = "/status_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Status_type>> getAllStatus_types(Pageable pageable)
        throws URISyntaxException {
        Page<Status_type> page = status_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status_types/:id -> get the "id" status_type.
     */
    @RequestMapping(value = "/status_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Status_type> getStatus_type(@PathVariable Long id) {
        log.debug("REST request to get Status_type : {}", id);
        return Optional.ofNullable(status_typeRepository.findOne(id))
            .map(status_type -> new ResponseEntity<>(
                status_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /status_types/:id -> delete the "id" status_type.
     */
    @RequestMapping(value = "/status_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus_type(@PathVariable Long id) {
        log.debug("REST request to delete Status_type : {}", id);
        status_typeRepository.delete(id);
        status_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/status_types/:query -> search for the status_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/status_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Status_type> searchStatus_types(@PathVariable String query) {
        return StreamSupport
            .stream(status_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
