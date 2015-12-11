package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Menu;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Menu entity.
 */
public interface MenuRepository extends JpaRepository<Menu,Long> {

}
