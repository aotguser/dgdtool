package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Frequency;
import com.asp.dgdtool.repository.FrequencyRepository;
import com.asp.dgdtool.repository.search.FrequencySearchRepository;
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
 * REST controller for managing Frequency.
 */
@RestController
@RequestMapping("/api")
public class FrequencyResource {

    private final Logger log = LoggerFactory.getLogger(FrequencyResource.class);

    @Inject
    private FrequencyRepository frequencyRepository;

    @Inject
    private FrequencySearchRepository frequencySearchRepository;

    /**
     * POST  /frequencys -> Create a new frequency.
     */
    @RequestMapping(value = "/frequencys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Frequency> createFrequency(@Valid @RequestBody Frequency frequency) throws URISyntaxException {
        log.debug("REST request to save Frequency : {}", frequency);
        if (frequency.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new frequency cannot already have an ID").body(null);
        }
        Frequency result = frequencyRepository.save(frequency);
        frequencySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/frequencys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("frequency", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /frequencys -> Updates an existing frequency.
     */
    @RequestMapping(value = "/frequencys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Frequency> updateFrequency(@Valid @RequestBody Frequency frequency) throws URISyntaxException {
        log.debug("REST request to update Frequency : {}", frequency);
        if (frequency.getId() == null) {
            return createFrequency(frequency);
        }
        Frequency result = frequencyRepository.save(frequency);
        frequencySearchRepository.save(frequency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("frequency", frequency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /frequencys -> get all the frequencys.
     */
    @RequestMapping(value = "/frequencys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Frequency>> getAllFrequencys(Pageable pageable)
        throws URISyntaxException {
        Page<Frequency> page = frequencyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/frequencys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /frequencys/:id -> get the "id" frequency.
     */
    @RequestMapping(value = "/frequencys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Frequency> getFrequency(@PathVariable Long id) {
        log.debug("REST request to get Frequency : {}", id);
        return Optional.ofNullable(frequencyRepository.findOne(id))
            .map(frequency -> new ResponseEntity<>(
                frequency,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /frequencys/:id -> delete the "id" frequency.
     */
    @RequestMapping(value = "/frequencys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFrequency(@PathVariable Long id) {
        log.debug("REST request to delete Frequency : {}", id);
        frequencyRepository.delete(id);
        frequencySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("frequency", id.toString())).build();
    }

    /**
     * SEARCH  /_search/frequencys/:query -> search for the frequency corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/frequencys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Frequency> searchFrequencys(@PathVariable String query) {
        return StreamSupport
            .stream(frequencySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
