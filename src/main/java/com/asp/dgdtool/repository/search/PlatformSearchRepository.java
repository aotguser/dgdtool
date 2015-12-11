package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Platform;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Platform entity.
 */
public interface PlatformSearchRepository extends ElasticsearchRepository<Platform, Long> {
}
