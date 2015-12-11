package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.App;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the App entity.
 */
public interface AppSearchRepository extends ElasticsearchRepository<App, Long> {
}
