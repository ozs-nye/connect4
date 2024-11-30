package connect4.Csomagok;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TablaKezelo {
  private static String[][] tablaMatrix;

  // Getter metódus az elem lekérdezésére
  public String getElem(int sor, int oszlop) {
    return tablaMatrix[sor][oszlop];
  }

  // Setter metódus az elem beállítására
  public static void setElem(int sor, int oszlop, String ertek) {
    if (!ertek.equals("E") && !ertek.equals("G") && !ertek.equals(" ")) {
      throw new IllegalArgumentException("Érvénytelen érték! Csak 'E', 'G', vagy ' ' lehet.");
    }
    tablaMatrix[sor][oszlop] = ertek;
  }

  // Méret dinamikus beállítása a fájl alapján
  public static void setTablaMatrixMeret(String fajlNev) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(fajlNev))) {
      int sorSzam = 0;
      int oszlopSzam = -1;

      String sor;
      while ((sor = reader.readLine()) != null) {
        sor = sor.trim(); // Szóközök eltávolítása
        if (sor.isEmpty()) {
          continue; // Üres sorok kihagyása
        }
        if (sor.startsWith("[") && sor.endsWith("]")) {
          sor = sor.substring(1, sor.length() - 1).trim(); // Szögletes zárójelek eltávolítása
        } else {
          throw new IllegalArgumentException("Érvénytelen sorformátum: " + sor);
        }

        String[] elemek = sor.split("\\s*,\\s*"); // Szóközök kezelése a vesszők körül

        System.out.println("Feldolgozott sor: " + String.join(", ", elemek));

        if (oszlopSzam == -1) {
          oszlopSzam = elemek.length; // Az első sor alapján állapítjuk meg az oszlopszámot
          System.out.println("Első sor tartalma: " + String.join(", ", elemek));
          System.out.println("Oszlopok száma beállítása: " + elemek.length);
        } else if (elemek.length != oszlopSzam) {
          throw new IllegalArgumentException("A fájl soraiban eltérő az elemek száma! Sor: " + sor);
        }
        sorSzam++;
      }

      // Inicializáljuk a tablaMatrix tömböt
      tablaMatrix = new String[sorSzam][oszlopSzam];
      System.out.println("tablaMatrix mérete dinamikusan beállítva: " + sorSzam + " x " + oszlopSzam);
    }
  }




  // Fájl beolvasása és a tömb feltöltése
  public static void beolvasFajlbol(String fajlNev) throws IOException {
    // Méret dinamikus beállítása
    setTablaMatrixMeret(fajlNev);

    // Fájl beolvasása és feltöltése
    try (BufferedReader reader = new BufferedReader(new FileReader(fajlNev))) {
      String sor;
      int sorIndex = 0;

      while ((sor = reader.readLine()) != null) {
        sor = sor.trim();
        if (sor.startsWith("[") && sor.endsWith("]")) {
          sor = sor.substring(1, sor.length() - 1).trim();
        }

        String[] elemek = sor.split("\\s*,\\s*");
        for (int oszlopIndex = 0; oszlopIndex < elemek.length; oszlopIndex++) {
          setElem(sorIndex, oszlopIndex, elemek[oszlopIndex].trim());
        }
        sorIndex++;
      }
    }

    System.out.println("tablaMatrix sikeresen feltöltve!");
  }

  // A tömb kiíratása fájlba
  public void kiirFajlba(String fajlNev) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fajlNev))) {
      for (String[] sor : tablaMatrix) {
        writer.write("[");
        writer.write(String.join(", ", sor));
        writer.write("]");
        writer.newLine();
      }
    }
    System.out.println("tablaMatrix sikeresen kiírva a fájlba: " + fajlNev);
  }

  // A tömb kiíratása a konzolra
  public void kiirKonzolra() {
    for (String[] sor : tablaMatrix) {
      for (String elem : sor) {
        System.out.print((elem.equals(" ") ? " " : elem) + " ");
      }
      System.out.println();
    }
  }

  // Példa a használatra
  public static void main(String[] args) {
    TablaKezelo tabla = new TablaKezelo();

    try {
      // Beolvasás
      tabla.beolvasFajlbol("mentes.txt");

      // Kiírás konzolra
      System.out.println("Beolvasott tablaMatrix:");
      tabla.kiirKonzolra();

      // Kiírás fájlba
      tabla.kiirFajlba("tabla_output.txt");
    } catch (IOException e) {
      System.err.println("Hiba történt a fájlművelet során: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Hiba történt: " + e.getMessage());
    }
  }
}
