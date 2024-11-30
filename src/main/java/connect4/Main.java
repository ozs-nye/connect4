package connect4;

import connect4.Csomagok.*;

import java.io.*;

public class Main {

  // Ezeket csak akkor vesszük figyelembe, ha nincs mentett állás, amit be kellene tölteni
  public static int tablaSorokSzama = 2;
  public static int tablaOszlopokSzama = 2;

  // Az alapértelmezett mentési név
  public static String mentesFajlNev = "mentes.txt";

  // Az adatbázis beállítások
  public static String dbFajlNev= "gyozelmek.db";
  public static String dbTabla  = "gyoztesek";


  public static void main(String[] args) {

    // Parancssori opciók beolvasása...
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("hs")) {
        System.out.println("Eddigi győzelmek: \n-----------------");
        dbKapcsolat.getGyozelmek(dbFajlNev, dbTabla);
      }
    }

    // Tábla generálása
    // ----------------

    // A tábla mérete beállítható így:
    //    Tabla.setOszlopokSzama(6);
    //    Tabla.setSorokSzama(7);
    //    Tabla tabla = new Tabla();

    // Illetve így is:
    //    Tabla tabla = new Tabla(6, 7); // Törölhető
//      Tabla tabla = new Tabla(tablaSorokSzama, tablaOszlopokSzama);
//      Jatek.JatekInicializalas();

    // A játék indítása...
    // -------------------
    //    Jatek jatek = new Jatek();

    // Gyakorlás...
//    dbKapcsolat.GyozelemNoveles(dbFajlNev, dbTabla, Tabla.EMBER);
//    dbKapcsolat.GyozelemNoveles(dbFajlNev, dbTabla, Tabla.GEP);

    try {
      Fajlkezelo.BeolvasFajlbol(mentesFajlNev);
    } catch (FileNotFoundException e) {
      System.err.println("Mentett játékállás nem található. Új játék indítása...");
      Tabla tabla = new Tabla(tablaSorokSzama, tablaOszlopokSzama);
      Jatek.JatekInicializalas();
    } catch (IOException e) {
//      throw new RuntimeException(e);
      System.err.println("Hiba történt: " + e.getMessage());
    } finally {
      System.err.println("Kilépek...");
    }

  }
}
