package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.App;
import com.asp.dgdtool.repository.AppRepository;
import com.asp.dgdtool.repository.search.AppSearchRepository;
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
 * REST controller for managing App.
 */
@RestController
@RequestMapping("/api")
public class AppResource {

    private final Logger log = LoggerFactory.getLogger(AppResource.class);

    @Inject
    private AppRepository appRepository;

    @Inject
    private AppSearchRepository appSearchRepository;

    /**
     * POST  /apps -> Create a new app.
     */
    @RequestMapping(value = "/apps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<App> createApp(@Valid @RequestBody App app) throws URISyntaxException {
        log.debug("REST request to save App : {}", app);
        if (app.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new app cannot already have an ID").body(null);
        }
        App result = appRepository.save(app);
        appSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("app", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apps -> Updates an existing app.
     */
    @RequestMapping(value = "/apps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<App> updateApp(@Valid @RequestBody App app) throws URISyntaxException {
        log.debug("REST request to update App : {}", app);
        if (app.getId() == null) {
            return createApp(app);
        }
        App result = appRepository.save(app);
        appSearchRepository.save(app);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("app", app.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apps -> get all the apps.
     */
    @RequestMapping(value = "/apps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<App>> getAllApps(Pageable pageable)
        throws URISyntaxException {
        Page<App> page = appRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/apps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /apps/:id -> get the "id" app.
     */
    @RequestMapping(value = "/apps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<App> getApp(@PathVariable Long id) {
        log.debug("REST request to get App : {}", id);
        return Optional.ofNullable(appRepository.findOne(id))
            .map(app -> new ResponseEntity<>(
                app,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /apps/:id -> delete the "id" app.
     */
    @RequestMapping(value = "/apps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApp(@PathVariable Long id) {
        log.debug("REST request to delete App : {}", id);
        appRepository.delete(id);
        appSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("app", id.toString())).build();
    }

    /**
     * SEARCH  /_search/apps/:query -> search for the app corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/apps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<App> searchApps(@PathVariable String query) {
        return StreamSupport
            .stream(appSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
