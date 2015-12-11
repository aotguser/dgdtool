package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Asset_type;
import com.asp.dgdtool.repository.Asset_typeRepository;
import com.asp.dgdtool.repository.search.Asset_typeSearchRepository;
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
 * REST controller for managing Asset_type.
 */
@RestController
@RequestMapping("/api")
public class Asset_typeResource {

    private final Logger log = LoggerFactory.getLogger(Asset_typeResource.class);

    @Inject
    private Asset_typeRepository asset_typeRepository;

    @Inject
    private Asset_typeSearchRepository asset_typeSearchRepository;

    /**
     * POST  /asset_types -> Create a new asset_type.
     */
    @RequestMapping(value = "/asset_types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Asset_type> createAsset_type(@Valid @RequestBody Asset_type asset_type) throws URISyntaxException {
        log.debug("REST request to save Asset_type : {}", asset_type);
        if (asset_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new asset_type cannot already have an ID").body(null);
        }
        Asset_type result = asset_typeRepository.save(asset_type);
        asset_typeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/asset_types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("asset_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /asset_types -> Updates an existing asset_type.
     */
    @RequestMapping(value = "/asset_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Asset_type> updateAsset_type(@Valid @RequestBody Asset_type asset_type) throws URISyntaxException {
        log.debug("REST request to update Asset_type : {}", asset_type);
        if (asset_type.getId() == null) {
            return createAsset_type(asset_type);
        }
        Asset_type result = asset_typeRepository.save(asset_type);
        asset_typeSearchRepository.save(asset_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("asset_type", asset_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /asset_types -> get all the asset_types.
     */
    @RequestMapping(value = "/asset_types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Asset_type>> getAllAsset_types(Pageable pageable)
        throws URISyntaxException {
        Page<Asset_type> page = asset_typeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/asset_types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /asset_types/:id -> get the "id" asset_type.
     */
    @RequestMapping(value = "/asset_types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Asset_type> getAsset_type(@PathVariable Long id) {
        log.debug("REST request to get Asset_type : {}", id);
        return Optional.ofNullable(asset_typeRepository.findOne(id))
            .map(asset_type -> new ResponseEntity<>(
                asset_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /asset_types/:id -> delete the "id" asset_type.
     */
    @RequestMapping(value = "/asset_types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAsset_type(@PathVariable Long id) {
        log.debug("REST request to delete Asset_type : {}", id);
        asset_typeRepository.delete(id);
        asset_typeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("asset_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/asset_types/:query -> search for the asset_type corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/asset_types/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Asset_type> searchAsset_types(@PathVariable String query) {
        return StreamSupport
            .stream(asset_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
