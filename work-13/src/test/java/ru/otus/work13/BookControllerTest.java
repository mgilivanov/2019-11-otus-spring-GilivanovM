package ru.otus.work13;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.work13.rest.BookController;
import ru.otus.work13.service.BookService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testNotAuthenticated() throws Exception {
        mockMvc.perform(get("/edit"))
                .andExpect(status().isFound());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void testAdminAuthenticatedOK() throws Exception {
        mockMvc.perform(get("/edit"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    public void testUserAuthenticatedForbidden() throws Exception {
        mockMvc.perform(get("/edit"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    void indexPageModel() throws Exception {
        ModelAndView modelAndView = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn()
                .getModelAndView();
        assertEquals("list_books", modelAndView.getViewName());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void editPageModel() throws Exception {
        ModelAndView modelAndView = mockMvc.perform(get("/edit"))
                .andExpect(status().isOk())
                .andReturn()
                .getModelAndView();
        assertEquals("edit", modelAndView.getViewName());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    void commentPageModelWithoutId() throws Exception {
        mockMvc.perform(get("/comment"))
                .andExpect(status().isBadRequest());
    }
}