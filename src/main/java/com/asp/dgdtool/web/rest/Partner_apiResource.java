package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Partner_api;
import com.asp.dgdtool.repository.Partner_apiRepository;
import com.asp.dgdtool.repository.search.Partner_apiSearchRepository;
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
 * REST controller for managing Partner_api.
 */
@RestController
@RequestMapping("/api")
public class Partner_apiResource {

    private final Logger log = LoggerFactory.getLogger(Partner_apiResource.class);

    @Inject
    private Partner_apiRepository partner_apiRepository;

    @Inject
    private Partner_apiSearchRepository partner_apiSearchRepository;

    /**
     * POST  /partner_apis -> Create a new partner_api.
     */
    @RequestMapping(value = "/partner_apis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partner_api> createPartner_api(@Valid @RequestBody Partner_api partner_api) throws URISyntaxException {
        log.debug("REST request to save Partner_api : {}", partner_api);
        if (partner_api.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new partner_api cannot already have an ID").body(null);
        }
        Partner_api result = partner_apiRepository.save(partner_api);
        partner_apiSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/partner_apis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("partner_api", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partner_apis -> Updates an existing partner_api.
     */
    @RequestMapping(value = "/partner_apis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partner_api> updatePartner_api(@Valid @RequestBody Partner_api partner_api) throws URISyntaxException {
        log.debug("REST request to update Partner_api : {}", partner_api);
        if (partner_api.getId() == null) {
            return createPartner_api(partner_api);
        }
        Partner_api result = partner_apiRepository.save(partner_api);
        partner_apiSearchRepository.save(partner_api);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("partner_api", partner_api.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partner_apis -> get all the partner_apis.
     */
    @RequestMapping(value = "/partner_apis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Partner_api>> getAllPartner_apis(Pageable pageable)
        throws URISyntaxException {
        Page<Partner_api> page = partner_apiRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/partner_apis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /partner_apis/:id -> get the "id" partner_api.
     */
    @RequestMapping(value = "/partner_apis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partner_api> getPartner_api(@PathVariable Long id) {
        log.debug("REST request to get Partner_api : {}", id);
        return Optional.ofNullable(partner_apiRepository.findOne(id))
            .map(partner_api -> new ResponseEntity<>(
                partner_api,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /partner_apis/:id -> delete the "id" partner_api.
     */
    @RequestMapping(value = "/partner_apis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePartner_api(@PathVariable Long id) {
        log.debug("REST request to delete Partner_api : {}", id);
        partner_apiRepository.delete(id);
        partner_apiSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("partner_api", id.toString())).build();
    }

    /**
     * SEARCH  /_search/partner_apis/:query -> search for the partner_api corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/partner_apis/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Partner_api> searchPartner_apis(@PathVariable String query) {
        return StreamSupport
            .stream(partner_apiSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
