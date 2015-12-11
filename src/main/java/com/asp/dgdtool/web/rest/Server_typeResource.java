package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Server_type;
import com.asp.dgdtool.repository.Server_typeRepository;
import com.asp.dgdtool.repository.search.Server_typeSearchRepository;
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
 * REST controller for managing Server_type.
 */
@RestController
@RequestMapping("/api")
public class Server_typeResource {

    private final Logger log = LoggerFactory.getLogger(Server_typeResource.class);

    @Inject
    private Server_typeRepository server_typeRepository;

    @Inject
    private Server_typeSearchRepository server_typeSearchRepository;

    /**
     * POST  /server_types -> Create a new server_type.
     */
    @RequestMapping(value = "/server_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Server_type> createServer_type(@Valid @RequestBody Server_type server_type) throws URISyntaxException {
        log.debug("REST request to save Server_type : {}", server_type);
        if (server_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new server_type cannot already have an ID").body(null);
        }
        Server_type result = server_typeRepository.save(server_type);
        server_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/server_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("server_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /server_types -> Updates an existing server_type.
     */
    @RequestMapping(value = "/server_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Server_type> updateServer_type(@Valid @RequestBody Server_type server_type) throws URISyntaxException {
        log.debug("REST request to update Server_type : {}", server_type);
        if (server_type.getId() == null) {
            return createServer_type(server_type);
        }
        Server_type result = server_typeRepository.save(server_type);
        server_typeSearchRepository.save(server_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("server_type", server_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /server_types -> get all the server_types.
     */
    @RequestMapping(value = "/server_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Server_type>> getAllServer_types(Pageable pageable)
        throws URISyntaxException {
        Page<Server_type> page = server_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/server_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /server_types/:id -> get the "id" server_type.
     */
    @RequestMapping(value = "/server_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Server_type> getServer_type(@PathVariable Long id) {
        log.debug("REST request to get Server_type : {}", id);
        return Optional.ofNullable(server_typeRepository.findOne(id))
            .map(server_type -> new ResponseEntity<>(
                server_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /server_types/:id -> delete the "id" server_type.
     */
    @RequestMapping(value = "/server_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteServer_type(@PathVariable Long id) {
        log.debug("REST request to delete Server_type : {}", id);
        server_typeRepository.delete(id);
        server_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("server_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/server_types/:query -> search for the server_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/server_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Server_type> searchServer_types(@PathVariable String query) {
        return StreamSupport
            .stream(server_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
