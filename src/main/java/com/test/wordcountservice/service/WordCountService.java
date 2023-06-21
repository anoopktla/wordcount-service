package com.test.wordcountservice.service;

import com.test.wordcountservice.enums.SortOrder;
import com.test.wordcountservice.enums.SortType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WordCountService {
    private static final String WORD_REGEX = "\\s+";

    private static Map<Integer, Set<String>> getWordSizeMapping(String sentence) {
        return Arrays.stream(sentence.split(WORD_REGEX)).collect(Collectors
                .groupingBy(String::length, Collectors.toSet()));
    }

    /**
     * Returns a list of words in the given sentence by length, size of the response
     * list equals the count passed.
     *
     * @param sentence The sentence from which we need to count the words.
     * @param count    The number of words needed in the output.
     * @return A list of String with the largest number of words.
     */
    public List<String> countWords(String sentence, int count, SortType sortType, SortOrder sortOrder) {
        log.debug(String.format("sentence is :%s count is :%d", sentence, count));
        if (!StringUtils.isEmpty(sentence) && count > 0) {

            Map<Integer, Set<String>> wordBySizeMapping = getWordSizeMapping(sentence);

            List<String> result = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Optional<Integer> longWord = wordBySizeMapping.keySet()
                        .stream().max(Integer::compareTo);
                int longestWordIndex = longWord.orElse(0);
                Set<String> longwordSet = wordBySizeMapping.get(longestWordIndex);
                if (longwordSet != null) {
                    wordBySizeMapping.remove(longestWordIndex);
                    result.addAll(longwordSet);
                }
                if (result.size() >= count) {
                    break;
                }
            }
            result = result.size() > count ? result.subList(0, count) : result;

            result = result.stream().sorted(getComparator(sortType, sortOrder)).toList();

            return result;

        } else {
            log.error(String.format("invalid inputs provided , " +
                    "sentence is :%s, count is :%d", sentence, count));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "input string is not valid");
        }
    }

    private Comparator<String> getComparator(SortType sortType, SortOrder sortOrder) {
        Comparator<String> comparator;
        if (SortType.lexical == sortType) {
            comparator = Comparator.naturalOrder();
        } else {
            comparator = Comparator.comparingInt(String::length);
        }
        if (SortOrder.desc == sortOrder) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
