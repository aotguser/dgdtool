package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Statuses;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Statuses entity.
 */
public interface StatusesRepository extends JpaRepository<Statuses,Long> {

}
