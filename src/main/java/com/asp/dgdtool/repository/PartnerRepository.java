package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Partner;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Partner entity.
 */
public interface PartnerRepository extends JpaRepository<Partner,Long> {

}
