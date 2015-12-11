package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Os_version;
import com.asp.dgdtool.repository.Os_versionRepository;
import com.asp.dgdtool.repository.search.Os_versionSearchRepository;
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
 * REST controller for managing Os_version.
 */
@RestController
@RequestMapping("/api")
public class Os_versionResource {

    private final Logger log = LoggerFactory.getLogger(Os_versionResource.class);

    @Inject
    private Os_versionRepository os_versionRepository;

    @Inject
    private Os_versionSearchRepository os_versionSearchRepository;

    /**
     * POST  /os_versions -> Create a new os_version.
     */
    @RequestMapping(value = "/os_versions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Os_version> createOs_version(@Valid @RequestBody Os_version os_version) throws URISyntaxException {
        log.debug("REST request to save Os_version : {}", os_version);
        if (os_version.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new os_version cannot already have an ID").body(null);
        }
        Os_version result = os_versionRepository.save(os_version);
        os_versionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/os_versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("os_version", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /os_versions -> Updates an existing os_version.
     */
    @RequestMapping(value = "/os_versions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Os_version> updateOs_version(@Valid @RequestBody Os_version os_version) throws URISyntaxException {
        log.debug("REST request to update Os_version : {}", os_version);
        if (os_version.getId() == null) {
            return createOs_version(os_version);
        }
        Os_version result = os_versionRepository.save(os_version);
        os_versionSearchRepository.save(os_version);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("os_version", os_version.getId().toString()))
            .body(result);
    }

    /**
     * GET  /os_versions -> get all the os_versions.
     */
    @RequestMapping(value = "/os_versions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Os_version>> getAllOs_versions(Pageable pageable)
        throws URISyntaxException {
        Page<Os_version> page = os_versionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/os_versions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /os_versions/:id -> get the "id" os_version.
     */
    @RequestMapping(value = "/os_versions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Os_version> getOs_version(@PathVariable Long id) {
        log.debug("REST request to get Os_version : {}", id);
        return Optional.ofNullable(os_versionRepository.findOne(id))
            .map(os_version -> new ResponseEntity<>(
                os_version,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /os_versions/:id -> delete the "id" os_version.
     */
    @RequestMapping(value = "/os_versions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOs_version(@PathVariable Long id) {
        log.debug("REST request to delete Os_version : {}", id);
        os_versionRepository.delete(id);
        os_versionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("os_version", id.toString())).build();
    }

    /**
     * SEARCH  /_search/os_versions/:query -> search for the os_version corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/os_versions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Os_version> searchOs_versions(@PathVariable String query) {
        return StreamSupport
            .stream(os_versionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
