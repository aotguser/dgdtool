package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Environment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Environment entity.
 */
public interface EnvironmentSearchRepository extends ElasticsearchRepository<Environment, Long> {
}
