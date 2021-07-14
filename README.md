# Snake

## Rövid ismertető

Ez a játék egy egyetemi kurzus keretein belül készült el. Céljai között az MVC tervezési minta megismerése és elsajátítása egy asztali és webes alkalmazás fejlesztése során.
Megkötések: a játékot java nyelven kellett fejeszteni, a desktop résznél JavaFX-et, a webes rész megvalósításához JSP technológiát kellett használni. Az adatbázisra nem jutott hangsúly a kurzus során, ezért egy egyszerű kis SQLite-ot használtunk.

A játék követelményspecifikációja az alábbi linken tekinthető meg:

https://okt.sed.hu/alkfejl1/gyakorlat/kotprog/snake/

## Fordítás & futtatás

A projektet a gyakorlati anyagban ismertett verziójú programokkal igyekeztem összeállítani,
így a fordítása / futtatása nem tér el elvileg a bemutatott módszerektől.

A sikeres futtatás érdekében át kell írni a jdbc url-jét ezen az útvonalon:
> core/src/main/resources/application.properties

az adatbázist magát is csomagoltam / felraktam a projektbe, viszont ha mégis balul
sülne el valami, akkor itt a DDL hozzá
```sql
create table one_player
(
    name nvarchar(50) constraint name primary key,
    score int default 0
);


create table two_player
(
    name_1 nvarchar(50),
    score_1 int default 0,
    name_2 nvarchar(50),
    score_2 int default 0,
    constraint names primary key (name_1, name_2)
);
```

Ezt követően a desktop rész az alábbi (root directoryben kiadott) parancsokkal futtatható:
> mvn install
> mvn javafx:run -f desktop/pom.xml

---

A webes rész futtatásához a web modulhoz kell létrehozni egy lokális tomcat run
configuration az idea-ban (web_war artifacthoz).

## Lehetséges eltérések a követelményektől

Az ételek színének változtatására nem adtam lehetőséget, ennek oka, hogy
az ételek színe számít a játékban (lásd FruitType.java).

- a kék lassít
- a sárga megfordítja a menetirányt
- a piros aktiválja a berserk módot:
  -  egyszemélyes játékban leharaphatja saját testrészét a kígyó
  -  kétszemélyes játék során pedig egymást csonkíthatják meg (magukba harapva vége a játéknak)
  -  ha egyik kígyó a másik fejét harapja le, akkor vége a játéknak
- a lila extra pontot ér
- a szürke a sima

A kék étel hatása stackelhet (mindig felezi a sebességet, persze 0-át nem lehet elérni) és
a berserk piros étellel hasonlóan 5 másodpercig hat. (a berserk hatásidejét meg is jelenítjük)

Ha nincs fal a pálya körül, akkor a pályáról kilépve a túloldalt újra belépünk.

exit gomb megnyomására a játék megáll, (beléphetünk a settings menübe és állítgathatjuk a kígyók színét
a többi gomb viszont disabled, és csak abba a játékba léphetünk vissza, ami már el lett indítva).

## Megjegyzések

A játék logikával és megvalósításával foglalkoztam a legtöbbet és mire a végére értem már nem tudtam úgy
"refaktorálni", hogy az egyes játékosoknak saját ID-je legyen. Legalábbis nem úgy, hogy az érthető és szép legyen.

Ez azt eredményezte, hogy az adatbázisban a játékos neve lett a primary key (tudom, bad practice).


## Extrák

A dao implementálása során hikariCP-t használtam, amivel könnyedén lehet connection poololni. (Habár tény
hogy amikor legelőször megnézzük a toplistát van egy kis lag mire inicializál a DataSource.java)

A scene váltásokat egy SceneManager osztály könnyíti meg ami lazy loadingolja az adott sceneket,
miközben az egyes controllerekbe injektálja a GameModel objektumot. Illetve így lehetőség van
`onSwitch`, `init` "callback" metódusok használatára (a GameController ősosztálynak hála).
(( Az init-re szükség volt, mivel a GameModel injektálás később történik valamivel, mint az FXML
fieldek injektálása. ugyanakkor ezeket szerettem volna bindolni egymással. ))

Toplista megvalósításánl fxml include-dal építettem fel a controller hiearchiát, így nem húzzuk le
az adatbázisból minden tab váltásra az adatokat csak egyszer mikor erre a scenere navigálunk
(`desktop/src/main/java/hu/alkfejl/controller/TopListController.java: 27`)
