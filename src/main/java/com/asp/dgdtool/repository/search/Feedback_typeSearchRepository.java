package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Feedback_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Feedback_type entity.
 */
public interface Feedback_typeSearchRepository extends ElasticsearchRepository<Feedback_type, Long> {
}
