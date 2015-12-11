package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Role;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Role entity.
 */
public interface RoleSearchRepository extends ElasticsearchRepository<Role, Long> {
}
