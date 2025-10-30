# Entwicklungsprojekt WS 25/26 von Florian Roß und Karim Khemiri
DataDetective, ein Interaktiven Spiel-System zur Aufklärung über Manipulation und Visualisierung von und mit Daten.


## Problemstellung
Gesellschaftlich wird es durch Digitalisierung immer wichtiger Statistiken und Daten in der heutigen Zeit allgemein besser einschätzen, einordnen, filtern und bewerten zu können. Besonders durch visuelle Darstellungen werden Information irreführend oder emotional vermittelt, um die Interpretation zu beeinflussen. Dieser Prozess ist für viele Menschen aufgrund von limitiertem Fachwissen oder mangelnder Aufklärung teils schwer umsetzbar. Korrelation wird von vielen oft als ausreichender Faktor für Kausalität gehalten. Falscher Umgang dieser Inhalte führt zu Polarisation, unreflektierter Meinungsbildung und fördert Falschinformationen.


## Projektübersicht
Das Projekt umfasst die Entwicklung eines interaktiven Spielsystems, in dem Nutzer veränderte und angepasste Statistiken analysieren und erkennen müssen, welche visuellen oder strukturellen Änderungen die Wahrnehmung beeinflussen. 


## Zielsetzung
Das System soll spielerisch mithilfe visueller Darstellung, deren Gegenüberstellung und dessen Beurteilung, Medienkompetenz vermitteln und einen besseren Umgang mit Statistiken und Daten bei den Nutzern entwickeln. Es soll Wissenschaftskommunikation erleichtern und kritische Denkweisen bei den Nutzern entwickeln, um ein besseres allgemeines Verständniss für unterschiedlicher Themenbereiche zu ermöglichen. Fokus liegt hierbei darauf generelles Statistikverständniss aufzubauen statt nur auf aktiv manipulierte Inhalte einzugehen.


## Zielgruppe
Primär sollen Personen im Alter von 15-50 Jahren mit Interesse zum Thema Medienkompetenz, Statistikevaluation, Wissenschaftskommunikation und oder Lerninteresse angesprochen werden. Je nach Rahmen, Schwierigkeitsgrad oder Themeneingrenzungen der Datensätze können sekundär oder indirekt jüngere und ältere Altersgruppen angesprochen.


## Aufbau und Methodik
Als Grundidee bekommt der Spieler verschiedene Daten oder Statistiken als Beispieldatensätze präsentiert und dazugehörige Auswahlmöglichkeiten, ähnlich wie es bei Quiz-Formaten der Fall ist, welche Faktoren zum aktuellen Beispiel verändert/angepasst wurden, oder auch wo relevanter Kontext fehlt, um Inhalte richtig einordnen zu können. 

Es können alle oder keine der Faktoren als Lösung ausgewählt werden und der Nutzer bekommt direkt danach vermittelt welche Punkte richtig und welche falsch sind. 
Die geänderten Faktoren werden dabei visuell präsentiert und oder in ihren Normalzustand transformiert/zurückgeführt. 

Der Spieler kann jederzeit eine Gesamtstatistik zu seiner eigenen Entwicklung betrachten, die vermittelt, wie der Nutzer sich über Zeit verbessert oder verschlechtert und welche Manipulationswerkzeuge wie gut vom Spieler erkannt werden. Diese Statistik ist in Tendenzen für kurzweilige Unterschiede und Entwicklungen des Nutzers und Gesamtprogression für die gesamte Entwicklung des Nutzers unterteilt. Die Einsicht soll dem Nutzer eine bessere Lernerfahrung ermöglichen und auf schwächen einzugehen.

Die Trainings-Datensätze werden künstlich anhand von Realstatistiken gebildet, da dies nicht nur generelles Wissen vermittelt sondern auch ermöglicht die Aufgaben gezielt an die Ziele des System anzupassen und zu iterieren.


## Technische Umsetzung
Das Spiel wird basiert hauptsächlich auf 2D-Grafiken und Tile basierten Elementen der Struktur vergleichbar zu vielen heutigen Apps. 

- Game Engine 2D (UE): Viele mitgelieferte Features und Tools, mehr Komplexität für weniger Entiwcklungsaufwand, Unklare Performance auf Endgeräten, nicht lightweight 
- App über Kotlin (ähnlich zu MOCO), relativ einfache Umsetzung und leightweight App-Development, eingeschränkte Möglichkeiten zur spezifischen Visualisierung 
- Refresh JavaScript über D3, Umsetzung als Web-Basierte Anwendung z.B. wie Discord, unklare Performance,  
