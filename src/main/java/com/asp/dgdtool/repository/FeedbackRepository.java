package com.asp.dgdtool.repository;

import com.asp.dgdtool.domain.Feedback;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Feedback entity.
 */
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

}
