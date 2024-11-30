package connect4.Csomagok;

import java.io.*;

public class Fajlkezelo {

  public static void KiirFajlba(String fajlNev) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fajlNev))) {
      for (String[] sor : Tabla.tablaMatrix) {
        writer.write(String.join("|", sor));
        writer.newLine();
      }
    }
//    System.err.println("A játékállás mentése megtörtént. (" +fajlNev+ ")");
  }

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
//          System.err.println("Aktuális sor: " +aktualisSor);
//          System.err.println("Aktuális sorhossz: " +aktualisSor.length() +" "+ aktualisSor.replace("|","").length());
          oszlopSzam = aktualisSor.length() - aktualisSor.replace("|", "").length() + 1; // A +1 a sor mezőinek számára emeli
//          System.err.println("Oszlopszám: " +oszlopSzam);
        }
        sorSzam++;
      }
//      System.err.println("Beolvasott fájl: " + fajlNev);
//      System.err.println("Sorok száma    : " + sorSzam);
//      System.err.println("Oszlopok száma : " + oszlopSzam);
      Tabla tabla = new Tabla(sorSzam, oszlopSzam);
    }

  }

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
//            System.err.println(String.valueOf(oszlopIndex));
//            System.err.println(String.valueOf(Tabla.BETUK.charAt(oszlopIndex)));
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
