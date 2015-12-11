package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Server_role;
import com.asp.dgdtool.repository.Server_roleRepository;
import com.asp.dgdtool.repository.search.Server_roleSearchRepository;
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
 * REST controller for managing Server_role.
 */
@RestController
@RequestMapping("/api")
public class Server_roleResource {

    private final Logger log = LoggerFactory.getLogger(Server_roleResource.class);

    @Inject
    private Server_roleRepository server_roleRepository;

    @Inject
    private Server_roleSearchRepository server_roleSearchRepository;

    /**
     * POST  /server_roles -> Create a new server_role.
     */
    @RequestMapping(value = "/server_roles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Server_role> createServer_role(@Valid @RequestBody Server_role server_role) throws URISyntaxException {
        log.debug("REST request to save Server_role : {}", server_role);
        if (server_role.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new server_role cannot already have an ID").body(null);
        }
        Server_role result = server_roleRepository.save(server_role);
        server_roleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/server_roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("server_role", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /server_roles -> Updates an existing server_role.
     */
    @RequestMapping(value = "/server_roles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Server_role> updateServer_role(@Valid @RequestBody Server_role server_role) throws URISyntaxException {
        log.debug("REST request to update Server_role : {}", server_role);
        if (server_role.getId() == null) {
            return createServer_role(server_role);
        }
        Server_role result = server_roleRepository.save(server_role);
        server_roleSearchRepository.save(server_role);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("server_role", server_role.getId().toString()))
            .body(result);
    }

    /**
     * GET  /server_roles -> get all the server_roles.
     */
    @RequestMapping(value = "/server_roles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Server_role>> getAllServer_roles(Pageable pageable)
        throws URISyntaxException {
        Page<Server_role> page = server_roleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/server_roles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /server_roles/:id -> get the "id" server_role.
     */
    @RequestMapping(value = "/server_roles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Server_role> getServer_role(@PathVariable Long id) {
        log.debug("REST request to get Server_role : {}", id);
        return Optional.ofNullable(server_roleRepository.findOne(id))
            .map(server_role -> new ResponseEntity<>(
                server_role,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /server_roles/:id -> delete the "id" server_role.
     */
    @RequestMapping(value = "/server_roles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteServer_role(@PathVariable Long id) {
        log.debug("REST request to delete Server_role : {}", id);
        server_roleRepository.delete(id);
        server_roleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("server_role", id.toString())).build();
    }

    /**
     * SEARCH  /_search/server_roles/:query -> search for the server_role corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/server_roles/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Server_role> searchServer_roles(@PathVariable String query) {
        return StreamSupport
            .stream(server_roleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
