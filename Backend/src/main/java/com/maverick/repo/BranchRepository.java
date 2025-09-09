package com.maverick.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maverick.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer>{

    @Query("select b from Branch b where b.ifscCode=?1")
    Optional<Branch> getByIfscCode(String ifscCode);		// user written JPQL to fetch branch by ifscCode
    Optional<Branch> findByIfscCode(String ifscCode);	// inbuit by Jpa writes JPQL to fetch branch by ifscCode

    @Query("select b from Branch b where b.ifscCode=?1 and b.branchName=?2")
    Optional<Branch> getBranchByNameIfsc(String ifscCode, String branchName);
}