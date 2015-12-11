package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Server_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Server_type entity.
 */
public interface Server_typeSearchRepository extends ElasticsearchRepository<Server_type, Long> {
}
