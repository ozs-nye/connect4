package connect4.Csomagok;

import connect4.Main;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Jatek.class
 * A játék lebonyolításáért felelős osztály.
 */
public class Jatek {

  public static String elerhetoOszlopok = "";
  private static boolean ember = true;
  public static boolean vanNyertes = false;

  /**
   * A Jatek() inicializálása
   */
  public Jatek() {
    JatekInicializalas();
  }

  /**
   * JatekInicializalas()
   *
   * A tábla feltöltése üres mezőkkel, majd várakozás az első beolvasásra
   */
  public static void JatekInicializalas() {
    for (int oszlop = 0; oszlop < Tabla.getOszlopokSzama(); oszlop++) {
      for (int sor = 0; sor < Tabla.getSorokSzama(); sor++) {
        Jatek.addElerhetoOszlopok(String.valueOf(Tabla.BETUK.charAt(oszlop)));
      }
    }
    BillentyuBeolvasas();
  }

  /**
   * VIZSGÁLAT - Elérhető oszlop keresése
   * @param oszlopIndex Keresett oszlop
   * @return A keresett oszlop indexe
   */
  private static char getElerhetoOszlopok(int oszlopIndex) {
    return Jatek.elerhetoOszlopok.charAt(oszlopIndex);
  }

  /**
   * Elérhető oszlopok feltöltése.
   * @param StringKarakter Feltölteni kívánt oszlop karaktere (a, b, c, ...)
   */
  public static void addElerhetoOszlopok(String StringKarakter) {
    elerhetoOszlopok += StringKarakter;
  }

  /**
   * Oszlop eltávolítása az elérhetőek közül
   * @param StringKarakter Eltávolítani kívánt oszlop azonosítója (a, b, c, ...)
   */
  public static void removeElerhetoOszlopok(String StringKarakter) {
    // Sajnos a "központi" változóval nem működik, ezért mindenképpen kell egy új String bevezetése, amit később vissza tudok írni.
    String tmpElerhetoOszlopok = Jatek.elerhetoOszlopok;
    Jatek.elerhetoOszlopok = tmpElerhetoOszlopok.replaceFirst(StringKarakter, "");
    Jatek.KorongLerakas(StringKarakter);
  }

  /**
   * VIZSGÁLAT - Az adott oszlopban van-e még rendelkezésre álló hely
   * @param oszlopSzam Keresett oszlop
   * @return Ha van elérhető hely az adott oszlopban, akkor az adott sor; ha nincs, akkor '-1'.
   */
  private static int OszlopSzabadSor(int oszlopSzam) {
    for (int sor = 0; sor < Tabla.getSorokSzama(); sor++) {
      if (Tabla.tablaMatrix[sor][oszlopSzam].equals("   ")) {
        return sor;
      }
    }
    return -1;
  }

  /**
   * Egy korong lerakása
   * @param StringKarakter A lerakni kívánt korong tulajdonosa (ember, gép)
   */
  private static void KorongLerakas(String StringKarakter) {
    int sor = OszlopSzabadSor(Tabla.BETUK.indexOf(StringKarakter));
    int oszlop = Tabla.BETUK.indexOf(StringKarakter);
    if (sor >= 0) {
      if (Jatek.ember) {
        Tabla.setTablaMatrix(sor, oszlop, Tabla.EMBER);
        if (Jatek.NyertesEllenorzes(Tabla.EMBER)) {
          System.out.println();
          System.out.println(ConsoleColors.YELLOW + "A játéknak vége. Nyertél, ember! Gratulálok! :)" + ConsoleColors.RESET);
          Jatek.vanNyertes = true;
        }
        Jatek.ember = false;
        try {
          Fajlkezelo.KiirFajlba(Main.mentesFajlNev);
        } catch (IOException e) {
          throw new RuntimeException("Hiba történt: " +e);
        }
      } else {
        Tabla.setTablaMatrix(sor, oszlop, Tabla.GEP);
        if (Jatek.NyertesEllenorzes(Tabla.GEP)) {
          System.out.println();
          System.out.println(ConsoleColors.RED + "A játéknak vége. Vesztettél, ember! A gép nyert :(" + ConsoleColors.RESET);
          Jatek.vanNyertes = true;
        }
        Jatek.ember = true;
        // A tábla aktuális tartalmának fájlba írása...
        try {
          Fajlkezelo.KiirFajlba(Main.mentesFajlNev);
        } catch (IOException e) {
          throw new RuntimeException("Hiba történt: " +e);
        }
      }
    } else {
      System.err.println("Valami nem klappol! :( (Sor: " + sor + ", oszlop: " + oszlop + ")");
    }
  }

  /**
   * Az oszlop karakter kiválasztása a billentyűzetről
   */
  public static void BillentyuBeolvasas() {
    char oszlopKarakter;
    // Nyertes vizsgálata. Mindaddig végrehajtható, amíg nincs nyertes.
    while (!vanNyertes) {
      Scanner input = new Scanner(System.in);
      System.out.println("(Elérhető: " + ConsoleColors.CYAN + Jatek.elerhetoOszlopok + ConsoleColors.RESET + ")");
      System.out.println("Add meg az oszlop nevét, ahova a korongot rakjuk!");
      System.out.print(" - Választás: ");

      // Ha ember a következő, akkor beolvasás a billentyűzetről. Ha nem, véletlen választás az elérhető oszlopok közül.
      if (Jatek.ember) {
        oszlopKarakter = input.next().charAt(0);
      } else {
        Random veletlenSzam = new Random();
        oszlopKarakter = Jatek.elerhetoOszlopok.charAt(veletlenSzam.nextInt(Jatek.elerhetoOszlopok.length()));
      }

      if (!Jatek.elerhetoOszlopok.contains(String.valueOf(oszlopKarakter))) {
        System.err.println("Olyan oszlop azonosító lett megadva, ami nem elérhető. (\"" + oszlopKarakter + "\")");
        Tabla.TablaUjraRajzolas();
      } else {
        Jatek.removeElerhetoOszlopok(String.valueOf(oszlopKarakter));
        Tabla.TablaUjraRajzolas();
      }

    }
  }

  /**
   * VIZSGÁLAT - Négy azonos színű korong keresése vízszintesen, függőlegesen és átlóban
   * @param keresettJatekos A lerakott korong tulajdonosa (ember, gép)
   * @return Volt-e nyertes (true, false)
   */
  public static boolean NyertesEllenorzes(String keresettJatekos) {
    // Vízszintes ellenőrzés
    for (int sor = 0; sor < Tabla.getSorokSzama(); sor++) {
      for (int oszlop = 0; oszlop < Tabla.getOszlopokSzama() - 3; oszlop++) {
        if (Tabla.getTablaMatrix(sor, oszlop).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor, oszlop + 1).equals(Tabla.EMBER)
                && Tabla.getTablaMatrix(sor, oszlop + 2).equals(Tabla.EMBER)
                && Tabla.getTablaMatrix(sor, oszlop + 3).equals(Tabla.EMBER)) {
          return true;
        }
      }
    }
    // Függőleges ellenőrzés
    for (int sor = 0; sor < Tabla.getSorokSzama() - 3; sor++) {
      for (int oszlop = 0; oszlop < Tabla.getOszlopokSzama(); oszlop++) {
        if (Tabla.getTablaMatrix(sor, oszlop).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor + 1, oszlop).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor + 2, oszlop).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor + 3, oszlop).equals(keresettJatekos)) {
          return true;
        }
      }
    }
    // Átlós ellenőrzés 1 | bal fent -> jobb lent
    for (int sor = 0; sor < Tabla.getSorokSzama() - 3; sor++) {
      for (int oszlop = 0; oszlop < Tabla.getOszlopokSzama() - 3; oszlop++) {
        if (Tabla.getTablaMatrix(sor, oszlop).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor + 1, oszlop + 1).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor + 2, oszlop + 2).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor + 3, oszlop + 3).equals(keresettJatekos)) {
          return true;
        }
      }
    }
    // Átlós ellenőrzés 1 | jobb fent -> bal lent
    for (int sor = 3; sor < Tabla.getSorokSzama(); sor++) {
      for (int oszlop = 0; oszlop < Tabla.getOszlopokSzama() - 3; oszlop++) {
        if (Tabla.getTablaMatrix(sor, oszlop).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor - 1, oszlop + 1).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor - 2, oszlop + 2).equals(keresettJatekos)
                && Tabla.getTablaMatrix(sor - 3, oszlop + 3).equals(keresettJatekos)) {
          return true;
        }
      }
    }
    return false;
  }

}
