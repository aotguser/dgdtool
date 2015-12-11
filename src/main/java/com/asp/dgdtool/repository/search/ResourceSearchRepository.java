package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Resource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Resource entity.
 */
public interface ResourceSearchRepository extends ElasticsearchRepository<Resource, Long> {
}
