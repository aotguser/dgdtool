package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Project_phase;
import com.asp.dgdtool.repository.Project_phaseRepository;
import com.asp.dgdtool.repository.search.Project_phaseSearchRepository;
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
 * REST controller for managing Project_phase.
 */
@RestController
@RequestMapping("/api")
public class Project_phaseResource {

    private final Logger log = LoggerFactory.getLogger(Project_phaseResource.class);

    @Inject
    private Project_phaseRepository project_phaseRepository;

    @Inject
    private Project_phaseSearchRepository project_phaseSearchRepository;

    /**
     * POST  /project_phases -> Create a new project_phase.
     */
    @RequestMapping(value = "/project_phases",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project_phase> createProject_phase(@Valid @RequestBody Project_phase project_phase) throws URISyntaxException {
        log.debug("REST request to save Project_phase : {}", project_phase);
        if (project_phase.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new project_phase cannot already have an ID").body(null);
        }
        Project_phase result = project_phaseRepository.save(project_phase);
        project_phaseSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/project_phases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("project_phase", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project_phases -> Updates an existing project_phase.
     */
    @RequestMapping(value = "/project_phases",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project_phase> updateProject_phase(@Valid @RequestBody Project_phase project_phase) throws URISyntaxException {
        log.debug("REST request to update Project_phase : {}", project_phase);
        if (project_phase.getId() == null) {
            return createProject_phase(project_phase);
        }
        Project_phase result = project_phaseRepository.save(project_phase);
        project_phaseSearchRepository.save(project_phase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("project_phase", project_phase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project_phases -> get all the project_phases.
     */
    @RequestMapping(value = "/project_phases",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Project_phase>> getAllProject_phases(Pageable pageable)
        throws URISyntaxException {
        Page<Project_phase> page = project_phaseRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project_phases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /project_phases/:id -> get the "id" project_phase.
     */
    @RequestMapping(value = "/project_phases/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project_phase> getProject_phase(@PathVariable Long id) {
        log.debug("REST request to get Project_phase : {}", id);
        return Optional.ofNullable(project_phaseRepository.findOne(id))
            .map(project_phase -> new ResponseEntity<>(
                project_phase,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /project_phases/:id -> delete the "id" project_phase.
     */
    @RequestMapping(value = "/project_phases/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProject_phase(@PathVariable Long id) {
        log.debug("REST request to delete Project_phase : {}", id);
        project_phaseRepository.delete(id);
        project_phaseSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("project_phase", id.toString())).build();
    }

    /**
     * SEARCH  /_search/project_phases/:query -> search for the project_phase corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/project_phases/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Project_phase> searchProject_phases(@PathVariable String query) {
        return StreamSupport
            .stream(project_phaseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
