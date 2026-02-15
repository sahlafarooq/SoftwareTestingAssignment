package business;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import bll.FacadeBO;
import bll.IEditorBO;
import dto.Documents;

public class FacadeBOTest {

    // ✅ Stub (fake) implementation to make tests "swappable" against interface
    static class EditorBOStub implements IEditorBO {

        boolean createFileCalled = false;
        String lastFileName = null;
        String lastContent = null;

        @Override
        public boolean createFile(String nameOfFile, String content) {
            createFileCalled = true;
            lastFileName = nameOfFile;
            lastContent = content;

            // simple behavior for testing
            if (nameOfFile == null || nameOfFile.trim().isEmpty()) return false;
            if (content == null) return false;
            return true;
        }

        // ---- Methods not needed for these tests (return safe defaults) ----
        @Override public boolean updateFile(int id, String fileName, int pageNumber, String content) { return false; }
        @Override public boolean deleteFile(int id) { return false; }
        @Override public boolean importTextFiles(File file, String fileName) { return false; }
        @Override public Documents getFile(int id) { return null; }
        @Override public List<Documents> getAllFiles() { return Collections.emptyList(); }
        @Override public String getFileExtension(String fileName) { return ""; }
        @Override public String transliterate(int pageId, String arabicText) { return ""; }
        @Override public List<String> searchKeyword(String keyword) { return Collections.emptyList(); }
        @Override public Map<String, String> lemmatizeWords(String text) { return Collections.emptyMap(); }
        @Override public Map<String, List<String>> extractPOS(String text) { return Collections.emptyMap(); }
        @Override public Map<String, String> extractRoots(String text) { return Collections.emptyMap(); }
        @Override public double performTFIDF(List<String> unSelectedDocsContent, String selectedDocContent) { return 0; }
        @Override public Map<String, Double> performPMI(String content) { return Collections.emptyMap(); }
        @Override public Map<String, Double> performPKL(String content) { return Collections.emptyMap(); }
        @Override public Map<String, String> stemWords(String text) { return Collections.emptyMap(); }
        @Override public Map<String, String> segmentWords(String text) { return Collections.emptyMap(); }
    }

    @Test
    void createFilePositive_shouldDelegateToBO_andReturnTrue() {
        EditorBOStub stub = new EditorBOStub();
        FacadeBO facade = new FacadeBO(stub);

        boolean ok = facade.createFile("a.txt", "hello");

        assertTrue(ok);
        assertTrue(stub.createFileCalled, "Facade should delegate to IEditorBO");
        assertEquals("a.txt", stub.lastFileName);
        assertEquals("hello", stub.lastContent);
    }

    @Test
    void createFileNegative_blankName_shouldReturnFalse() {
        EditorBOStub stub = new EditorBOStub();
        FacadeBO facade = new FacadeBO(stub);

        boolean ok = facade.createFile("   ", "hello");

        assertFalse(ok);
        assertTrue(stub.createFileCalled);
    }

    @Test
    void createFileNegative_nullContent_shouldReturnFalse() {
        EditorBOStub stub = new EditorBOStub();
        FacadeBO facade = new FacadeBO(stub);

        boolean ok = facade.createFile("a.txt", null);

        assertFalse(ok);
        assertTrue(stub.createFileCalled);
    }
}
