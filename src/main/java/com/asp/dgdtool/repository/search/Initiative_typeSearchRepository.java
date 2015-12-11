package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Initiative_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Initiative_type entity.
 */
public interface Initiative_typeSearchRepository extends ElasticsearchRepository<Initiative_type, Long> {
}
