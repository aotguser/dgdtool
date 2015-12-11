package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Platform;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Platform entity.
 */
public interface PlatformRepository extends JpaRepository<Platform,Long> {

}
