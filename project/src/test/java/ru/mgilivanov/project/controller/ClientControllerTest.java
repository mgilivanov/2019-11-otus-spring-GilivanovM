package ru.mgilivanov.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;
import ru.mgilivanov.project.TestConfig;
import ru.mgilivanov.project.service.ClientService;

@SpringBootTest(classes = {TestConfig.class, ClientController.class})
public class ClientControllerTest extends ControllerTest {

    @MockBean
    ClientService clientService;

    public ClientControllerTest(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @Test
    @WithMockUser(roles = {"CRM"})
    public void callClientAddCorrectData() throws Exception {
        checkCorrectJson(ClientController.CLIENT_ADD,
                "{\"last_name\": \"Ivanov\"," +
                        "\"first_name\": \"Ivan\"," +
                        "\"middle_name\": \"Ivanovich\","+
                        "\"passport_id\": \"1312 455444\","+
                        "\"email\": \"dfsg@dfg.ru\"}");
    }

    @Test
    @WithMockUser(roles = {"CRM"})
    public void callClientAddIncorrectData() throws Exception {
        checkInvalidJson(ClientController.CLIENT_ADD,
                "{\"last_nameee\": \"Ivanov\"," +
                        "\"first_name\": \"Ivan\"," +
                        "\"middle_name\": \"Ivanovich\","+
                        "\"passport_id\": \"1312 455444\","+
                        "\"email\": \"dfsg@dfg.ru\"}");
    }

    @Test
    @WithMockUser(roles = {"ABS"})
    public void callClientAddForbidden() throws Exception {
        checkForbidden(ClientController.CLIENT_ADD,
                "{\"last_name\": \"Ivanov\"," +
                        "\"first_name\": \"Ivan\"," +
                        "\"middle_name\": \"Ivanovich\","+
                        "\"passport_id\": \"1312 455444\","+
                        "\"email\": \"dfsg@dfg.ru\"}");
    }
}
