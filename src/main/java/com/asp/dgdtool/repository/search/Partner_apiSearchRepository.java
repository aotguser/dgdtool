package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Partner_api;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Partner_api entity.
 */
public interface Partner_apiSearchRepository extends ElasticsearchRepository<Partner_api, Long> {
}
