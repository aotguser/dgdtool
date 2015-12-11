package com.asp.dgdtool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.asp.dgdtool.domain.Ticket;
import com.asp.dgdtool.repository.TicketRepository;
import com.asp.dgdtool.repository.search.TicketSearchRepository;
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
 * REST controller for managing Ticket.
 */
@RestController
@RequestMapping("/api")
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);

    @Inject
    private TicketRepository ticketRepository;

    @Inject
    private TicketSearchRepository ticketSearchRepository;

    /**
     * POST  /tickets -> Create a new ticket.
     */
    @RequestMapping(value = "/tickets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody Ticket ticket) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticket);
        if (ticket.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new ticket cannot already have an ID").body(null);
        }
        Ticket result = ticketRepository.save(ticket);
        ticketSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ticket", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tickets -> Updates an existing ticket.
     */
    @RequestMapping(value = "/tickets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ticket> updateTicket(@Valid @RequestBody Ticket ticket) throws URISyntaxException {
        log.debug("REST request to update Ticket : {}", ticket);
        if (ticket.getId() == null) {
            return createTicket(ticket);
        }
        Ticket result = ticketRepository.save(ticket);
        ticketSearchRepository.save(ticket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ticket", ticket.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tickets -> get all the tickets.
     */
    @RequestMapping(value = "/tickets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Ticket>> getAllTickets(Pageable pageable)
        throws URISyntaxException {
        Page<Ticket> page = ticketRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tickets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tickets/:id -> get the "id" ticket.
     */
    @RequestMapping(value = "/tickets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        log.debug("REST request to get Ticket : {}", id);
        return Optional.ofNullable(ticketRepository.findOne(id))
            .map(ticket -> new ResponseEntity<>(
                ticket,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tickets/:id -> delete the "id" ticket.
     */
    @RequestMapping(value = "/tickets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        log.debug("REST request to delete Ticket : {}", id);
        ticketRepository.delete(id);
        ticketSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ticket", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tickets/:query -> search for the ticket corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/tickets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ticket> searchTickets(@PathVariable String query) {
        return StreamSupport
            .stream(ticketSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
