package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Field_type;
import com.asp.dgdtool.repository.Field_typeRepository;
import com.asp.dgdtool.repository.search.Field_typeSearchRepository;
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
 * REST controller for managing Field_type.
 */
@RestController
@RequestMapping("/api")
public class Field_typeResource {

    private final Logger log = LoggerFactory.getLogger(Field_typeResource.class);

    @Inject
    private Field_typeRepository field_typeRepository;

    @Inject
    private Field_typeSearchRepository field_typeSearchRepository;

    /**
     * POST  /field_types -> Create a new field_type.
     */
    @RequestMapping(value = "/field_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Field_type> createField_type(@Valid @RequestBody Field_type field_type) throws URISyntaxException {
        log.debug("REST request to save Field_type : {}", field_type);
        if (field_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new field_type cannot already have an ID").body(null);
        }
        Field_type result = field_typeRepository.save(field_type);
        field_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/field_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("field_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /field_types -> Updates an existing field_type.
     */
    @RequestMapping(value = "/field_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Field_type> updateField_type(@Valid @RequestBody Field_type field_type) throws URISyntaxException {
        log.debug("REST request to update Field_type : {}", field_type);
        if (field_type.getId() == null) {
            return createField_type(field_type);
        }
        Field_type result = field_typeRepository.save(field_type);
        field_typeSearchRepository.save(field_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("field_type", field_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /field_types -> get all the field_types.
     */
    @RequestMapping(value = "/field_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Field_type>> getAllField_types(Pageable pageable)
        throws URISyntaxException {
        Page<Field_type> page = field_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/field_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /field_types/:id -> get the "id" field_type.
     */
    @RequestMapping(value = "/field_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Field_type> getField_type(@PathVariable Long id) {
        log.debug("REST request to get Field_type : {}", id);
        return Optional.ofNullable(field_typeRepository.findOne(id))
            .map(field_type -> new ResponseEntity<>(
                field_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /field_types/:id -> delete the "id" field_type.
     */
    @RequestMapping(value = "/field_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteField_type(@PathVariable Long id) {
        log.debug("REST request to delete Field_type : {}", id);
        field_typeRepository.delete(id);
        field_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("field_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/field_types/:query -> search for the field_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/field_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Field_type> searchField_types(@PathVariable String query) {
        return StreamSupport
            .stream(field_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
