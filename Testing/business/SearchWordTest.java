package business;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import bll.SearchWord;
import dto.Documents;
import dto.Pages;

public class SearchWordTest {

    // Helper method to create document with one page
    private Documents makeDoc(String name, String content) {

        Pages page = new Pages(
                1,          // pageId
                1,          // fileId
                1,          // pageNumber
                content     // pageContent
        );

        List<Pages> pages = new ArrayList<>();
        pages.add(page);

        Documents doc = new Documents(
                1,          // id
                name,
                "hash",
                "today",
                "today",
                pages
        );

        return doc;
    }

    @Test
    void searchKeywordPositive_shouldReturnMatch() {

        List<Documents> docs = new ArrayList<>();
        docs.add(makeDoc("doc1.txt", "hello world hello"));

        List<String> result =
                SearchWord.searchKeyword("hello", docs);

        assertFalse(result.isEmpty());
    }

    @Test
    void searchKeywordNegative_shouldReturnEmpty() {

        List<Documents> docs = new ArrayList<>();
        docs.add(makeDoc("doc1.txt", "hello world"));

        List<String> result =
                SearchWord.searchKeyword("java", docs);

        assertTrue(result.isEmpty());
    }

    @Test
    void searchKeywordBoundary_shouldThrowException() {

        List<Documents> docs = new ArrayList<>();
        docs.add(makeDoc("doc1.txt", "hello world"));

        assertThrows(IllegalArgumentException.class, () -> {
            SearchWord.searchKeyword("he", docs);
        });
    }
}
