package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Data_type;
import com.asp.dgdtool.repository.Data_typeRepository;
import com.asp.dgdtool.repository.search.Data_typeSearchRepository;
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
 * REST controller for managing Data_type.
 */
@RestController
@RequestMapping("/api")
public class Data_typeResource {

    private final Logger log = LoggerFactory.getLogger(Data_typeResource.class);

    @Inject
    private Data_typeRepository data_typeRepository;

    @Inject
    private Data_typeSearchRepository data_typeSearchRepository;

    /**
     * POST  /data_types -> Create a new data_type.
     */
    @RequestMapping(value = "/data_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Data_type> createData_type(@Valid @RequestBody Data_type data_type) throws URISyntaxException {
        log.debug("REST request to save Data_type : {}", data_type);
        if (data_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new data_type cannot already have an ID").body(null);
        }
        Data_type result = data_typeRepository.save(data_type);
        data_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/data_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("data_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data_types -> Updates an existing data_type.
     */
    @RequestMapping(value = "/data_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Data_type> updateData_type(@Valid @RequestBody Data_type data_type) throws URISyntaxException {
        log.debug("REST request to update Data_type : {}", data_type);
        if (data_type.getId() == null) {
            return createData_type(data_type);
        }
        Data_type result = data_typeRepository.save(data_type);
        data_typeSearchRepository.save(data_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("data_type", data_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data_types -> get all the data_types.
     */
    @RequestMapping(value = "/data_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Data_type>> getAllData_types(Pageable pageable)
        throws URISyntaxException {
        Page<Data_type> page = data_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /data_types/:id -> get the "id" data_type.
     */
    @RequestMapping(value = "/data_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Data_type> getData_type(@PathVariable Long id) {
        log.debug("REST request to get Data_type : {}", id);
        return Optional.ofNullable(data_typeRepository.findOne(id))
            .map(data_type -> new ResponseEntity<>(
                data_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /data_types/:id -> delete the "id" data_type.
     */
    @RequestMapping(value = "/data_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteData_type(@PathVariable Long id) {
        log.debug("REST request to delete Data_type : {}", id);
        data_typeRepository.delete(id);
        data_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("data_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/data_types/:query -> search for the data_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/data_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Data_type> searchData_types(@PathVariable String query) {
        return StreamSupport
            .stream(data_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
