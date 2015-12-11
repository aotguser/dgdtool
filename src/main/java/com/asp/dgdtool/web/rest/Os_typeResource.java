package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Os_type;
import com.asp.dgdtool.repository.Os_typeRepository;
import com.asp.dgdtool.repository.search.Os_typeSearchRepository;
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
 * REST controller for managing Os_type.
 */
@RestController
@RequestMapping("/api")
public class Os_typeResource {

    private final Logger log = LoggerFactory.getLogger(Os_typeResource.class);

    @Inject
    private Os_typeRepository os_typeRepository;

    @Inject
    private Os_typeSearchRepository os_typeSearchRepository;

    /**
     * POST  /os_types -> Create a new os_type.
     */
    @RequestMapping(value = "/os_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Os_type> createOs_type(@Valid @RequestBody Os_type os_type) throws URISyntaxException {
        log.debug("REST request to save Os_type : {}", os_type);
        if (os_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new os_type cannot already have an ID").body(null);
        }
        Os_type result = os_typeRepository.save(os_type);
        os_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/os_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("os_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /os_types -> Updates an existing os_type.
     */
    @RequestMapping(value = "/os_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Os_type> updateOs_type(@Valid @RequestBody Os_type os_type) throws URISyntaxException {
        log.debug("REST request to update Os_type : {}", os_type);
        if (os_type.getId() == null) {
            return createOs_type(os_type);
        }
        Os_type result = os_typeRepository.save(os_type);
        os_typeSearchRepository.save(os_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("os_type", os_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /os_types -> get all the os_types.
     */
    @RequestMapping(value = "/os_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Os_type>> getAllOs_types(Pageable pageable)
        throws URISyntaxException {
        Page<Os_type> page = os_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/os_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /os_types/:id -> get the "id" os_type.
     */
    @RequestMapping(value = "/os_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Os_type> getOs_type(@PathVariable Long id) {
        log.debug("REST request to get Os_type : {}", id);
        return Optional.ofNullable(os_typeRepository.findOne(id))
            .map(os_type -> new ResponseEntity<>(
                os_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /os_types/:id -> delete the "id" os_type.
     */
    @RequestMapping(value = "/os_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOs_type(@PathVariable Long id) {
        log.debug("REST request to delete Os_type : {}", id);
        os_typeRepository.delete(id);
        os_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("os_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/os_types/:query -> search for the os_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/os_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Os_type> searchOs_types(@PathVariable String query) {
        return StreamSupport
            .stream(os_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
