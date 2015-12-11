package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Decision;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Decision entity.
 */
public interface DecisionRepository extends JpaRepository<Decision,Long> {

}
