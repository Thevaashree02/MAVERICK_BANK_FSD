package com.maverick.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maverick.entity.LoanDetails;

public interface LoanDetailsRepository extends JpaRepository<LoanDetails, Integer>{

}