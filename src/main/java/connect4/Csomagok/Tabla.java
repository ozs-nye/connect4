package connect4.Csomagok;

public class Tabla {

  private static int sorokSzama;
  private static int oszlopokSzama;
  public static final String EMBER  = " E ";   // E - ember
  public static final String GEP    = " G ";     // G - gép
  public static final String URES   = "   ";    //   - üres (nincs korong)
  private static final String KERET = "+---"; // A tábla kirajzolásához kell.
  public static final String BETUK  = "abcdefghijklmnopqrstuvwxyz";   // Most már ez az aktuálisan használt.
  public static String[][] tablaMatrix;

  public Tabla() {
    TablaInicializalas();
  }

  public Tabla(int sorokSzama, int oszlopokSzama) {
    setSorokSzama(sorokSzama);
    setOszlopokSzama(oszlopokSzama);
    TablaInicializalas();
  }

  public void TablaInicializalas() {
    tablaMatrix = new String[getSorokSzama()][getOszlopokSzama()];
    UresTabla();
    System.out.println("\n\t" + ConsoleColors.WHITE_BOLD + " -=[ Connect4 ]=- " + ConsoleColors.RESET);
    TablaRajzolas();
  }

  public static int getSorokSzama() {
    return sorokSzama;
  }

  public static void setSorokSzama(int sorokSzama) {
    if (sorokSzama >= 12) {
      Tabla.sorokSzama = 12;
      System.err.println("* Sorok száma probléma!\n - Nagyobb szám lett megadva mint a megengedett maximális, ezért a legmagasabbra állítva!\n   (Sorok száma <= 12. A megadott érték " + sorokSzama + " volt.)");
    } else if (sorokSzama < 4) {
      Tabla.sorokSzama = 4;
      System.err.println("* Sorok száma probléma!\n - Kisebb szám lett megadva mint a megengedett minimális, ezért a legalacsonyabbra állítva!\n   (Sorok száma >= 4. A megadott érték " + sorokSzama + " volt.)");
    } else {
      Tabla.sorokSzama = sorokSzama;
    }
  }

  public static int getOszlopokSzama() {
    return oszlopokSzama;
  }

  public static void setOszlopokSzama(int oszlopokSzama) {
    if (oszlopokSzama >= 12) {
      Tabla.oszlopokSzama = 12;
      System.err.println("* Oszlopok száma probléma!\n - Nagyobb szám lett megadva mint a megengedett maximális, ezért a legmagasabbra állítva!\n   (Oszlopok száma <= 12. A megadott érték " + oszlopokSzama + " volt.)");
    } else if (oszlopokSzama < 4) {
      Tabla.oszlopokSzama = 4;
      System.err.println("* Oszlopok száma probléma!\n - Kisebb szám lett megadva mint a megengedett minimális, ezért a legalacsonyabbra állítva!\n   (Oszlopok száma >= 4. A megadott érték " + oszlopokSzama + " volt.)");
    } else {
      Tabla.oszlopokSzama = oszlopokSzama;
    }
  }

  public static void setTablaMatrix(int X, int Y, String GepEmberUres) {
    Tabla.tablaMatrix[X][Y] = GepEmberUres;
  }

  public static String getTablaMatrix(int X, int Y) {
    return Tabla.tablaMatrix[X][Y];
  }

  public String toString() {
    return "Sorok száma: " + Tabla.getSorokSzama() + "\nOszlopok száma: " + Tabla.getOszlopokSzama();
  }

  public void UresTabla() {
    for (int sor = 0; sor < getSorokSzama(); sor++) {
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
        tablaMatrix[sor][oszlop] = Tabla.URES;
      }
    }
  }

  private static void TablaKeret(boolean isFelso) {
    if (isFelso) {
      System.out.print("\t");
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
        if (oszlop == getOszlopokSzama() - 1) {
          System.out.print(Tabla.KERET + "+");
        } else {
          System.out.print(Tabla.KERET);
        }
      }
    } else {
      System.out.print("\t");
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
        if (oszlop == getOszlopokSzama() - 1) {
          System.out.print(Tabla.KERET + "+");
        } else {
          System.out.print(Tabla.KERET);
        }
      }
      System.out.println();
      System.out.print("\t");
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
        System.out.print("  " + Tabla.BETUK.charAt(oszlop) + " "); // Most már a String egy karakterét keresi és jeleníti meg. Ezt a billentyű ellenőrzés miatt kellett bevezetni.
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void TablaUjraRajzolas() {
    System.out.println(ConsoleColors.BLACK + "\n-----------------------------------------" + ConsoleColors.RESET);
    Tabla.TablaRajzolas();
    Jatek.BillentyuBeolvasas();
  }

  public static void TablaRajzolas() {
    System.out.println();
    TablaKeret(true);
    for (int sor = getSorokSzama() - 1; sor >= 0; sor--) {
      System.out.print(sor + 1 + "\t");
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
        System.out.print("|");

        // Színezett karakterekkel való kirajzolás, kiemelés ettől...
        if (tablaMatrix[sor][oszlop].equals(" E ")) {
          System.out.print(ConsoleColors.YELLOW + tablaMatrix[sor][oszlop] + ConsoleColors.RESET);
        } else if (tablaMatrix[sor][oszlop].equals(" G ")) {
          System.out.print(ConsoleColors.RED + tablaMatrix[sor][oszlop] + ConsoleColors.RESET);
        } else System.out.print(tablaMatrix[sor][oszlop]);
        //...eddig

        if (oszlop == getOszlopokSzama() - 1) System.out.print("|");
      }
      System.out.println();
    }
    TablaKeret(false);
  }

}
