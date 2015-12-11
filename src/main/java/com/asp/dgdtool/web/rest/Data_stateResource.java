package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Data_state;
import com.asp.dgdtool.repository.Data_stateRepository;
import com.asp.dgdtool.repository.search.Data_stateSearchRepository;
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
 * REST controller for managing Data_state.
 */
@RestController
@RequestMapping("/api")
public class Data_stateResource {

    private final Logger log = LoggerFactory.getLogger(Data_stateResource.class);

    @Inject
    private Data_stateRepository data_stateRepository;

    @Inject
    private Data_stateSearchRepository data_stateSearchRepository;

    /**
     * POST  /data_states -> Create a new data_state.
     */
    @RequestMapping(value = "/data_states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Data_state> createData_state(@Valid @RequestBody Data_state data_state) throws URISyntaxException {
        log.debug("REST request to save Data_state : {}", data_state);
        if (data_state.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new data_state cannot already have an ID").body(null);
        }
        Data_state result = data_stateRepository.save(data_state);
        data_stateSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/data_states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("data_state", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data_states -> Updates an existing data_state.
     */
    @RequestMapping(value = "/data_states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Data_state> updateData_state(@Valid @RequestBody Data_state data_state) throws URISyntaxException {
        log.debug("REST request to update Data_state : {}", data_state);
        if (data_state.getId() == null) {
            return createData_state(data_state);
        }
        Data_state result = data_stateRepository.save(data_state);
        data_stateSearchRepository.save(data_state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("data_state", data_state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data_states -> get all the data_states.
     */
    @RequestMapping(value = "/data_states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Data_state>> getAllData_states(Pageable pageable)
        throws URISyntaxException {
        Page<Data_state> page = data_stateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data_states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /data_states/:id -> get the "id" data_state.
     */
    @RequestMapping(value = "/data_states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Data_state> getData_state(@PathVariable Long id) {
        log.debug("REST request to get Data_state : {}", id);
        return Optional.ofNullable(data_stateRepository.findOne(id))
            .map(data_state -> new ResponseEntity<>(
                data_state,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /data_states/:id -> delete the "id" data_state.
     */
    @RequestMapping(value = "/data_states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteData_state(@PathVariable Long id) {
        log.debug("REST request to delete Data_state : {}", id);
        data_stateRepository.delete(id);
        data_stateSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("data_state", id.toString())).build();
    }

    /**
     * SEARCH  /_search/data_states/:query -> search for the data_state corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/data_states/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Data_state> searchData_states(@PathVariable String query) {
        return StreamSupport
            .stream(data_stateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
