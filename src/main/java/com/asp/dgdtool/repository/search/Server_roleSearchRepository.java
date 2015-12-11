package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Server_role;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Server_role entity.
 */
public interface Server_roleSearchRepository extends ElasticsearchRepository<Server_role, Long> {
}
