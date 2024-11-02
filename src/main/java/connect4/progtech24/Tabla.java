package connect4.progtech24;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Tabla {

  private static int sorokSzama;
  private static int oszlopokSzama;
  private static final String SARGA = "S";
  private static final String PIROS = "P";
  private static final String URES = "   ";
  private static String KERET = "+---";
  private static final char[] BETUK = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  private static String[][] tablaMatrix;

  public Tabla(int sorokSzama, int oszlopokSzama) {
    Tabla.sorokSzama = sorokSzama;
    Tabla.oszlopokSzama = oszlopokSzama;
    TablaInicializalas();
  }

  public static int getSorokSzama() {
    return sorokSzama;
  }

  public static void setSorokSzama(int sorokSzama) {
    Tabla.sorokSzama = sorokSzama;
  }

  public static int getOszlopokSzama() {
    return oszlopokSzama;
  }

  public static void setOszlopokSzama(int oszlopokSzama) {
    Tabla.oszlopokSzama = oszlopokSzama;
  }

  public String toString() {
    return "Sorok száma: " + Tabla.getSorokSzama() + "\nOszlopok száma: " + Tabla.getOszlopokSzama();
  }

  public void TablaInicializalas() {
    tablaMatrix = new String[getSorokSzama()][getOszlopokSzama()];
    UresTabla();
  }

  public void UresTabla() {
    for (int sor = 0; sor < getSorokSzama(); sor++) {
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
        tablaMatrix[sor][oszlop] = Tabla.URES;
      }
    }
  }

  private void TablaKeret(boolean isFelso) {
    if (isFelso) {
      System.out.print("\t");
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
//      Tabla.KERET = Tabla.KERET.substring(0,2) + Tabla.BETUK[oszlop] + Tabla.KERET.substring(3);
        if (oszlop == getOszlopokSzama() - 1) {
          System.out.print(Tabla.KERET + "+");
        } else {
          System.out.print(Tabla.KERET);
        }
      }
    } else {
      System.out.print("\t");
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
//      Tabla.KERET = Tabla.KERET.substring(0,2) + Tabla.BETUK[oszlop] + Tabla.KERET.substring(3);
        if (oszlop == getOszlopokSzama() - 1) {
          System.out.print(Tabla.KERET + "+");
        } else {
          System.out.print(Tabla.KERET);
        }
      }
      System.out.println();
      System.out.print("\t");
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
        System.out.print("  " + Tabla.BETUK[oszlop] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public void TablaRajzolas() {
    System.out.println();
    TablaKeret(true);
    for (int sor = getSorokSzama() - 1; sor >= 0; sor--) {
      System.out.print(sor + "\t");
      for (int oszlop = 0; oszlop < getOszlopokSzama(); oszlop++) {
        System.out.print("|");
        System.out.print(tablaMatrix[sor][oszlop]);
        if (oszlop == getOszlopokSzama() - 1) System.out.print("|");
      }
      System.out.println();
    }
    TablaKeret(false);
  }


}
