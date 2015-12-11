package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Project_phase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Project_phase entity.
 */
public interface Project_phaseSearchRepository extends ElasticsearchRepository<Project_phase, Long> {
}
