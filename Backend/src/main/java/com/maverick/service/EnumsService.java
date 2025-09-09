package com.maverick.service;

import java.util.Arrays;
import java.util.List;

import com.maverick.dto.TransferTypeDto;
import com.maverick.enums.*;
import org.springframework.stereotype.Service;



@Service
public class EnumsService {

    // fetch account status
    public List<String> getEnumAccountStatus() {
        return Arrays.stream(AccountStatus.values()).map(as -> as.name()).toList();
    }

    // fetch loan application status from enum
    public List<String> getEnumLoanApplicationStatus() {
        return Arrays.stream(LoanApplicationStatus.values()).map(la -> la.name()).toList();
    }

    // fetch loan status from enum
    public List<String> getEnumLoanStatus() {
        return Arrays.stream(LoanStatus.values()).map(l -> l.name()).toList();
    }

    // fetch loan type status from enum
    public List<String> getEnumLoanType() {
        return Arrays.stream(LoanType.values()).map(t -> t.name()).toList();
    }

    // fetch transfer type from enum
    public List<TransferTypeDto> getEnumTransferType() {
        return Arrays.stream(TransferType.values()).map(t -> new TransferTypeDto(t.name(), t.getCharge())).toList();
    }
}