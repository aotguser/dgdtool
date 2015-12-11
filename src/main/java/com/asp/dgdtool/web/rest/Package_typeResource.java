package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Package_type;
import com.asp.dgdtool.repository.Package_typeRepository;
import com.asp.dgdtool.repository.search.Package_typeSearchRepository;
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
 * REST controller for managing Package_type.
 */
@RestController
@RequestMapping("/api")
public class Package_typeResource {

    private final Logger log = LoggerFactory.getLogger(Package_typeResource.class);

    @Inject
    private Package_typeRepository package_typeRepository;

    @Inject
    private Package_typeSearchRepository package_typeSearchRepository;

    /**
     * POST  /package_types -> Create a new package_type.
     */
    @RequestMapping(value = "/package_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_type> createPackage_type(@Valid @RequestBody Package_type package_type) throws URISyntaxException {
        log.debug("REST request to save Package_type : {}", package_type);
        if (package_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new package_type cannot already have an ID").body(null);
        }
        Package_type result = package_typeRepository.save(package_type);
        package_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/package_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("package_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /package_types -> Updates an existing package_type.
     */
    @RequestMapping(value = "/package_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_type> updatePackage_type(@Valid @RequestBody Package_type package_type) throws URISyntaxException {
        log.debug("REST request to update Package_type : {}", package_type);
        if (package_type.getId() == null) {
            return createPackage_type(package_type);
        }
        Package_type result = package_typeRepository.save(package_type);
        package_typeSearchRepository.save(package_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("package_type", package_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /package_types -> get all the package_types.
     */
    @RequestMapping(value = "/package_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Package_type>> getAllPackage_types(Pageable pageable)
        throws URISyntaxException {
        Page<Package_type> page = package_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/package_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /package_types/:id -> get the "id" package_type.
     */
    @RequestMapping(value = "/package_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_type> getPackage_type(@PathVariable Long id) {
        log.debug("REST request to get Package_type : {}", id);
        return Optional.ofNullable(package_typeRepository.findOne(id))
            .map(package_type -> new ResponseEntity<>(
                package_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /package_types/:id -> delete the "id" package_type.
     */
    @RequestMapping(value = "/package_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePackage_type(@PathVariable Long id) {
        log.debug("REST request to delete Package_type : {}", id);
        package_typeRepository.delete(id);
        package_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("package_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/package_types/:query -> search for the package_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/package_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Package_type> searchPackage_types(@PathVariable String query) {
        return StreamSupport
            .stream(package_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
