package com.asp.dgdtool.repository.search;

import com.asp.dgdtool.domain.Menu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Menu entity.
 */
public interface MenuSearchRepository extends ElasticsearchRepository<Menu, Long> {
}
