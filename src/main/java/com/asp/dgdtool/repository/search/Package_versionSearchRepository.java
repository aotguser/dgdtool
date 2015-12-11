package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Package_version;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Package_version entity.
 */
public interface Package_versionSearchRepository extends ElasticsearchRepository<Package_version, Long> {
}
