package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.App_type;
import com.asp.dgdtool.repository.App_typeRepository;
import com.asp.dgdtool.repository.search.App_typeSearchRepository;
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
 * REST controller for managing App_type.
 */
@RestController
@RequestMapping("/api")
public class App_typeResource {

    private final Logger log = LoggerFactory.getLogger(App_typeResource.class);

    @Inject
    private App_typeRepository app_typeRepository;

    @Inject
    private App_typeSearchRepository app_typeSearchRepository;

    /**
     * POST  /app_types -> Create a new app_type.
     */
    @RequestMapping(value = "/app_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<App_type> createApp_type(@Valid @RequestBody App_type app_type) throws URISyntaxException {
        log.debug("REST request to save App_type : {}", app_type);
        if (app_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new app_type cannot already have an ID").body(null);
        }
        App_type result = app_typeRepository.save(app_type);
        app_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/app_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("app_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /app_types -> Updates an existing app_type.
     */
    @RequestMapping(value = "/app_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<App_type> updateApp_type(@Valid @RequestBody App_type app_type) throws URISyntaxException {
        log.debug("REST request to update App_type : {}", app_type);
        if (app_type.getId() == null) {
            return createApp_type(app_type);
        }
        App_type result = app_typeRepository.save(app_type);
        app_typeSearchRepository.save(app_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("app_type", app_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /app_types -> get all the app_types.
     */
    @RequestMapping(value = "/app_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<App_type>> getAllApp_types(Pageable pageable)
        throws URISyntaxException {
        Page<App_type> page = app_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/app_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /app_types/:id -> get the "id" app_type.
     */
    @RequestMapping(value = "/app_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<App_type> getApp_type(@PathVariable Long id) {
        log.debug("REST request to get App_type : {}", id);
        return Optional.ofNullable(app_typeRepository.findOne(id))
            .map(app_type -> new ResponseEntity<>(
                app_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /app_types/:id -> delete the "id" app_type.
     */
    @RequestMapping(value = "/app_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApp_type(@PathVariable Long id) {
        log.debug("REST request to delete App_type : {}", id);
        app_typeRepository.delete(id);
        app_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("app_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/app_types/:query -> search for the app_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/app_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<App_type> searchApp_types(@PathVariable String query) {
        return StreamSupport
            .stream(app_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
