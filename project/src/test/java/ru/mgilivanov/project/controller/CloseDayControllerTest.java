package ru.mgilivanov.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;
import ru.mgilivanov.project.TestConfig;
import ru.mgilivanov.project.service.ClientService;
import ru.mgilivanov.project.service.CloseDayService;
import ru.mgilivanov.project.service.EodService;

@SpringBootTest(classes = {TestConfig.class, CloseDayController.class})
public class CloseDayControllerTest extends ControllerTest {

    @MockBean
    CloseDayService closeDayService;

    @MockBean
    EodService eodService;

    public CloseDayControllerTest(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @Test
    @WithMockUser(roles = {"EOD"})
    public void callCloseDayCorrectData() throws Exception {
        checkCorrectJson(CloseDayController.CLOSE_DAY_RUN,
                "{\"date\": \"2020-05-06\"}");
    }

    @Test
    @WithMockUser(roles = {"ABS"})
    public void callCloseDayForbidden() throws Exception {
        checkForbidden(CloseDayController.CLOSE_DAY_RUN,
                "{\"date\": \"2020-05-06\"}");
    }
}
