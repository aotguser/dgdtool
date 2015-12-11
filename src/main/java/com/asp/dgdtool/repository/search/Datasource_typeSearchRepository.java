package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Datasource_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Datasource_type entity.
 */
public interface Datasource_typeSearchRepository extends ElasticsearchRepository<Datasource_type, Long> {
}
