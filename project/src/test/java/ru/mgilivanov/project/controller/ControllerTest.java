package ru.mgilivanov.project.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.mgilivanov.project.model.Result;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class ControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public ControllerTest(WebApplicationContext webApplicationContext){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    protected MockHttpServletRequestBuilder postJson(String url, String content) {
        return MockMvcRequestBuilders.post(url).content(content).contentType(MediaType.APPLICATION_JSON);
    }

    protected ResultActions checkInvalidJson(String url, String content) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url).content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(Result.VALIDATION_ERROR_CODE));
    }

    protected ResultActions checkCorrectJson(String url, String content) throws Exception {
        return mockMvc.perform(postJson(url, content))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    protected void checkForbidden(String url, String content) throws Exception {
        mockMvc.perform(postJson(url, content)).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
