package com.maverick.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maverick.entity.LoanRepayment;

public interface LoanRepaymentRepository extends JpaRepository<LoanRepayment, Integer>{

    @Query("select lr from LoanRepayment lr where lr.loan.id=?1")
    List<LoanRepayment> getByLoanId(int loanId);
}