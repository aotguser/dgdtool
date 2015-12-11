package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Asset_type;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Asset_type entity.
 */
public interface Asset_typeRepository extends JpaRepository<Asset_type,Long> {

}
