package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Initiative;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Initiative entity.
 */
public interface InitiativeSearchRepository extends ElasticsearchRepository<Initiative, Long> {
}
