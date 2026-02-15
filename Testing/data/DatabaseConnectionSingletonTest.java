package data;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import dal.DatabaseConnection;

public class DatabaseConnectionSingletonTest {

    @Test
    void shouldReturnSameInstanceEveryTime() {
        DatabaseConnection a = DatabaseConnection.getInstance();
        DatabaseConnection b = DatabaseConnection.getInstance();

        assertSame(a, b, "DatabaseConnection must be a Singleton (same instance expected).");
    }
}
