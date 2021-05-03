# Snake

## Fordítás & futtatás

Kedves javító! 

A projektet a gyakorlati anyagban ismertett verziójú programokkal igyekeztem összeállítani,
így a fordítása / futtatása nem tér el elvileg a bemutatott módszerektől.

A sikeres futtatás érdekében át kell írni a jdbc url-jét ezen az útvonalon: 
> core/src/main/resources/application.properties

Ezt követően a desktop rész az alábbi (root directoryben kiadott) parancsokkal futtatható:
> mvn install
> mvn javafx:run -f desktop/pom.xml

---

A webes rész futtatásához a web modulhoz kell létrehozni egy lokális tomcat run
configuration az idea-ban (web_war artifacthoz).
