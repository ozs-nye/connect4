# Connect 4 játék (JAVA - console)
A Connetc4 (kétszemélyes) játék konzolos JAVA implementációja. Az alapszabály leírása: [Wikipedia: Connect Four.](https://hu.wikipedia.org/wiki/Connect_four)

Ezen változatban ember játszik gép ellen. *(Ez könnyen feloldható, amennyiben deaktiváljuk az automatikus korong lerakást.)*

## Feladat
A Nyíregyházi Egyetem - [Programozási Technológiák (2024. őszi félév) kurzusleírásban](docs/kurzusleiras.pdf)  foglaltak megvalósítása.

## Működés

### Az alkalmazás indítása paraméter nélkül
Az alkalmazás paraméter nélküli indításakor megvizsgálja, hogy létezik-e, elérhető-e a **Main.java** metódusában beállított mentési fájl.

![mentesFajlNev](docs\mentesFajlNev.png "mentesFajlNev")

Ha létezik, akkor megpróbálja betölteni, majd a tábla méretét automatikusan hozzá igazítja. Ekkor a játék a kimentett állástól folytatható. *(Ez minden esetben a felhasználói lépés.)*

Ha nem található a meghatározott mentési fájl, akkor a játék a **Main.java** metódusában megadott sor és oszlop paraméterekkel legenerál egy üres játékteret, amiben az első lépés az emberi játékosé.
![tablaSorOszlop](docs\tablaSorOszlop.png "tablaSorOszlop")

Amennyiben a megadott sorok és oszlopok száma kisebb vagy nagyobb a feladatban meghatározottaktól (4 <= Sorok száma <= Oszlopok száma <= 12), akkor az alkalmazás a legnagyobb adható értékkel helyettesíti ezeket a változókat.
![tablaSorVizsgalat](docs\tablaSorVizsgalat.png "tablaSorOszlopVizsg")
[...]
![tablaOszlopVizsgalat](docs\tablaOszlopVizsgalat.png "tablaSorOszlopVizsg")