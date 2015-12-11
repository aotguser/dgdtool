package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Packages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Packages entity.
 */
public interface PackagesSearchRepository extends ElasticsearchRepository<Packages, Long> {
}
