package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.End_state_designation;
import com.asp.dgdtool.repository.End_state_designationRepository;
import com.asp.dgdtool.repository.search.End_state_designationSearchRepository;
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
 * REST controller for managing End_state_designation.
 */
@RestController
@RequestMapping("/api")
public class End_state_designationResource {

    private final Logger log = LoggerFactory.getLogger(End_state_designationResource.class);

    @Inject
    private End_state_designationRepository end_state_designationRepository;

    @Inject
    private End_state_designationSearchRepository end_state_designationSearchRepository;

    /**
     * POST  /end_state_designations -> Create a new end_state_designation.
     */
    @RequestMapping(value = "/end_state_designations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<End_state_designation> createEnd_state_designation(@Valid @RequestBody End_state_designation end_state_designation) throws URISyntaxException {
        log.debug("REST request to save End_state_designation : {}", end_state_designation);
        if (end_state_designation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new end_state_designation cannot already have an ID").body(null);
        }
        End_state_designation result = end_state_designationRepository.save(end_state_designation);
        end_state_designationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/end_state_designations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("end_state_designation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /end_state_designations -> Updates an existing end_state_designation.
     */
    @RequestMapping(value = "/end_state_designations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<End_state_designation> updateEnd_state_designation(@Valid @RequestBody End_state_designation end_state_designation) throws URISyntaxException {
        log.debug("REST request to update End_state_designation : {}", end_state_designation);
        if (end_state_designation.getId() == null) {
            return createEnd_state_designation(end_state_designation);
        }
        End_state_designation result = end_state_designationRepository.save(end_state_designation);
        end_state_designationSearchRepository.save(end_state_designation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("end_state_designation", end_state_designation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /end_state_designations -> get all the end_state_designations.
     */
    @RequestMapping(value = "/end_state_designations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<End_state_designation>> getAllEnd_state_designations(Pageable pageable)
        throws URISyntaxException {
        Page<End_state_designation> page = end_state_designationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/end_state_designations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /end_state_designations/:id -> get the "id" end_state_designation.
     */
    @RequestMapping(value = "/end_state_designations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<End_state_designation> getEnd_state_designation(@PathVariable Long id) {
        log.debug("REST request to get End_state_designation : {}", id);
        return Optional.ofNullable(end_state_designationRepository.findOne(id))
            .map(end_state_designation -> new ResponseEntity<>(
                end_state_designation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /end_state_designations/:id -> delete the "id" end_state_designation.
     */
    @RequestMapping(value = "/end_state_designations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEnd_state_designation(@PathVariable Long id) {
        log.debug("REST request to delete End_state_designation : {}", id);
        end_state_designationRepository.delete(id);
        end_state_designationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("end_state_designation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/end_state_designations/:query -> search for the end_state_designation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/end_state_designations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<End_state_designation> searchEnd_state_designations(@PathVariable String query) {
        return StreamSupport
            .stream(end_state_designationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
