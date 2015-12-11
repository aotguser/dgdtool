package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Project_team;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Project_team entity.
 */
public interface Project_teamSearchRepository extends ElasticsearchRepository<Project_team, Long> {
}
