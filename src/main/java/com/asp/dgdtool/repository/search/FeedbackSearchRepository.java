package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Feedback;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Feedback entity.
 */
public interface FeedbackSearchRepository extends ElasticsearchRepository<Feedback, Long> {
}
