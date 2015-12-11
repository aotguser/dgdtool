package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Partner;
import com.asp.dgdtool.repository.PartnerRepository;
import com.asp.dgdtool.repository.search.PartnerSearchRepository;
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
 * REST controller for managing Partner.
 */
@RestController
@RequestMapping("/api")
public class PartnerResource {

    private final Logger log = LoggerFactory.getLogger(PartnerResource.class);

    @Inject
    private PartnerRepository partnerRepository;

    @Inject
    private PartnerSearchRepository partnerSearchRepository;

    /**
     * POST  /partners -> Create a new partner.
     */
    @RequestMapping(value = "/partners",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partner> createPartner(@Valid @RequestBody Partner partner) throws URISyntaxException {
        log.debug("REST request to save Partner : {}", partner);
        if (partner.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new partner cannot already have an ID").body(null);
        }
        Partner result = partnerRepository.save(partner);
        partnerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/partners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("partner", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partners -> Updates an existing partner.
     */
    @RequestMapping(value = "/partners",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partner> updatePartner(@Valid @RequestBody Partner partner) throws URISyntaxException {
        log.debug("REST request to update Partner : {}", partner);
        if (partner.getId() == null) {
            return createPartner(partner);
        }
        Partner result = partnerRepository.save(partner);
        partnerSearchRepository.save(partner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("partner", partner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partners -> get all the partners.
     */
    @RequestMapping(value = "/partners",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Partner>> getAllPartners(Pageable pageable)
        throws URISyntaxException {
        Page<Partner> page = partnerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/partners");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /partners/:id -> get the "id" partner.
     */
    @RequestMapping(value = "/partners/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partner> getPartner(@PathVariable Long id) {
        log.debug("REST request to get Partner : {}", id);
        return Optional.ofNullable(partnerRepository.findOne(id))
            .map(partner -> new ResponseEntity<>(
                partner,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /partners/:id -> delete the "id" partner.
     */
    @RequestMapping(value = "/partners/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        log.debug("REST request to delete Partner : {}", id);
        partnerRepository.delete(id);
        partnerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("partner", id.toString())).build();
    }

    /**
     * SEARCH  /_search/partners/:query -> search for the partner corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/partners/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Partner> searchPartners(@PathVariable String query) {
        return StreamSupport
            .stream(partnerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
