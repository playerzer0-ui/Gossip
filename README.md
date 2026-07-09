# Gossip

Gossip is a Java/JSP messaging web application packaged as a Maven WAR. It provides user registration and login, one-to-one and group chats, encrypted message storage, media/file messages, profile editing, stories/status updates, reporting, blocking, and an admin area for handling reports and user suspension.

The application uses JSP pages for the UI, a single Jakarta Servlet controller for request handling, DAO classes for database access, and a local MySQL/MariaDB database.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [Default Test Data](#default-test-data)
- [Running Tests](#running-tests)
- [Configuration Notes](#configuration-notes)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Features

- User registration and login with BCrypt password hashing.
- One-to-one chat conversations.
- Group chat creation, editing, member invitations, member removal, and leaving groups.
- Text, image, video, and file messages.
- AES-based message encryption/decryption before displaying messages.
- Profile editing, profile pictures, password changes, and short bios.
- User search for starting chats and inviting group members.
- Stories/status uploads with image and video support.
- Story viewer tracking.
- User blocking.
- User reporting.
- Admin dashboard for report review, report dismissal, and user suspension/unsuspension.
- Progressive web app assets through `manifest.json` and `sw.js`.

## Tech Stack

- Java 11
- Jakarta Servlet API 5
- JSP
- Maven
- MySQL or MariaDB
- JDBC
- BCrypt (`org.mindrot:jbcrypt`)
- Jackson Databind
- JUnit 5
- Mockito
- Selenium
- Bootstrap, custom CSS, and vanilla JavaScript

## Project Structure

```text
.
+-- pom.xml
+-- mvnw / mvnw.cmd
+-- src
    +-- main
    |   +-- java
    |   |   +-- ajax              # Additional servlet/ajax helpers
    |   |   +-- business          # Domain model classes
    |   |   +-- controller        # Main Controller servlet
    |   |   +-- daos              # Database access classes
    |   |   +-- miscellaneous     # Utility classes, AES helpers, validation
    |   |   +-- sql               # Database setup scripts
    |   +-- webapp
    |       +-- css               # Stylesheets
    |       +-- js                # Client-side JavaScript
    |       +-- img               # Static images and icons
    |       +-- imageMessages     # Uploaded image message files
    |       +-- videoMessages     # Uploaded video message files
    |       +-- fileMessages      # Uploaded document/file message files
    |       +-- profilePictures   # User/group profile images
    |       +-- stories           # Uploaded story/status media
    |       +-- *.jsp             # Application pages
    +-- test
        +-- java
            +-- daos              # DAO unit/integration tests
            +-- botTesting        # Selenium browser test bots
```

## Prerequisites

Install the following before running the app:

- Java Development Kit 11 or newer.
- Maven, or use the included Maven wrapper.
- MySQL or MariaDB running locally on port `3306`.
- Apache Tomcat 10.x or another Jakarta Servlet 5 compatible servlet container.

Tomcat 10 is recommended because this project uses the `jakarta.servlet` package namespace.

## Database Setup

The DAO layer currently connects to MySQL with these values:

```text
host: localhost
port: 3306
username: root
password: empty string
database: gossip
```

To create the development database:

```bash
mysql -u root < src/main/java/sql/gossip.sql
```

To create the test database:

```bash
mysql -u root < src/main/java/sql/gossiptest.sql
```

If your MySQL user or password is different, update the connection details in:

```text
src/main/java/daos/Dao.java
```

## Running the Application

Build the WAR file:

```bash
./mvnw clean package
```

On Windows PowerShell:

```powershell
.\mvnw.cmd clean package
```

After the build completes, deploy the generated WAR:

```text
target/Gossip-1.0-SNAPSHOT.war
```

Copy the WAR into your Tomcat `webapps` directory, then start Tomcat. Once deployed, open:

```text
http://localhost:8080/Gossip-1.0-SNAPSHOT/
```

Useful direct pages:

```text
http://localhost:8080/Gossip-1.0-SNAPSHOT/login.jsp
http://localhost:8080/Gossip-1.0-SNAPSHOT/register.jsp
```

## Default Test Data

The `gossip.sql` script includes sample users. The seeded users share the password used by the Selenium login bot:

```text
rippleMMW1$
```

Example normal user:

```text
email: joe@gmail.com
password: rippleMMW1$
```

Example admin user:

```text
email: angel@gmail.com
password: rippleMMW1$
```

User roles are stored in the `users.userType` column:

```text
1 = normal user
2 = admin
```

## Running Tests

Run the Maven test suite:

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

The DAO tests expect a local MySQL/MariaDB instance and the `gossiptest` database. Import `src/main/java/sql/gossiptest.sql` before running them.

The `src/test/java/botTesting` classes are Selenium browser bots. They expect:

- Chrome installed locally.
- The bundled `src/test/java/chromedriver.exe` to be compatible with your Chrome version.
- The application deployed at `http://localhost:8080/Gossip-1.0-SNAPSHOT/`.
- The `gossip` database loaded with seed data.

## Configuration Notes

- The production/development database name is hard-coded as `gossip` in the controller and several DAO call sites.
- The shared DAO connection method is in `src/main/java/daos/Dao.java`.
- Uploaded files are written back under `src/main/webapp` relative to the deployed project path. This is convenient for local development, but for production a dedicated external upload directory is usually safer.
- Message type values used by the application:

```text
1 = text
2 = image
3 = video
4 = file
```

- Story type values:

```text
1 = image
2 = video
```

- Report status values:

```text
1 = unsolved/unseen
2 = solved
3 = ignored
4 = in review
```

## Troubleshooting

### Database connection fails

Check that MySQL/MariaDB is running on `localhost:3306`, that the `gossip` database exists, and that the credentials in `src/main/java/daos/Dao.java` match your local database user.

### Login does not work with seed users

Confirm that `src/main/java/sql/gossip.sql` was imported successfully and that you are using the seeded password:

```text
rippleMMW1$
```

### Pages show 404 after deployment

Make sure the WAR name is still `Gossip-1.0-SNAPSHOT.war`. The app URLs in the manifest and Selenium tests use:

```text
/Gossip-1.0-SNAPSHOT/
```

If you rename the WAR, update any hard-coded URLs that include the context path.

### Servlet errors on older Tomcat versions

Use Tomcat 10 or newer. Tomcat 9 uses the older `javax.servlet` namespace, while this project depends on `jakarta.servlet`.

### Uploaded media does not appear

Check that the upload directories exist under `src/main/webapp` and that the application process has permission to write files there:

```text
fileMessages
imageMessages
profilePictures
stories
videoMessages
```

## Contributing

1. Create a feature branch.
2. Keep changes focused and consistent with the existing JSP/Servlet/DAO structure.
3. Run the relevant Maven tests before opening a pull request.
4. Include database migration notes when changing table structure or seed data.
