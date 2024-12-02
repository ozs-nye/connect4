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
    void testNyertesEllenorzesFuggoleges() {
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

    @Test
    void testDontetlenTelitettTabla() {
        // Töltsük fel a táblát a döntetlen állás szerint
        Tabla.setTablaMatrix(0, 0, Tabla.EMBER);
        Tabla.setTablaMatrix(0, 1, Tabla.EMBER);
        Tabla.setTablaMatrix(0, 2, Tabla.GEP);
        Tabla.setTablaMatrix(0, 3, Tabla.GEP);

        Tabla.setTablaMatrix(1, 0, Tabla.GEP);
        Tabla.setTablaMatrix(1, 1, Tabla.GEP);
        Tabla.setTablaMatrix(1, 2, Tabla.EMBER);
        Tabla.setTablaMatrix(1, 3, Tabla.EMBER);

        Tabla.setTablaMatrix(2, 0, Tabla.EMBER);
        Tabla.setTablaMatrix(2, 1, Tabla.EMBER);
        Tabla.setTablaMatrix(2, 2, Tabla.GEP);
        Tabla.setTablaMatrix(2, 3, Tabla.GEP);

        Tabla.setTablaMatrix(3, 0, Tabla.EMBER);
        Tabla.setTablaMatrix(3, 1, Tabla.GEP);
        Tabla.setTablaMatrix(3, 2, Tabla.GEP);
        Tabla.setTablaMatrix(3, 3, Tabla.EMBER);

        // Ellenőrizzük, hogy a rendszer döntetlent hirdet-e
        assertTrue(Jatek.dontetlenEllenorzes(), "A tábla teljesen tele van, döntetlent kellett volna hirdetni.");
    }
}
