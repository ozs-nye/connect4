package connect4.Csomagok;

//import connect4.Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * Fajlkezelo.java
 * <p>
 * A játékállás fájlba való kiírása és betöltése a feladata
 */
public class Fajlkezelo {

  private static final Logger logger = LoggerFactory.getLogger(Fajlkezelo.class);

  /**
   * Játékállás kiírása fájlba.
   *
   * @param fajlNev A játékállás mentéséra használt fájl.
   * @throws IOException Kivételkezelés.
   */
  public static void KiirFajlba(String fajlNev) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fajlNev))) {
      for (String[] sor : Tabla.tablaMatrix) {
        writer.write(String.join("|", sor));
        writer.newLine();
      }
    }
  }

  /**
   * A beolvasott játékmentés alapján megpróbáljuk a mátrix dimenzióit meghatározni.
   *
   * @param fajlNev A játékállás mentésére használt fájl.
   * @throws IOException Kivételkezelés.
   */
  public static void setTablaMatrixMeretFajlbol(String fajlNev) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(fajlNev))) {
      int tablaSorokSzama = 0;
      int tablaOszlopokSzama = -1;
      String aktualisSor;

      while ((aktualisSor = reader.readLine()) != null) {
        if (aktualisSor.isEmpty()) {
          continue; // Üres sorok kihagyása
        }

        if (tablaOszlopokSzama == -1) {
          tablaOszlopokSzama = aktualisSor.length() - aktualisSor.replace("|", "").length() + 1; // A +1 a sor mezőinek számára emeli
        }
        tablaSorokSzama++;
      }
      Tabla tabla = new Tabla(tablaSorokSzama, tablaOszlopokSzama);
    }

  }

  /**
   * Játékállás betöltése fájlból.
   *
   * @param fajlNev A játékállás mentésére használt fájl.
   * @throws IOException Kivételkezelés.
   */
  public static void BeolvasFajlbol(String fajlNev) throws IOException {

//    System.err.println("Mentett játékállás betöltése... (" +fajlNev+ ")");
    logger.info("Mentett játékállás betöltése... (" + fajlNev + ")");

    // A fájlba mentett tábla mérete alapján történő dinamikus beállítás
    setTablaMatrixMeretFajlbol(fajlNev);

    // Az összes elérhető oszlop feltöltése
    for (int oszlop = 0; oszlop < Tabla.getOszlopokSzama(); oszlop++) {
      for (int sor = 0; sor < Tabla.getSorokSzama(); sor++) {
        Jatek.addElerhetoOszlopok(String.valueOf(Tabla.BETUK.charAt(oszlop)));
      }
    }
    // Tábla feltöltése fájlból
    try (BufferedReader reader = new BufferedReader(new FileReader(fajlNev))) {
      String aktualisSor;
      int sorIndex = 0;
      while ((aktualisSor = reader.readLine()) != null) {
        if (aktualisSor.isEmpty()) {
          continue; // Üres sorok kihagyása
        }
        // Az aktuális sor elemekre bontása egy String tömb tagjaira
        String[] elemek = aktualisSor.split("\\|");
        for (int oszlopIndex = 0; oszlopIndex < elemek.length; oszlopIndex++) {
          if (!elemek[oszlopIndex].equals("   ")) {
            // Az aktuális oszlopnak megfelelő karakter megkeresése majd levétele az elérhetőek közül
            Jatek.removeElerhetoOszlopok(String.valueOf(Tabla.BETUK.charAt(oszlopIndex)));
          }
          Tabla.setTablaMatrix(sorIndex, oszlopIndex, elemek[oszlopIndex]);
        }
        sorIndex++;
      }
    }
    // A játék folytatása a kimentett állapottól
    System.out.println(ConsoleColors.GREEN_BOLD + "Mentett játékállás betöltve. Folytassuk!" + ConsoleColors.RESET);
    Tabla.TablaUjraRajzolas();
  }

  /**
   * Mentett játékállás törlése.
   * @param fajlNev A törlendő játékállás fájlja.
   */
  public static void MentesTorles(String fajlNev) {
    try {
      Files.deleteIfExists(Path.of(fajlNev));
    } catch (NoSuchFileException e) {
      logger.error("A fájl nem létezik. (" +fajlNev+ ") " +e);
    } catch (IOException e) {
      logger.error("Hiba történt. (" +e+ ")");
    }
    logger.info("Mentett játékállás törölve. (" +fajlNev+ ")");
  }

}
