package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.App;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the App entity.
 */
public interface AppRepository extends JpaRepository<App,Long> {

}
