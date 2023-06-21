package com.test.wordcountservice.service;

import com.test.wordcountservice.enums.SortOrder;
import com.test.wordcountservice.enums.SortType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = WordCountService.class)
class WordCountServiceTest {
    @Autowired
    private WordCountService wordCountService;

    @Test
    void testWordCountExceptionSentenceIsNullCase(){
        assertThrows(ResponseStatusException.class,()->
                wordCountService.countWords(null,5, SortType.length,SortOrder.asc));
    }

    @Test
    void testWordCountExceptionCountIsZeroCase(){
        assertThrows(ResponseStatusException.class,()->
                wordCountService.countWords("this is a good test case",0,SortType.length,SortOrder.asc));
    }
    @Test
    void testGetCountSuccess() {
        String input =
                "“When I consider how my light is spent,\n"
                        + "   Ere half my days, in this dark world and wide,\n"
                        + "   And that one Talent which is death to hide\n"
                        + "   Lodged with me useless, though my Soul more bent\n"
                        + "To serve therewith my Maker, and present\n"
                        + "   My true account, lest he returning chide;\n"
                        + "   “Doth God exact day-labour, light denied?”\n"
                        + "   I fondly ask. But patience, to prevent\n"
                        + "That murmur, soon replies, “God doth not need\n"
                        + "   Either man’s work or his own gifts; who best\n"
                        + "   Bear his mild yoke, they serve him best. His state\n"
                        + "Is Kingly. Thousands at his bidding speed\n"
                        + "   And post o’er Land and Ocean without rest:\n"
                        + "   They also serve who only stand and wait.”\n"
                        + "”\n";

        List<String> words = wordCountService.countWords(input, 5
        ,SortType.length,SortOrder.desc);
        assertNotNull(words);
        assertEquals(5, words.size());
        assertEquals("day-labour,", words.get(0));
        assertEquals("returning", words.get(1));
        assertEquals("therewith", words.get(2));
        assertEquals("patience,", words.get(3));
        assertEquals("Thousands", words.get(4));

    }

    @Test
    void testGetCountSuccessReverseSorted() {
        String input =
                "“When I consider how my light is spent,\n"
                        + "   Ere half my days, in this dark world and wide,\n"
                        + "   And that one Talent which is death to hide\n"
                        + "   Lodged with me useless, though my Soul more bent\n"
                        + "To serve therewith my Maker, and present\n"
                        + "   My true account, lest he returning chide;\n"
                        + "   “Doth God exact day-labour, light denied?”\n"
                        + "   I fondly ask. But patience, to prevent\n"
                        + "That murmur, soon replies, “God doth not need\n"
                        + "   Either man’s work or his own gifts; who best\n"
                        + "   Bear his mild yoke, they serve him best. His state\n"
                        + "Is Kingly. Thousands at his bidding speed\n"
                        + "   And post o’er Land and Ocean without rest:\n"
                        + "   They also serve who only stand and wait.”\n"
                        + "”\n";

        List<String> words = wordCountService.countWords(input, 5
                ,SortType.length,SortOrder.asc);
        assertNotNull(words);
        assertEquals(5, words.size());
        assertEquals("returning", words.get(0));
        assertEquals("therewith", words.get(1));
        assertEquals("patience,", words.get(2));
        assertEquals("Thousands", words.get(3));
        assertEquals("day-labour,", words.get(4));


    }

    @Test
    void testWordCountCaseSentenceIsBiggerThanCountCase(){
       List<String> longestWords = wordCountService.countWords("this is going to be a wonderful test",5,SortType.length, SortOrder.asc);
       assertNotNull(longestWords);
       assertEquals(5,longestWords.size());
    }
    @Test
    void testWordCountCaseSentenceIsSmallerThanCountCase(){
        List<String> longestWords = wordCountService.countWords("this is ",5,SortType.length,SortOrder.asc);
        assertNotNull(longestWords);
        assertEquals(2,longestWords.size());
    }

    //Question: what should be the response if words are repeating and cont is > no of words :Assuming we send unique words in response
    @Test
    void testWordCountCaseSentenceIsSmallerThanCountAndWordsRepeatingCase(){
        List<String> longestWords = wordCountService.countWords("this this",5,SortType.length,SortOrder.asc);
        assertNotNull(longestWords);
        assertEquals(1,longestWords.size());
    }
}
