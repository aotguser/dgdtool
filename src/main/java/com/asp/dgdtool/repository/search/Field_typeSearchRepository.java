package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Field_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Field_type entity.
 */
public interface Field_typeSearchRepository extends ElasticsearchRepository<Field_type, Long> {
}
