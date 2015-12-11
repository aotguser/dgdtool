package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.App_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the App_type entity.
 */
public interface App_typeSearchRepository extends ElasticsearchRepository<App_type, Long> {
}
