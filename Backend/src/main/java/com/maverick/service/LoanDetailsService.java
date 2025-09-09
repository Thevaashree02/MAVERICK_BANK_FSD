package com.maverick.service;

import com.maverick.entity.LoanDetails;
import com.maverick.exception.InvalidInputException;
import com.maverick.repo.LoanDetailsRepository;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;

import java.math.BigDecimal;

import java.util.List;

@Service
public class LoanDetailsService {

    private LoanDetailsRepository loanDetailsRepository;

    public LoanDetailsService(LoanDetailsRepository loanDetailsRepository) {
        this.loanDetailsRepository = loanDetailsRepository;
    }

    public LoanDetails postLoanDetails(LoanDetails loanDetails) throws InvalidInputException {
        if (loanDetails.getPrincipalAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidInputException("Principal amount cannot be negative or zero");
        BigDecimal amount = loanDetails.getPrincipalAmount();
        if (loanDetails.getInterestRate().compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidInputException("Interest Rate cannot be negative or zero");
        loanDetails.setTotalRepayableAmount(amount.add(amount.multiply(loanDetails.getInterestRate().divide(BigDecimal.valueOf(100)))));
        if (loanDetails.getTermInMonth() <= 0)
            throw new InvalidInputException("Term in month cannot be negative or zero");
        loanDetails.setEmiAmount(loanDetails.getTotalRepayableAmount().divide(BigDecimal.valueOf(loanDetails.getTermInMonth()), 2, RoundingMode.HALF_UP));
        return loanDetailsRepository.save(loanDetails);
    }

    public LoanDetails getById(int id) {
        return loanDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
    }

    public List<LoanDetails> getAll() {
        return loanDetailsRepository.findAll();
    }

    public LoanDetails putLoanDetails(int id, LoanDetails loanDetails) throws InvalidInputException {
        LoanDetails dbLoanDetails = loanDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
        if(loanDetails.getLoanType() != null)
            dbLoanDetails.setLoanType(loanDetails.getLoanType());
        if(loanDetails.getPrincipalAmount() != null) {
            if (loanDetails.getPrincipalAmount().compareTo(BigDecimal.ZERO) <= 0)
                throw new InvalidInputException("Principal amount cannot be negative or zero");
            dbLoanDetails.setPrincipalAmount(loanDetails.getPrincipalAmount());
            BigDecimal amount = loanDetails.getPrincipalAmount();
            dbLoanDetails.setTotalRepayableAmount(amount.add(amount.multiply(dbLoanDetails.getInterestRate().divide(BigDecimal.valueOf(100)))));
            dbLoanDetails.setEmiAmount(dbLoanDetails.getTotalRepayableAmount().divide(BigDecimal.valueOf(dbLoanDetails.getTermInMonth()), 2, RoundingMode.HALF_UP));
        }
        if(loanDetails.getInterestRate() != null) {
            if (loanDetails.getInterestRate().compareTo(BigDecimal.ZERO) <= 0)
                throw new InvalidInputException("Interest Rate cannot be negative or zero");
            dbLoanDetails.setInterestRate(loanDetails.getInterestRate());
            BigDecimal amount = dbLoanDetails.getPrincipalAmount();
            dbLoanDetails.setTotalRepayableAmount(amount.add(amount.multiply(loanDetails.getInterestRate().divide(BigDecimal.valueOf(100)))));
            dbLoanDetails.setEmiAmount(dbLoanDetails.getTotalRepayableAmount().divide(BigDecimal.valueOf(dbLoanDetails.getTermInMonth()), 2, RoundingMode.HALF_UP));
        }
        if(loanDetails.getTermInMonth() != 0) {
            if (loanDetails.getTermInMonth() < 0)
                throw new InvalidInputException("Term in month cannot be negative");
            dbLoanDetails.setTermInMonth(loanDetails.getTermInMonth());
            dbLoanDetails.setEmiAmount(dbLoanDetails.getTotalRepayableAmount().divide(BigDecimal.valueOf(loanDetails.getTermInMonth()), 2, RoundingMode.HALF_UP));
        }
        return loanDetailsRepository.save(dbLoanDetails);
    }
}
