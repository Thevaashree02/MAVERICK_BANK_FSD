package com.maverick.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.maverick.enums.AccountStatus;
import com.maverick.exception.ResourceNotFoundException;
import com.maverick.entity.Account;
import com.maverick.entity.Beneficiary;
import com.maverick.entity.Customer;
import com.maverick.repo.AccountRepository;
import com.maverick.repo.BeneficiaryRepository;
import com.maverick.repo.BranchRepository;
import com.maverick.repo.CustomerRepository;

@Service
public class BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;

    public BeneficiaryService(BeneficiaryRepository beneficiaryRepository,
                              CustomerRepository customerRepository,
                              AccountRepository accountRepository,
                              BranchRepository branchRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.branchRepository = branchRepository;
    }

    // insert values into beneficiary
    public Beneficiary postBeneficiary(String username, Beneficiary beneficiaryRequest) {
        // logged-in customer
        Customer loggedInCustomer = customerRepository.getCustomerByUsername(username);

        // find account using Aadhaar number
        Account account = accountRepository.findByAadharNumber(beneficiaryRequest.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Invalid Aadhaar Number"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Account is not active");
        }

        // get branch from IFSC
        var branch = branchRepository.getByIfscCode(beneficiaryRequest.getIfscCode())
                .orElseThrow(() -> new RuntimeException("Invalid IFSC Code"));

        // beneficiary’s actual customer from account
        Customer beneficiaryCustomer = account.getCustomer();

        // build beneficiary object
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setAccountNumber(String.valueOf(account.getId()));
        beneficiary.setIfscCode(branch.getIfscCode());
        beneficiary.setBranchName(branch.getBranchName());
        beneficiary.setName(beneficiaryCustomer.getFirstName() + " " + beneficiaryCustomer.getLastName());
        beneficiary.setDescription(beneficiaryRequest.getDescription());
        beneficiary.setAddedOn(LocalDate.now());
        beneficiary.setCustomer(loggedInCustomer); // owner of this beneficiary

        return beneficiaryRepository.save(beneficiary);
    }

    // update beneficiary
    public Beneficiary putBeneficiary(int id, int customerId, Beneficiary beneficiary) {
        Beneficiary dbBeneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID is Invalid"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer ID is Invalid"));

        if (beneficiary.getDescription() != null) {
            dbBeneficiary.setDescription(beneficiary.getDescription());
        }
        dbBeneficiary.setCustomer(customer);

        return beneficiaryRepository.save(dbBeneficiary);
    }

    // fetch by customer username
    public List<Beneficiary> getByCustomerUsername(String username) throws ResourceNotFoundException {
        Customer customer = customerRepository.getCustomerByUsername(username);
        return beneficiaryRepository.getByCustomerId(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    // fetch by id
    public Beneficiary getById(int id) {
        return beneficiaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID is Invalid"));
    }

    // fetch all beneficiary
    public List<Beneficiary> getAll() {
        return beneficiaryRepository.findAll();
    }

    // delete beneficiary by id
    public void deleteById(int id) {
        beneficiaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID is Invalid"));
        beneficiaryRepository.deleteById(id);
    }
}

