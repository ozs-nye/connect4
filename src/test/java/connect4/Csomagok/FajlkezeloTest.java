package connect4.Csomagok;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FajlkezeloTest {

    private final String testFile = "test_mentes.txt";

    @Test
    void testKiirEsBeolvasFajlbol() throws IOException {
        Tabla tabla = new Tabla(6, 7);
        Tabla.setTablaMatrix(0, 0, Tabla.EMBER);
        Fajlkezelo.KiirFajlba(testFile);

        Fajlkezelo.BeolvasFajlbol(testFile);
        assertEquals(Tabla.EMBER, Tabla.getTablaMatrix(0, 0));
    }
}
