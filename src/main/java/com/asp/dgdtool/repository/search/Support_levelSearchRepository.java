package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Support_level;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Support_level entity.
 */
public interface Support_levelSearchRepository extends ElasticsearchRepository<Support_level, Long> {
}
