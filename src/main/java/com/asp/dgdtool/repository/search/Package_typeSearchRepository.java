package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Package_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Package_type entity.
 */
public interface Package_typeSearchRepository extends ElasticsearchRepository<Package_type, Long> {
}
