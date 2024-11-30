package connect4.Csomagok;

import java.sql.*;

/**
 * dbKapcsolat.java
 * Adatbázis kapcsolatot megvalósítandó osztály
 */
public class dbKapcsolat {

  /**
   * Győzelmet követő számláló növelés az adatbázisban
   * @param dbFajlNev Az sqlite3 adatbázis fájl neve.
   * @param dbTabla A tárolásra használt tábla.
   * @param Jatekos A játékos azonosítója, akihez tartozó számlálót növelni szeretnénk.
   */
  public static void GyozelemNoveles(String dbFajlNev, String dbTabla, String Jatekos) {
    Connection sqlKapcsolat = null;
    String tablaJatekos = (Jatekos.equals(" E ")) ? "Ember" : "Gep";
    String SQLParancs = "UPDATE " + dbTabla + " SET " + tablaJatekos + " = " + tablaJatekos + " + 1";

    try {
      String url = "jdbc:sqlite:" + dbFajlNev;
      sqlKapcsolat = DriverManager.getConnection(url);
      System.err.println("SQL kapcsolat kiépítve.");

      Statement sqlKifejezes = sqlKapcsolat.createStatement();
      sqlKifejezes.executeUpdate(SQLParancs);
      sqlKifejezes.close();

    } catch (SQLException e) {
      System.err.println("Nem tudtam kiépíteni a kapcsolatot. (dbFajlNev: " + dbFajlNev + ", hibaüzenet: " + e.getMessage() + ")");
    } finally {
      try {
        if (sqlKapcsolat != null) {
          sqlKapcsolat.close();
          System.err.println("SQL kapcsolat lezárva.");
        }
      } catch (SQLException e) {
        System.err.println("Nem tudtam lezárni a kapcsolatot. (dbFajlNev: " + dbFajlNev + ", hibaüzenet: " + e.getMessage() + ")");
      }
    }
  }

  /**
   * Az adatbázisban tárolt győzelmek számának lekérdezése.
   * @param dbFajlNev Az sqlite3 adatbázis fájl neve.
   * @param dbTabla A tárolásra használt tábla.
   */
  public static void getGyozelmek(String dbFajlNev, String dbTabla) {
    Connection sqlKapcsolat = null;
    String SQLParancs = "SELECT * FROM " + dbTabla;
    try {
      String url = "jdbc:sqlite:" + dbFajlNev;
      sqlKapcsolat = DriverManager.getConnection(url);
      Statement sqlKifejezes = sqlKapcsolat.createStatement();
      ResultSet sqlEredmeny = sqlKifejezes.executeQuery(SQLParancs);

      while (sqlEredmeny.next()) {
        for (int i = 1; i <= sqlEredmeny.getMetaData().getColumnCount(); i++) {
          if (i == 1) {
            System.out.println(" * Ember: " + sqlEredmeny.getString(i));
          } else {
            System.out.println(" * Gép  : " + sqlEredmeny.getString(i));
          }
        }
      }
      sqlKifejezes.close();

    } catch (SQLException e) {
      System.err.println("Nem tudtam kiépíteni a kapcsolatot. (dbFajlNev: " + dbFajlNev + ", hibaüzenet: " + e.getMessage() + ")");
    }
  }

}
