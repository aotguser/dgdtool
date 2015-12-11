package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Project_team;
import com.asp.dgdtool.repository.Project_teamRepository;
import com.asp.dgdtool.repository.search.Project_teamSearchRepository;
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
 * REST controller for managing Project_team.
 */
@RestController
@RequestMapping("/api")
public class Project_teamResource {

    private final Logger log = LoggerFactory.getLogger(Project_teamResource.class);

    @Inject
    private Project_teamRepository project_teamRepository;

    @Inject
    private Project_teamSearchRepository project_teamSearchRepository;

    /**
     * POST  /project_teams -> Create a new project_team.
     */
    @RequestMapping(value = "/project_teams",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project_team> createProject_team(@Valid @RequestBody Project_team project_team) throws URISyntaxException {
        log.debug("REST request to save Project_team : {}", project_team);
        if (project_team.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new project_team cannot already have an ID").body(null);
        }
        Project_team result = project_teamRepository.save(project_team);
        project_teamSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/project_teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("project_team", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project_teams -> Updates an existing project_team.
     */
    @RequestMapping(value = "/project_teams",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project_team> updateProject_team(@Valid @RequestBody Project_team project_team) throws URISyntaxException {
        log.debug("REST request to update Project_team : {}", project_team);
        if (project_team.getId() == null) {
            return createProject_team(project_team);
        }
        Project_team result = project_teamRepository.save(project_team);
        project_teamSearchRepository.save(project_team);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("project_team", project_team.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project_teams -> get all the project_teams.
     */
    @RequestMapping(value = "/project_teams",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Project_team>> getAllProject_teams(Pageable pageable)
        throws URISyntaxException {
        Page<Project_team> page = project_teamRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project_teams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /project_teams/:id -> get the "id" project_team.
     */
    @RequestMapping(value = "/project_teams/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project_team> getProject_team(@PathVariable Long id) {
        log.debug("REST request to get Project_team : {}", id);
        return Optional.ofNullable(project_teamRepository.findOne(id))
            .map(project_team -> new ResponseEntity<>(
                project_team,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /project_teams/:id -> delete the "id" project_team.
     */
    @RequestMapping(value = "/project_teams/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProject_team(@PathVariable Long id) {
        log.debug("REST request to delete Project_team : {}", id);
        project_teamRepository.delete(id);
        project_teamSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("project_team", id.toString())).build();
    }

    /**
     * SEARCH  /_search/project_teams/:query -> search for the project_team corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/project_teams/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Project_team> searchProject_teams(@PathVariable String query) {
        return StreamSupport
            .stream(project_teamSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
