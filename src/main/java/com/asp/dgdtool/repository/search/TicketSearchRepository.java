package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Ticket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Ticket entity.
 */
public interface TicketSearchRepository extends ElasticsearchRepository<Ticket, Long> {
}
