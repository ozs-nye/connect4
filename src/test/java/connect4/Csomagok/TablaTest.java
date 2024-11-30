package connect4.Csomagok;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TablaTest {

    private Tabla tabla;

    @BeforeEach
    void setUp() {
        tabla = new Tabla(6, 7); // Standard 6x7 Connect4 tábla
    }

    @Test
    void testTablaInicializalas() {
        tabla.TablaInicializalas();
        assertEquals(6, tabla.getSorokSzama());
        assertEquals(7, tabla.getOszlopokSzama());
        for (int sor = 0; sor < 6; sor++) {
            for (int oszlop = 0; oszlop < 7; oszlop++) {
                assertEquals(Tabla.URES, Tabla.getTablaMatrix(sor, oszlop));
            }
        }
    }

    @Test
    void testSetTablaMatrix() {
        Tabla.setTablaMatrix(0, 0, Tabla.EMBER);
        assertEquals(Tabla.EMBER, Tabla.getTablaMatrix(0, 0));
    }

    @Test
    void testSetSorokSzamaInvalidValues() {
        Tabla.setSorokSzama(15); // Max limit: 12
        assertEquals(12, Tabla.getSorokSzama());
    }
}
