package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Resource;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Resource entity.
 */
public interface ResourceRepository extends JpaRepository<Resource,Long> {

}
