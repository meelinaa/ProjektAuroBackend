# Auro Backend: Virtueller Aktien-Broker 📈

Das Backend von **Auro** ist das Herzstück der Anwendung. Es verwaltet die gesamte Business-Logik, die Kommunikation mit der Datenbank und die Bereitstellung von RESTful APIs, um ein robustes und skalierbares System zu gewährleisten.

---

## Features

- **RESTful APIs:** Bieten eine Schnittstelle zur Kommunikation zwischen Frontend und Backend.
- **Datenbank-Integration:** Verwaltung und Speicherung der Daten mit PostgreSQL, bereitgestellt via Docker.
- **Business-Logik:** Verwaltung von Transaktionen, Portfolio-Berechnungen und Validierungen.
- **Web Scraping:** Integration von JSoup, um zusätzliche Finanzdaten von Webseiten zu extrahieren und bereitzustellen.
- **Testing:** Unit-Tests wurden hinzugefügt, Integrationstests folgen in einer späteren Iteration.
- **Skalierbarkeit:** Modular aufgebaute Architektur für einfache Erweiterungen.

---

## Technologien

- ☕ **Backend:** Java mit Spring Boot, Spring Data JPA, Lombok  
  Implementiert die gesamte Business-Logik sowie die Verbindung zur Datenbank.
- 🔗 **API:** RESTful Services  
  Ermöglicht die Kommunikation mit dem Frontend.
- 🗄️ **Datenbank:** PostgreSQL (via Docker)  
  Speicherung und effiziente Verwaltung aller Daten.
- 🐳 **Containerisierung:** Docker  
  Ermöglicht eine portable und skalierbare Bereitstellung des Backends.
- ✅ **Testing:**  
  Unit-Tests wurden implementiert, Integrationstests sind geplant.

---

## Projektstruktur

Dieses Backend ist Teil des Gesamtprojekts **Auro**. Das dazugehörige Frontend finden Sie hier:
- [Frontend Repository](https://github.com/meelinaa/ProjektAuroFrontend)

---

## Voraussetzungen

Um das Backend lokal auszuführen, benötigen Sie folgende Tools:

1. [Docker](https://www.docker.com/)
2. Java 17 oder höher
3. Maven (zum Bauen und Starten des Backends)
4. Git (zum Klonen des Repositories)

---

## Lokale Installation und Ausführung

### 1. Projekt klonen
```bash
 git clone https://github.com/dein-benutzername/ProjektAuroBackend.git
 cd ProjektAuroBackend
```

### 2. Backend starten

1. **Docker Compose ausführen:**

   Navigieren Sie in das Backend-Verzeichnis und starten Sie die Datenbank::
   ```bash
   docker-compose up
   ```

2. **Backend mit Maven starten:**

   Öffnen Sie ein neues Terminal im Backend-Verzeichnis und führen Sie folgendes aus:
   ```bash
   mvn spring-boot:run
   ```

   Das Backend wird standardmäßig unter `http://localhost:8080` ausgeführt.

---

## Software-Entwicklung und Planung

- **Planung und Architektur:** Erstellung der gesamten Backend-Architektur mit Fokus auf Modularität und Skalierbarkeit.  
- **Coding-Praktiken:** Clean Code-Prinzipien und Design Patterns für wartbaren und erweiterbaren Code.  
- **Iterative Entwicklung:** Neue Features werden kontinuierlich hinzugefügt und bestehende optimiert.  
- **Testing:** Implementierung von Unit-Tests, um die Business-Logik abzusichern. Integrationstests sind als nächster Schritt geplant.  

---

## Lizenz
Dieses Projekt steht unter der [MIT-Lizenz](LICENSE).

---

## Kontakt
Für Fragen oder Feedback kontaktieren Sie mich über [E-Mail](mailto:melinakiefer@hotmail.de) oder GitHub.
