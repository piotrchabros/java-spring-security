package com.itcpc.user.jwt.security;

import com.itcpc.user.jwt.security.api.controller.UsersController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private UsersController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testContexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUsersAdminAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Piotr")));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUsersUserAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }
}