package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Path;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Path entity.
 */
public interface PathSearchRepository extends ElasticsearchRepository<Path, Long> {
}
