package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Data_state;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Data_state entity.
 */
public interface Data_stateSearchRepository extends ElasticsearchRepository<Data_state, Long> {
}
