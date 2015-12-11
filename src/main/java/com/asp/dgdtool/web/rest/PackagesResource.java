package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Packages;
import com.asp.dgdtool.repository.PackagesRepository;
import com.asp.dgdtool.repository.search.PackagesSearchRepository;
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
 * REST controller for managing Packages.
 */
@RestController
@RequestMapping("/api")
public class PackagesResource {

    private final Logger log = LoggerFactory.getLogger(PackagesResource.class);

    @Inject
    private PackagesRepository packagesRepository;

    @Inject
    private PackagesSearchRepository packagesSearchRepository;

    /**
     * POST  /packagess -> Create a new packages.
     */
    @RequestMapping(value = "/packagess",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Packages> createPackages(@Valid @RequestBody Packages packages) throws URISyntaxException {
        log.debug("REST request to save Packages : {}", packages);
        if (packages.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new packages cannot already have an ID").body(null);
        }
        Packages result = packagesRepository.save(packages);
        packagesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/packagess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("packages", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /packagess -> Updates an existing packages.
     */
    @RequestMapping(value = "/packagess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Packages> updatePackages(@Valid @RequestBody Packages packages) throws URISyntaxException {
        log.debug("REST request to update Packages : {}", packages);
        if (packages.getId() == null) {
            return createPackages(packages);
        }
        Packages result = packagesRepository.save(packages);
        packagesSearchRepository.save(packages);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("packages", packages.getId().toString()))
            .body(result);
    }

    /**
     * GET  /packagess -> get all the packagess.
     */
    @RequestMapping(value = "/packagess",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Packages>> getAllPackagess(Pageable pageable)
        throws URISyntaxException {
        Page<Packages> page = packagesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/packagess");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /packagess/:id -> get the "id" packages.
     */
    @RequestMapping(value = "/packagess/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Packages> getPackages(@PathVariable Long id) {
        log.debug("REST request to get Packages : {}", id);
        return Optional.ofNullable(packagesRepository.findOne(id))
            .map(packages -> new ResponseEntity<>(
                packages,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /packagess/:id -> delete the "id" packages.
     */
    @RequestMapping(value = "/packagess/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePackages(@PathVariable Long id) {
        log.debug("REST request to delete Packages : {}", id);
        packagesRepository.delete(id);
        packagesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("packages", id.toString())).build();
    }

    /**
     * SEARCH  /_search/packagess/:query -> search for the packages corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/packagess/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Packages> searchPackagess(@PathVariable String query) {
        return StreamSupport
            .stream(packagesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
