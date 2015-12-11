package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Initiative_type;
import com.asp.dgdtool.repository.Initiative_typeRepository;
import com.asp.dgdtool.repository.search.Initiative_typeSearchRepository;
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
 * REST controller for managing Initiative_type.
 */
@RestController
@RequestMapping("/api")
public class Initiative_typeResource {

    private final Logger log = LoggerFactory.getLogger(Initiative_typeResource.class);

    @Inject
    private Initiative_typeRepository initiative_typeRepository;

    @Inject
    private Initiative_typeSearchRepository initiative_typeSearchRepository;

    /**
     * POST  /initiative_types -> Create a new initiative_type.
     */
    @RequestMapping(value = "/initiative_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Initiative_type> createInitiative_type(@Valid @RequestBody Initiative_type initiative_type) throws URISyntaxException {
        log.debug("REST request to save Initiative_type : {}", initiative_type);
        if (initiative_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new initiative_type cannot already have an ID").body(null);
        }
        Initiative_type result = initiative_typeRepository.save(initiative_type);
        initiative_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/initiative_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("initiative_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /initiative_types -> Updates an existing initiative_type.
     */
    @RequestMapping(value = "/initiative_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Initiative_type> updateInitiative_type(@Valid @RequestBody Initiative_type initiative_type) throws URISyntaxException {
        log.debug("REST request to update Initiative_type : {}", initiative_type);
        if (initiative_type.getId() == null) {
            return createInitiative_type(initiative_type);
        }
        Initiative_type result = initiative_typeRepository.save(initiative_type);
        initiative_typeSearchRepository.save(initiative_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("initiative_type", initiative_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /initiative_types -> get all the initiative_types.
     */
    @RequestMapping(value = "/initiative_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Initiative_type>> getAllInitiative_types(Pageable pageable)
        throws URISyntaxException {
        Page<Initiative_type> page = initiative_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/initiative_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /initiative_types/:id -> get the "id" initiative_type.
     */
    @RequestMapping(value = "/initiative_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Initiative_type> getInitiative_type(@PathVariable Long id) {
        log.debug("REST request to get Initiative_type : {}", id);
        return Optional.ofNullable(initiative_typeRepository.findOne(id))
            .map(initiative_type -> new ResponseEntity<>(
                initiative_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /initiative_types/:id -> delete the "id" initiative_type.
     */
    @RequestMapping(value = "/initiative_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInitiative_type(@PathVariable Long id) {
        log.debug("REST request to delete Initiative_type : {}", id);
        initiative_typeRepository.delete(id);
        initiative_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("initiative_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/initiative_types/:query -> search for the initiative_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/initiative_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Initiative_type> searchInitiative_types(@PathVariable String query) {
        return StreamSupport
            .stream(initiative_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
