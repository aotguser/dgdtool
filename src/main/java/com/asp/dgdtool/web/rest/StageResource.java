package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Stage;
import com.asp.dgdtool.repository.StageRepository;
import com.asp.dgdtool.repository.search.StageSearchRepository;
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
 * REST controller for managing Stage.
 */
@RestController
@RequestMapping("/api")
public class StageResource {

    private final Logger log = LoggerFactory.getLogger(StageResource.class);

    @Inject
    private StageRepository stageRepository;

    @Inject
    private StageSearchRepository stageSearchRepository;

    /**
     * POST  /stages -> Create a new stage.
     */
    @RequestMapping(value = "/stages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Stage> createStage(@Valid @RequestBody Stage stage) throws URISyntaxException {
        log.debug("REST request to save Stage : {}", stage);
        if (stage.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new stage cannot already have an ID").body(null);
        }
        Stage result = stageRepository.save(stage);
        stageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stages -> Updates an existing stage.
     */
    @RequestMapping(value = "/stages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Stage> updateStage(@Valid @RequestBody Stage stage) throws URISyntaxException {
        log.debug("REST request to update Stage : {}", stage);
        if (stage.getId() == null) {
            return createStage(stage);
        }
        Stage result = stageRepository.save(stage);
        stageSearchRepository.save(stage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stage", stage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stages -> get all the stages.
     */
    @RequestMapping(value = "/stages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Stage>> getAllStages(Pageable pageable)
        throws URISyntaxException {
        Page<Stage> page = stageRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stages/:id -> get the "id" stage.
     */
    @RequestMapping(value = "/stages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Stage> getStage(@PathVariable Long id) {
        log.debug("REST request to get Stage : {}", id);
        return Optional.ofNullable(stageRepository.findOne(id))
            .map(stage -> new ResponseEntity<>(
                stage,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stages/:id -> delete the "id" stage.
     */
    @RequestMapping(value = "/stages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        log.debug("REST request to delete Stage : {}", id);
        stageRepository.delete(id);
        stageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stages/:query -> search for the stage corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/stages/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Stage> searchStages(@PathVariable String query) {
        return StreamSupport
            .stream(stageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
