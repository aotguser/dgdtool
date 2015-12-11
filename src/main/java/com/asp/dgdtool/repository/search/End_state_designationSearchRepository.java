package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.End_state_designation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the End_state_designation entity.
 */
public interface End_state_designationSearchRepository extends ElasticsearchRepository<End_state_designation, Long> {
}
