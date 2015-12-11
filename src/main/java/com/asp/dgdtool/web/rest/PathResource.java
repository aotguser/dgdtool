package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Path;
import com.asp.dgdtool.repository.PathRepository;
import com.asp.dgdtool.repository.search.PathSearchRepository;
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
 * REST controller for managing Path.
 */
@RestController
@RequestMapping("/api")
public class PathResource {

    private final Logger log = LoggerFactory.getLogger(PathResource.class);

    @Inject
    private PathRepository pathRepository;

    @Inject
    private PathSearchRepository pathSearchRepository;

    /**
     * POST  /paths -> Create a new path.
     */
    @RequestMapping(value = "/paths",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Path> createPath(@Valid @RequestBody Path path) throws URISyntaxException {
        log.debug("REST request to save Path : {}", path);
        if (path.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new path cannot already have an ID").body(null);
        }
        Path result = pathRepository.save(path);
        pathSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("path", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paths -> Updates an existing path.
     */
    @RequestMapping(value = "/paths",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Path> updatePath(@Valid @RequestBody Path path) throws URISyntaxException {
        log.debug("REST request to update Path : {}", path);
        if (path.getId() == null) {
            return createPath(path);
        }
        Path result = pathRepository.save(path);
        pathSearchRepository.save(path);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("path", path.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paths -> get all the paths.
     */
    @RequestMapping(value = "/paths",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Path>> getAllPaths(Pageable pageable)
        throws URISyntaxException {
        Page<Path> page = pathRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/paths");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /paths/:id -> get the "id" path.
     */
    @RequestMapping(value = "/paths/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Path> getPath(@PathVariable Long id) {
        log.debug("REST request to get Path : {}", id);
        return Optional.ofNullable(pathRepository.findOne(id))
            .map(path -> new ResponseEntity<>(
                path,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /paths/:id -> delete the "id" path.
     */
    @RequestMapping(value = "/paths/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePath(@PathVariable Long id) {
        log.debug("REST request to delete Path : {}", id);
        pathRepository.delete(id);
        pathSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("path", id.toString())).build();
    }

    /**
     * SEARCH  /_search/paths/:query -> search for the path corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/paths/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Path> searchPaths(@PathVariable String query) {
        return StreamSupport
            .stream(pathSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
