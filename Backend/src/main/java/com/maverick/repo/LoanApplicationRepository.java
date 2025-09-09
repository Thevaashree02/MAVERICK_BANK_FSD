package com.maverick.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maverick.enums.LoanType;
import com.maverick.entity.Customer;
import com.maverick.entity.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Integer>{

    @Query("select la from LoanApplication la where la.account.customer.user.username=?1")
    List<LoanApplication> getByUsername(String username);

    @Query("select la from LoanApplication la where la.status=?1")
    List<LoanApplication> getByStatus(String status);

    @Query("select la from LoanApplication la where la.account.branch.id=?1")
    List<LoanApplication> getByBranchId(String branchId);

    @Query("select count(la)>0 from LoanApplication la where la.account.customer=?1 and la.loanDetails.loanType=?2")
    boolean getLoanAppExistsByCustomerAndType(Customer customer, LoanType loanType);

}