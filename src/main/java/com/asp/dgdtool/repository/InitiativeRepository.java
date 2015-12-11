package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Initiative;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Initiative entity.
 */
public interface InitiativeRepository extends JpaRepository<Initiative,Long> {

}
