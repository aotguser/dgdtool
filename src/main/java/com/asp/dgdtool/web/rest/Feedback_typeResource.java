package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Feedback_type;
import com.asp.dgdtool.repository.Feedback_typeRepository;
import com.asp.dgdtool.repository.search.Feedback_typeSearchRepository;
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
 * REST controller for managing Feedback_type.
 */
@RestController
@RequestMapping("/api")
public class Feedback_typeResource {

    private final Logger log = LoggerFactory.getLogger(Feedback_typeResource.class);

    @Inject
    private Feedback_typeRepository feedback_typeRepository;

    @Inject
    private Feedback_typeSearchRepository feedback_typeSearchRepository;

    /**
     * POST  /feedback_types -> Create a new feedback_type.
     */
    @RequestMapping(value = "/feedback_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Feedback_type> createFeedback_type(@Valid @RequestBody Feedback_type feedback_type) throws URISyntaxException {
        log.debug("REST request to save Feedback_type : {}", feedback_type);
        if (feedback_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new feedback_type cannot already have an ID").body(null);
        }
        Feedback_type result = feedback_typeRepository.save(feedback_type);
        feedback_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/feedback_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("feedback_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feedback_types -> Updates an existing feedback_type.
     */
    @RequestMapping(value = "/feedback_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Feedback_type> updateFeedback_type(@Valid @RequestBody Feedback_type feedback_type) throws URISyntaxException {
        log.debug("REST request to update Feedback_type : {}", feedback_type);
        if (feedback_type.getId() == null) {
            return createFeedback_type(feedback_type);
        }
        Feedback_type result = feedback_typeRepository.save(feedback_type);
        feedback_typeSearchRepository.save(feedback_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("feedback_type", feedback_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feedback_types -> get all the feedback_types.
     */
    @RequestMapping(value = "/feedback_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Feedback_type>> getAllFeedback_types(Pageable pageable)
        throws URISyntaxException {
        Page<Feedback_type> page = feedback_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feedback_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /feedback_types/:id -> get the "id" feedback_type.
     */
    @RequestMapping(value = "/feedback_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Feedback_type> getFeedback_type(@PathVariable Long id) {
        log.debug("REST request to get Feedback_type : {}", id);
        return Optional.ofNullable(feedback_typeRepository.findOne(id))
            .map(feedback_type -> new ResponseEntity<>(
                feedback_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /feedback_types/:id -> delete the "id" feedback_type.
     */
    @RequestMapping(value = "/feedback_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFeedback_type(@PathVariable Long id) {
        log.debug("REST request to delete Feedback_type : {}", id);
        feedback_typeRepository.delete(id);
        feedback_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feedback_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/feedback_types/:query -> search for the feedback_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/feedback_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Feedback_type> searchFeedback_types(@PathVariable String query) {
        return StreamSupport
            .stream(feedback_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
