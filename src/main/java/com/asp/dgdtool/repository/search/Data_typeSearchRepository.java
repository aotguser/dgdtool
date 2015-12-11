package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Data_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Data_type entity.
 */
public interface Data_typeSearchRepository extends ElasticsearchRepository<Data_type, Long> {
}
