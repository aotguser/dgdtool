package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Platform;
import com.asp.dgdtool.repository.PlatformRepository;
import com.asp.dgdtool.repository.search.PlatformSearchRepository;
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
 * REST controller for managing Platform.
 */
@RestController
@RequestMapping("/api")
public class PlatformResource {

    private final Logger log = LoggerFactory.getLogger(PlatformResource.class);

    @Inject
    private PlatformRepository platformRepository;

    @Inject
    private PlatformSearchRepository platformSearchRepository;

    /**
     * POST  /platforms -> Create a new platform.
     */
    @RequestMapping(value = "/platforms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Platform> createPlatform(@Valid @RequestBody Platform platform) throws URISyntaxException {
        log.debug("REST request to save Platform : {}", platform);
        if (platform.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new platform cannot already have an ID").body(null);
        }
        Platform result = platformRepository.save(platform);
        platformSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/platforms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("platform", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /platforms -> Updates an existing platform.
     */
    @RequestMapping(value = "/platforms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Platform> updatePlatform(@Valid @RequestBody Platform platform) throws URISyntaxException {
        log.debug("REST request to update Platform : {}", platform);
        if (platform.getId() == null) {
            return createPlatform(platform);
        }
        Platform result = platformRepository.save(platform);
        platformSearchRepository.save(platform);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("platform", platform.getId().toString()))
            .body(result);
    }

    /**
     * GET  /platforms -> get all the platforms.
     */
    @RequestMapping(value = "/platforms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Platform>> getAllPlatforms(Pageable pageable)
        throws URISyntaxException {
        Page<Platform> page = platformRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/platforms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /platforms/:id -> get the "id" platform.
     */
    @RequestMapping(value = "/platforms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Platform> getPlatform(@PathVariable Long id) {
        log.debug("REST request to get Platform : {}", id);
        return Optional.ofNullable(platformRepository.findOne(id))
            .map(platform -> new ResponseEntity<>(
                platform,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /platforms/:id -> delete the "id" platform.
     */
    @RequestMapping(value = "/platforms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id) {
        log.debug("REST request to delete Platform : {}", id);
        platformRepository.delete(id);
        platformSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("platform", id.toString())).build();
    }

    /**
     * SEARCH  /_search/platforms/:query -> search for the platform corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/platforms/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Platform> searchPlatforms(@PathVariable String query) {
        return StreamSupport
            .stream(platformSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
