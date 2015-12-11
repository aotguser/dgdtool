package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Status_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Status_type entity.
 */
public interface Status_typeSearchRepository extends ElasticsearchRepository<Status_type, Long> {
}
