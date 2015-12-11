package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Statuses;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Statuses entity.
 */
public interface StatusesSearchRepository extends ElasticsearchRepository<Statuses, Long> {
}
