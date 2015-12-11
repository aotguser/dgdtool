package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.License_type;
import com.asp.dgdtool.repository.License_typeRepository;
import com.asp.dgdtool.repository.search.License_typeSearchRepository;
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
 * REST controller for managing License_type.
 */
@RestController
@RequestMapping("/api")
public class License_typeResource {

    private final Logger log = LoggerFactory.getLogger(License_typeResource.class);

    @Inject
    private License_typeRepository license_typeRepository;

    @Inject
    private License_typeSearchRepository license_typeSearchRepository;

    /**
     * POST  /license_types -> Create a new license_type.
     */
    @RequestMapping(value = "/license_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<License_type> createLicense_type(@Valid @RequestBody License_type license_type) throws URISyntaxException {
        log.debug("REST request to save License_type : {}", license_type);
        if (license_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new license_type cannot already have an ID").body(null);
        }
        License_type result = license_typeRepository.save(license_type);
        license_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/license_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("license_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /license_types -> Updates an existing license_type.
     */
    @RequestMapping(value = "/license_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<License_type> updateLicense_type(@Valid @RequestBody License_type license_type) throws URISyntaxException {
        log.debug("REST request to update License_type : {}", license_type);
        if (license_type.getId() == null) {
            return createLicense_type(license_type);
        }
        License_type result = license_typeRepository.save(license_type);
        license_typeSearchRepository.save(license_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("license_type", license_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /license_types -> get all the license_types.
     */
    @RequestMapping(value = "/license_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<License_type>> getAllLicense_types(Pageable pageable)
        throws URISyntaxException {
        Page<License_type> page = license_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/license_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /license_types/:id -> get the "id" license_type.
     */
    @RequestMapping(value = "/license_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<License_type> getLicense_type(@PathVariable Long id) {
        log.debug("REST request to get License_type : {}", id);
        return Optional.ofNullable(license_typeRepository.findOne(id))
            .map(license_type -> new ResponseEntity<>(
                license_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /license_types/:id -> delete the "id" license_type.
     */
    @RequestMapping(value = "/license_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLicense_type(@PathVariable Long id) {
        log.debug("REST request to delete License_type : {}", id);
        license_typeRepository.delete(id);
        license_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("license_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/license_types/:query -> search for the license_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/license_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<License_type> searchLicense_types(@PathVariable String query) {
        return StreamSupport
            .stream(license_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
