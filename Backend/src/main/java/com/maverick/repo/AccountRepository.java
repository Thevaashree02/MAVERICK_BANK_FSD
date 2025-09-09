package com.maverick.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maverick.entity.Account;
import com.maverick.entity.AccountType;
import com.maverick.entity.Customer;

public interface AccountRepository extends JpaRepository<Account, Integer>{

    @Query("select a from Account a where a.customer.id=?1")
    Optional<List<Account>> getByCustomerId(int customerId);

    @Query("select a from Account a where a.branch.id=?1")
    Optional<List<Account>> getByBranchId(int branchId);

    @Query("select a from Account a where a.status=?1")
    Optional<List<Account>> getByStatus(String status);

    @Query("select a from Account a where a.customer.user.username=?1")
    List<Account> getByUsername(String username);

    @Query("select count(a)>0 from Account a where a.customer=?1 and a.accountType=?2 and a.status != 'CLOSED'")
    boolean getAccountExistsByCustomerandType(Customer customer, AccountType accountType);

    @Query("select a from Account a where a.aadharNumber = ?1")
    Optional<Account> findByAadharNumber(String aadharNumber);

}