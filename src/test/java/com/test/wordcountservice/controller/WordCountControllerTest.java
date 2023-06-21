package com.test.wordcountservice.controller;

import com.test.wordcountservice.service.WordCountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = WordCountController.class)
public class WordCountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WordCountService wordCountService;

    @Test
    void testGetWordCount() throws Exception {
        when(wordCountService.countWords(any(), anyInt(),any(),any())).thenReturn(List.of("this", "test"));
        mockMvc.perform(get("/v1/words/longestwords?sentence=this%20is%20a%20test&count=1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]", is("this")))
                .andExpect(jsonPath("$.[1]", is("test")));
    }

    @Test
    void testGetWordCountBadRequest() throws Exception {
        mockMvc.perform(get("/v1/words/longestwords?count=1"))
                .andDo(print()).andExpect(status().isBadRequest());
    }
}
