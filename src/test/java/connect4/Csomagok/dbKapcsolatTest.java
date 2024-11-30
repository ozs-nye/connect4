package connect4.Csomagok;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class dbKapcsolatTest {

    private final String testDb = "test_gyozelmek.db";
    private final String testTable = "gyoztesek";

    @BeforeEach
    void setUp() throws Exception {
        String url = "jdbc:sqlite:" + testDb;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS " + testTable + " (Ember INTEGER, Gep INTEGER)");
            stmt.execute("INSERT INTO " + testTable + " VALUES (0, 0)");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        String url = "jdbc:sqlite:" + testDb;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE IF EXISTS " + testTable);
        }
    }

    @Test
    void testGyozelemNoveles() {
        dbKapcsolat.GyozelemNoveles(testDb, testTable, Tabla.EMBER);
        // Ellenorizzük, hogy a gyozelmek száma nott
        dbKapcsolat.getGyozelmek(testDb, testTable);
    }
}
