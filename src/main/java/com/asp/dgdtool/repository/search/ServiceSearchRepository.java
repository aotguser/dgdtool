package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Service;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Service entity.
 */
public interface ServiceSearchRepository extends ElasticsearchRepository<Service, Long> {
}
