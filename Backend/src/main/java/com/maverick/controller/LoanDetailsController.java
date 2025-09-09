package com.maverick.controller;

import java.util.List;

import com.maverick.entity.LoanDetails;
import com.maverick.exception.InvalidInputException;
import com.maverick.service.LoanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loanDetails")
@CrossOrigin(origins = "http://localhost:5173")
public class LoanDetailsController {

    @Autowired
    private LoanDetailsService loanDetailsService;

    @PostMapping("/post")
    public LoanDetails postLoanDetails(@RequestBody LoanDetails loanDetails) throws InvalidInputException {
        return loanDetailsService.postLoanDetails(loanDetails);
    }

    @PutMapping("/put/{id}")
    public LoanDetails putLoanDetails(@PathVariable int id, @RequestBody LoanDetails loanDetails) throws InvalidInputException {
        return loanDetailsService.putLoanDetails(id, loanDetails);
    }

    @GetMapping("/get/id/{id}")
    public LoanDetails getById(@PathVariable int id) {
        return loanDetailsService.getById(id);
    }

    @GetMapping("/get/all")
    public List<LoanDetails> getAll() {
        return loanDetailsService.getAll();
    }
}