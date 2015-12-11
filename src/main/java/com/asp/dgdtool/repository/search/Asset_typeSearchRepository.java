package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Asset_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Asset_type entity.
 */
public interface Asset_typeSearchRepository extends ElasticsearchRepository<Asset_type, Long> {
}
