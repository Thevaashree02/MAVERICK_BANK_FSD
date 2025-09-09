package com.maverick.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.maverick.entity.LoanRepayment;
import com.maverick.repo.LoanRepaymentRepository;
import com.maverick.repo.LoanRepository;
import com.maverick.entity.Loan;
import org.springframework.stereotype.Service;



@Service
public class LoanRepaymentService {

    private LoanRepaymentRepository loanRepaymentRepository;
    private LoanService loanService;
    private LoanRepository loanRepository;
    private TransactionService transactionService;

    public LoanRepaymentService(LoanRepaymentRepository loanRepaymentRepository, LoanService loanService,
                                LoanRepository loanRepository, TransactionService transactionService) {
        this.loanRepaymentRepository = loanRepaymentRepository;
        this.loanService = loanService;
        this.loanRepository = loanRepository;
        this.transactionService = transactionService;
    }

    // post loan repayment
    public LoanRepayment postLoanRepayment(int loanId, BigDecimal amount) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("ID is Invalid"));
        if (amount.compareTo(loan.getBalanceAmount()) == 1)
            throw new RuntimeException("You cannot pay more than the loan balance amount");
        LoanRepayment loanRepayment = new LoanRepayment();
        loanRepayment.setRepaymentAmount(amount);
        loanRepayment.setRepaymentDate(LocalDate.now());
        loanRepayment.setLoan(loan);
        transactionService.postLoanWithdraw(loan.getLoanApplication().getAccount().getId(), amount);
        loanService.putLoanBalance(loanId, amount);
        return loanRepaymentRepository.save(loanRepayment);
    }

    // fetch loan repayment by loan id
    public List<LoanRepayment> getByLoanId(int loanId) {
        return loanRepaymentRepository.getByLoanId(loanId);
    }

}