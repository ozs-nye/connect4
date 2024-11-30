package connect4.Csomagok;

import java.io.*;

/**
 * Fajlkezelo.java
 *
 * A játékállás fájlba való kiírása és betöltése a feladata
 */
public class Fajlkezelo {

  /**
   * Játékállás kiírása fájlba.
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
   * @param fajlNev A játékállás mentésére használt fájl.
   * @throws IOException Kivételkezelés.
   */
  public static void setTablaMatrixMeretFajlbol(String fajlNev) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(fajlNev))) {
      int sorSzam = 0;
      int oszlopSzam = -1;
      String aktualisSor;

      while ((aktualisSor = reader.readLine()) != null) {
        if (aktualisSor.isEmpty()) {
          continue; // Üres sorok kihagyása
        }

        if (oszlopSzam == -1) {
          oszlopSzam = aktualisSor.length() - aktualisSor.replace("|", "").length() + 1; // A +1 a sor mezőinek számára emeli
        }
        sorSzam++;
      }
      Tabla tabla = new Tabla(sorSzam, oszlopSzam);
    }

  }

  /**
   * Játékállás betöltése fájlból.
   * @param fajlNev A játékállás mentésére használt fájl.
   * @throws IOException Kivételkezelés.
   */
  public static void BeolvasFajlbol(String fajlNev) throws IOException {

    System.err.println("Mentett játékállás betöltése... (" +fajlNev+ ")");

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
    System.out.println(ConsoleColors.GREEN_BOLD +"Mentett játékállás betöltve. Folytassuk!"+ ConsoleColors.RESET);
    Tabla.TablaUjraRajzolas();
  }

}
