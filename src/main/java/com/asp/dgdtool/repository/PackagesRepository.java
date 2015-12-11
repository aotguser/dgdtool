package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Packages;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Packages entity.
 */
public interface PackagesRepository extends JpaRepository<Packages,Long> {

}
