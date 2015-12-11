package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.License_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the License_type entity.
 */
public interface License_typeSearchRepository extends ElasticsearchRepository<License_type, Long> {
}
