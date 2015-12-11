package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Partner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Partner entity.
 */
public interface PartnerSearchRepository extends ElasticsearchRepository<Partner, Long> {
}
