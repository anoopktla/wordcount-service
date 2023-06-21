package com.test.wordcountservice.controller;

import com.test.wordcountservice.enums.SortOrder;
import com.test.wordcountservice.enums.SortType;
import com.test.wordcountservice.service.WordCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/words")
public class WordCountController {
    private final WordCountService wordCountService;

    @GetMapping("/longestwords")
    public ResponseEntity<List<String>>
    getWordCount(@RequestParam("sentence") String word,

                 @RequestParam(value = "count",
                         required = false,
                         defaultValue = "5") int count, @RequestParam(value = "sort", required = false,
            defaultValue = "length") SortType sortType, @RequestParam(value = "sortOrder",
            required = false, defaultValue = "asc") SortOrder sortOrder) {
        return ResponseEntity.ok(wordCountService.countWords(word, count, sortType, sortOrder));

    }
}
