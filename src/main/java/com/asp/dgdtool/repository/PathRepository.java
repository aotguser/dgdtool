package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Path;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Path entity.
 */
public interface PathRepository extends JpaRepository<Path,Long> {

}
