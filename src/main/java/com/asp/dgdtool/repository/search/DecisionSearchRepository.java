package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Decision;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Decision entity.
 */
public interface DecisionSearchRepository extends ElasticsearchRepository<Decision, Long> {
}
