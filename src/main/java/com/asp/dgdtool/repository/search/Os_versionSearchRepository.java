package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Os_version;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Os_version entity.
 */
public interface Os_versionSearchRepository extends ElasticsearchRepository<Os_version, Long> {
}
