package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Os_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Os_type entity.
 */
public interface Os_typeSearchRepository extends ElasticsearchRepository<Os_type, Long> {
}
