package com.example.SimbirSoft_2021.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectController projectController;


    @Test
    void registration() {
    }

    @Test
    void getProjects() throws Exception {
        this.mockMvc.perform(get("/project/getprojects"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

    @Test
    void getOne() {
    }

    @Test
    void getOneByStatus() {
    }

    @Test
    void getCountByStatus() {
    }

    @Test
    void deleteOne() {
    }

    @Test
    void updateOne() {
    }
}