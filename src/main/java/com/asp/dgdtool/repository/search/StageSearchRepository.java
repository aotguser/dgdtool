package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Stage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Stage entity.
 */
public interface StageSearchRepository extends ElasticsearchRepository<Stage, Long> {
}
