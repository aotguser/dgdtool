package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Role;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Role entity.
 */
public interface RoleRepository extends JpaRepository<Role,Long> {

}
