package ru.mgilivanov.project.controller;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;
import ru.mgilivanov.project.TestConfig;
import ru.mgilivanov.project.service.ClientService;
import ru.mgilivanov.project.service.CreditService;

@SpringBootTest(classes = {TestConfig.class, CreditController.class})
public class CreditControllerTest extends ControllerTest {

    @MockBean
    CreditService creditService;

    public CreditControllerTest(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @Test
    @WithMockUser(roles = {"CRM"})
    public void callCreditIssueCorrectData() throws Exception {
        checkCorrectJson(CreditController.CREDIT_ISSUE,
                "{\"client_id\": 0," +
                        "\"applicationId\": \"string\"," +
                        "\"percent_rate\": 0," +
                        "\"sum\": 0," +
                        "\"reg_payment\": 0," +
                        "\"first_stmt_date\": \"2020-05-06\"," +
                        "\"overdue_fee\": 0 }");
    }

    @Test
    @WithMockUser(roles = {"CRM"})
    public void callCreditIssueIncorrectData() throws Exception {
        checkInvalidJson(CreditController.CREDIT_ISSUE,
                "{\"client_id\": 0," +
                        "\"applicationId\": \"string\"," +
                        "\"percent_rate\": 0," +
                        "\"sum\": -100," +
                        "\"reg_payment\": 0," +
                        "\"first_stmt_date\": \"2020-05-06\"," +
                        "\"overdue_fee\": 0 }");
    }

    @Test
    @WithMockUser(roles = {"ABS"})
    public void callCreditIssueForbidden() throws Exception {
        checkForbidden(CreditController.CREDIT_ISSUE,
                "{\"client_id\": 0," +
                        "\"applicationId\": \"string\"," +
                        "\"percent_rate\": 0," +
                        "\"sum\": 0," +
                        "\"reg_payment\": 0," +
                        "\"first_stmt_date\": \"2020-05-06\"," +
                        "\"overdue_fee\": 0 }");
    }
}
