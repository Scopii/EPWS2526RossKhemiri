# Entwicklungsprojekt WS 25/26 von Florian Roß und Karim Khemiri
DataDetective, ein Interaktiven Spiel-System zur Aufklärung über Manipulation und Visualisierung von und mit Daten.


## Problemstellung
Gesellschaftlich wird es durch Digitalisierung immer wichtiger Statistiken und Daten in der heutigen Zeit allgemein besser einschätzen, einordnen, filtern und bewerten zu können. Besonders durch visuelle Darstellungen werden Information irreführend oder emotional vermittelt, um die Interpretation zu beeinflussen. Dieser Prozess ist für viele Menschen aufgrund von limitiertem Fachwissen oder mangelnder Aufklärung teils schwer umsetzbar. Falscher Umgang dieser Inhalte führt zu Polarisation, unreflektierter Meinungsbildung und fördert Falschinformationen. 


## Projektübersicht
Das Projekt umfasst die Entwicklung eines interaktiven Spielsystems, in dem Nutzer manipulierte Statistiken analysieren und erkennen muss, welche visuellen oder strukturellen Änderungen die Wahrnehmung beeinflussen. 


## Zielsetzung
Das System soll spielerisch mithilfe visueller Darstellung, deren Gegenüberstellung und dessen Beurteilung, Medienkompetenz vermitteln und einen besseren Umgang mit Statistiken und Daten bei den Nutzern entwickeln. Ein Erfolgsfaktor könnte hierbei die Bewertung und Feedback sowie die Motivation zur Verbesserung der Durschnittswerte des Spielers. 


## Zielgruppe
Primär Personen im Alter von 15-50 Jahren mit Interesse zum Thema Medienkompetenz, Statistikevaluation und Lerninteresse. Je nach Rahmen oder Schwierigkeitsgrad werden auch indirekt jüngere und ältere Altersgruppen angesprochen. 


## Aufbau und Methodik
Als Grundidee bekommt der Spieler verschiedene Daten oder Statistiken als Beispielsätze präsentiert und dazugehörige Auswahlmöglichkeiten welche Faktoren zum aktuellen Beispiel verändert/angepasst wurden, oder auch wo relevanter Kontext fehlt, um Inhalte richtig einordnen zu können.  


Es können alle oder keine der Faktoren als Lösung ausgewählt werden und der Nutzer bekommt direkt danach vermittelt welche Punkte richtig und welche falsch sind. Die geänderten Faktoren werden dabei visuell präsentiert und oder in ihren Normalzustand transformiert/zurückgeführt. 

Der Spieler kann jederzeit eine Gesamtstatistik zu seiner eigenen Entwicklung betrachten, die vermittelt, wie der Nutzer sich über Zeit verbessert oder verschlechtert und welche Manipulationswerkzeuge wie gut vom Spieler erkannt werden. 


## Technische Umsetzung
Das Spiel wird basiert hauptsächlich auf 2D-Grafiken und Tile basierten Elementen der Struktur vergleichbar zu vielen heutigen Apps. 

- Game Engine 2D (UE): Viele mitgelieferte Features und Tools, mehr Komplexität für weniger Entiwcklungsaufwand, Unklare Performance auf Endgeräten, nicht lightweight 
- App über Kotlin (ähnlich zu MOCO), relativ einfache Umsetzung und leightweight App-Development, eingeschränkte Möglichkeiten zur spezifischen Visualisierung 
- Refresh JavaScript über D3, Umsetzung als Web-Basierte Anwendung z.B. wie Discord, unklare Performance,  
