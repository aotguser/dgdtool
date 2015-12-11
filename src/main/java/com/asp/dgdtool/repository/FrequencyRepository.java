package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Frequency;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Frequency entity.
 */
public interface FrequencyRepository extends JpaRepository<Frequency,Long> {

}
