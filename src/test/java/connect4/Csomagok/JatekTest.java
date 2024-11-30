package connect4.Csomagok;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JatekTest {

    private Jatek jatek;

    @BeforeEach
    void setUp() {
        jatek = new Jatek();
    }

    @Test
    void testKorongLerakas() {
        Tabla tabla = new Tabla(6, 7);
        Jatek.addElerhetoOszlopok("a");
        Jatek.removeElerhetoOszlopok("a");
        assertEquals(Tabla.EMBER, Tabla.getTablaMatrix(0, 0));
    }

    @Test
    void testNyertesEllenorzesHorizontal() {
        Tabla.setTablaMatrix(0, 0, Tabla.EMBER);
        Tabla.setTablaMatrix(0, 1, Tabla.EMBER);
        Tabla.setTablaMatrix(0, 2, Tabla.EMBER);
        Tabla.setTablaMatrix(0, 3, Tabla.EMBER);
        assertTrue(Jatek.NyertesEllenorzes(Tabla.EMBER));
    }

    @Test
    void testNyertesEllenorzesNoWin() {
        assertFalse(Jatek.NyertesEllenorzes(Tabla.EMBER));
    }
}
