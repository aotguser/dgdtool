package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Support_level;
import com.asp.dgdtool.repository.Support_levelRepository;
import com.asp.dgdtool.repository.search.Support_levelSearchRepository;
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
 * REST controller for managing Support_level.
 */
@RestController
@RequestMapping("/api")
public class Support_levelResource {

    private final Logger log = LoggerFactory.getLogger(Support_levelResource.class);

    @Inject
    private Support_levelRepository support_levelRepository;

    @Inject
    private Support_levelSearchRepository support_levelSearchRepository;

    /**
     * POST  /support_levels -> Create a new support_level.
     */
    @RequestMapping(value = "/support_levels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Support_level> createSupport_level(@Valid @RequestBody Support_level support_level) throws URISyntaxException {
        log.debug("REST request to save Support_level : {}", support_level);
        if (support_level.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new support_level cannot already have an ID").body(null);
        }
        Support_level result = support_levelRepository.save(support_level);
        support_levelSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/support_levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("support_level", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /support_levels -> Updates an existing support_level.
     */
    @RequestMapping(value = "/support_levels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Support_level> updateSupport_level(@Valid @RequestBody Support_level support_level) throws URISyntaxException {
        log.debug("REST request to update Support_level : {}", support_level);
        if (support_level.getId() == null) {
            return createSupport_level(support_level);
        }
        Support_level result = support_levelRepository.save(support_level);
        support_levelSearchRepository.save(support_level);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("support_level", support_level.getId().toString()))
            .body(result);
    }

    /**
     * GET  /support_levels -> get all the support_levels.
     */
    @RequestMapping(value = "/support_levels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Support_level>> getAllSupport_levels(Pageable pageable)
        throws URISyntaxException {
        Page<Support_level> page = support_levelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/support_levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /support_levels/:id -> get the "id" support_level.
     */
    @RequestMapping(value = "/support_levels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Support_level> getSupport_level(@PathVariable Long id) {
        log.debug("REST request to get Support_level : {}", id);
        return Optional.ofNullable(support_levelRepository.findOne(id))
            .map(support_level -> new ResponseEntity<>(
                support_level,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /support_levels/:id -> delete the "id" support_level.
     */
    @RequestMapping(value = "/support_levels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSupport_level(@PathVariable Long id) {
        log.debug("REST request to delete Support_level : {}", id);
        support_levelRepository.delete(id);
        support_levelSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("support_level", id.toString())).build();
    }

    /**
     * SEARCH  /_search/support_levels/:query -> search for the support_level corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/support_levels/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Support_level> searchSupport_levels(@PathVariable String query) {
        return StreamSupport
            .stream(support_levelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
