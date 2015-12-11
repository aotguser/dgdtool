package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Ticket;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ticket entity.
 */
public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
