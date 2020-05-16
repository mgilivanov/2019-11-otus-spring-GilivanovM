package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mgilivanov.project.domain.Account;
import ru.mgilivanov.project.domain.AccountType;
import ru.mgilivanov.project.domain.PayDocument;
import ru.mgilivanov.project.exception.BusinessLogicException;
import ru.mgilivanov.project.model.paydoc.PayDocumentCreateRequest;
import ru.mgilivanov.project.repository.PayDocumentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayDocumentServiceImpl implements PayDocumentService {

    private final AccountService accountService;
    private final EodService eodService;
    private final PayDocumentRepository payDocumentRepository;

    @Override
    public PayDocument create(String type, LocalDate eodDate, Account accountDt, Account accountKt, Double sum) {
        PayDocument payDocument = new PayDocument()
                .setType(type)
                .setEodDate(eodDate)
                .setAccountDt(accountDt)
                .setAccountKt(accountKt)
                .setSum(sum)
                .setStatus(PayDocument.Status.NEW);
        payDocumentRepository.save(payDocument);
        return process(payDocument);
    }

    @Override
    public PayDocument create(PayDocumentCreateRequest request){
        request.validate();

        Account accountDt = (request.getAccountDt() == null) ? accountService.getBankAccountDt() : accountService.findById(request.getAccountDt())
                .orElseThrow(() -> new BusinessLogicException(BusinessLogicException.DOCUMENT_NOT_FOUND_DT_CODE, BusinessLogicException.DOCUMENT_NOT_FOUND_DT_MESSAGE));
        Account accountKt = (request.getAccountKt() == null) ? accountService.getBankAccountKt() : accountService.findById(request.getAccountKt())
                .orElseThrow(() -> new BusinessLogicException(BusinessLogicException.DOCUMENT_NOT_FOUND_KT_CODE, BusinessLogicException.DOCUMENT_NOT_FOUND_KT_MESSAGE));
        if ((accountDt.getAccountType().getCode() == AccountType.SERVICE) && eodService.isDayClosing()){
            throw new BusinessLogicException(BusinessLogicException.DOCUMENT_DAY_CLOSING_CODE, BusinessLogicException.DOCUMENT_DAY_CLOSING_MESSAGE);
        }
        if ((accountDt.getAccountType().isCreditAccount() && !accountDt.getAccountType().getCode().equals(AccountType.SERVICE)) ||
                (accountKt.getAccountType().isCreditAccount() && !accountKt.getAccountType().getCode().equals(AccountType.SERVICE))){
            throw new BusinessLogicException(BusinessLogicException.DOCUMENT_EXTERNAL_CREDIT_PROHIBITED_CODE, BusinessLogicException.DOCUMENT_EXTERNAL_CREDIT_PROHIBITED_MESSAGE);
        }
        LocalDate eodDate = (request.getEodDate() == null) ? eodService.getEodDate() : request.getEodDate();
        PayDocument payDocument = new PayDocument()
                .setType(request.getType())
                .setEodDate(eodDate)
                .setAccountDt(accountDt)
                .setAccountKt(accountKt)
                .setExternalAccountDt(request.getExternalAccountDt())
                .setExternalAccountKt(request.getExternalAccountKt())
                .setSum(request.getSum())
                .setStatus(PayDocument.Status.WAIT_VALUE_DATE)
                .setExternal(true);
        payDocumentRepository.save(payDocument);
        return process(payDocument);
    }

    @Override
    public PayDocument process(PayDocument payDocument){
        if (payDocument.getStatus() == PayDocument.Status.COMPLETED){
            throw new BusinessLogicException(BusinessLogicException.DOCUMENT_ALREADY_PROCESSED_CODE, BusinessLogicException.DOCUMENT_ALREADY_PROCESSED_MESSAGE);
        }
        boolean isDayClosing = eodService.isDayClosing();
        if ((isDayClosing && payDocument.getEodDate().isBefore(eodService.getEodDate()) && payDocument.isExternal()) ||
                (isDayClosing && payDocument.getEodDate().isBefore(eodService.getPrevEodDate()) && !payDocument.isExternal()) ||
                (!isDayClosing && payDocument.getEodDate().isBefore(eodService.getEodDate())))
        {
            throw new BusinessLogicException(BusinessLogicException.OPERATION_DAY_CLOSED_CODE, BusinessLogicException.OPERATION_DAY_CLOSED_MESSAGE);
        }
        if ((payDocument.getEodDate().isAfter(eodService.getEodDate())) || (isDayClosing && payDocument.isExternal())){
            if (payDocument.getStatus() == PayDocument.Status.WAIT_VALUE_DATE) {return payDocument;}
            payDocument.setStatus(PayDocument.Status.WAIT_VALUE_DATE);
        }
        else {
            accountService.debit(payDocument.getAccountDt(), payDocument.getSum());
            accountService.credit(payDocument.getAccountKt(), payDocument.getSum());
            payDocument.setStatus(PayDocument.Status.COMPLETED).setOperationDate(LocalDateTime.now());
        }
        payDocumentRepository.save(payDocument);
        return payDocument;
    }

    @Override
    public void processAllValueDateDocs(LocalDate date){
        payDocumentRepository.findAllByEodDateAndStatus(date, PayDocument.Status.WAIT_VALUE_DATE).forEach(i -> process(i));
    }

    @Override
    public List<PayDocument> findAllByEodDate(LocalDate date){
        return payDocumentRepository.findAllByEodDate(date);
    }
}
