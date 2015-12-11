package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Frequency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Frequency entity.
 */
public interface FrequencySearchRepository extends ElasticsearchRepository<Frequency, Long> {
}
