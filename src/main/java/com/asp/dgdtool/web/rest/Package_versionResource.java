package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Package_version;
import com.asp.dgdtool.repository.Package_versionRepository;
import com.asp.dgdtool.repository.search.Package_versionSearchRepository;
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
 * REST controller for managing Package_version.
 */
@RestController
@RequestMapping("/api")
public class Package_versionResource {

    private final Logger log = LoggerFactory.getLogger(Package_versionResource.class);

    @Inject
    private Package_versionRepository package_versionRepository;

    @Inject
    private Package_versionSearchRepository package_versionSearchRepository;

    /**
     * POST  /package_versions -> Create a new package_version.
     */
    @RequestMapping(value = "/package_versions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_version> createPackage_version(@Valid @RequestBody Package_version package_version) throws URISyntaxException {
        log.debug("REST request to save Package_version : {}", package_version);
        if (package_version.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new package_version cannot already have an ID").body(null);
        }
        Package_version result = package_versionRepository.save(package_version);
        package_versionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/package_versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("package_version", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /package_versions -> Updates an existing package_version.
     */
    @RequestMapping(value = "/package_versions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_version> updatePackage_version(@Valid @RequestBody Package_version package_version) throws URISyntaxException {
        log.debug("REST request to update Package_version : {}", package_version);
        if (package_version.getId() == null) {
            return createPackage_version(package_version);
        }
        Package_version result = package_versionRepository.save(package_version);
        package_versionSearchRepository.save(package_version);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("package_version", package_version.getId().toString()))
            .body(result);
    }

    /**
     * GET  /package_versions -> get all the package_versions.
     */
    @RequestMapping(value = "/package_versions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Package_version>> getAllPackage_versions(Pageable pageable)
        throws URISyntaxException {
        Page<Package_version> page = package_versionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/package_versions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /package_versions/:id -> get the "id" package_version.
     */
    @RequestMapping(value = "/package_versions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_version> getPackage_version(@PathVariable Long id) {
        log.debug("REST request to get Package_version : {}", id);
        return Optional.ofNullable(package_versionRepository.findOne(id))
            .map(package_version -> new ResponseEntity<>(
                package_version,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /package_versions/:id -> delete the "id" package_version.
     */
    @RequestMapping(value = "/package_versions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePackage_version(@PathVariable Long id) {
        log.debug("REST request to delete Package_version : {}", id);
        package_versionRepository.delete(id);
        package_versionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("package_version", id.toString())).build();
    }

    /**
     * SEARCH  /_search/package_versions/:query -> search for the package_version corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/package_versions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Package_version> searchPackage_versions(@PathVariable String query) {
        return StreamSupport
            .stream(package_versionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
