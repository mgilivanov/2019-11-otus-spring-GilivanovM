package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mgilivanov.project.domain.*;
import ru.mgilivanov.project.exception.BusinessLogicException;
import ru.mgilivanov.project.model.credit.CreditInfoRequest;
import ru.mgilivanov.project.model.credit.CreditIssueRequest;
import ru.mgilivanov.project.model.credit.CreditPrepaymentRequest;
import ru.mgilivanov.project.repository.CreditRepository;
import ru.mgilivanov.project.repository.PrepaymentRepository;
import ru.mgilivanov.project.utils.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditPrepaymentServiceImpl implements CreditPrepaymentService {
    private final AccountService accountService;
    private final CreditService creditService;
    private final PrepaymentRepository prepaymentRepository;
    private final EodService eodService;

    @Override
    public Prepayment createPrepayment(CreditPrepaymentRequest request) {
        request.validate();
        if (request.getDate().isBefore(eodService.getEodDate())){
            throw new BusinessLogicException(BusinessLogicException.PREPAYMENT_BEFORE_EODDATE_CODE, BusinessLogicException.PREPAYMENT_BEFORE_EODDATE_MESSAGE);
        }
        return prepaymentRepository.save(
                new Prepayment()
                        .setCredit(creditService.findById(request.getCreditId())
                                .orElseThrow(() -> new BusinessLogicException(BusinessLogicException.CREDIT_NOT_FOUND_CODE, BusinessLogicException.CREDIT_NOT_FOUND_MESSAGE)))
                        .setDate(request.getDate())
                        .setFull(request.getIsFull())
                        .setSum(request.getSum())
                        .setCreateDate(LocalDateTime.now())
                        .setStatus(Prepayment.Status.NEW));
    }

    @Override
    public List<Prepayment> findPrepayments(Credit credit, LocalDate date, String status) {
        return prepaymentRepository.findAllByCreditAndDateAndStatus(credit, date, status);
    }

}
