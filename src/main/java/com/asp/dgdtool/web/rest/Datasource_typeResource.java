package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Datasource_type;
import com.asp.dgdtool.repository.Datasource_typeRepository;
import com.asp.dgdtool.repository.search.Datasource_typeSearchRepository;
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
 * REST controller for managing Datasource_type.
 */
@RestController
@RequestMapping("/api")
public class Datasource_typeResource {

    private final Logger log = LoggerFactory.getLogger(Datasource_typeResource.class);

    @Inject
    private Datasource_typeRepository datasource_typeRepository;

    @Inject
    private Datasource_typeSearchRepository datasource_typeSearchRepository;

    /**
     * POST  /datasource_types -> Create a new datasource_type.
     */
    @RequestMapping(value = "/datasource_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Datasource_type> createDatasource_type(@Valid @RequestBody Datasource_type datasource_type) throws URISyntaxException {
        log.debug("REST request to save Datasource_type : {}", datasource_type);
        if (datasource_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new datasource_type cannot already have an ID").body(null);
        }
        Datasource_type result = datasource_typeRepository.save(datasource_type);
        datasource_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/datasource_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("datasource_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /datasource_types -> Updates an existing datasource_type.
     */
    @RequestMapping(value = "/datasource_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Datasource_type> updateDatasource_type(@Valid @RequestBody Datasource_type datasource_type) throws URISyntaxException {
        log.debug("REST request to update Datasource_type : {}", datasource_type);
        if (datasource_type.getId() == null) {
            return createDatasource_type(datasource_type);
        }
        Datasource_type result = datasource_typeRepository.save(datasource_type);
        datasource_typeSearchRepository.save(datasource_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("datasource_type", datasource_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /datasource_types -> get all the datasource_types.
     */
    @RequestMapping(value = "/datasource_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Datasource_type>> getAllDatasource_types(Pageable pageable)
        throws URISyntaxException {
        Page<Datasource_type> page = datasource_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/datasource_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /datasource_types/:id -> get the "id" datasource_type.
     */
    @RequestMapping(value = "/datasource_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Datasource_type> getDatasource_type(@PathVariable Long id) {
        log.debug("REST request to get Datasource_type : {}", id);
        return Optional.ofNullable(datasource_typeRepository.findOne(id))
            .map(datasource_type -> new ResponseEntity<>(
                datasource_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /datasource_types/:id -> delete the "id" datasource_type.
     */
    @RequestMapping(value = "/datasource_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDatasource_type(@PathVariable Long id) {
        log.debug("REST request to delete Datasource_type : {}", id);
        datasource_typeRepository.delete(id);
        datasource_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("datasource_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/datasource_types/:query -> search for the datasource_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/datasource_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Datasource_type> searchDatasource_types(@PathVariable String query) {
        return StreamSupport
            .stream(datasource_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
