package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Security_solution;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Security_solution entity.
 */
public interface Security_solutionSearchRepository extends ElasticsearchRepository<Security_solution, Long> {
}
