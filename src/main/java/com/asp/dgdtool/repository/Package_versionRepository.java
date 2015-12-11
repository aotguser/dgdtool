package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Package_version;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Package_version entity.
 */
public interface Package_versionRepository extends JpaRepository<Package_version,Long> {

}
