# Contacts Directory

**Michelle Cruz**

Contacts Directory is a Java terminal application that organizes business, family, and friend contacts. It stores records in SQLite and provides a numbered menu for adding, reading, searching, updating, and deleting contacts.

## Features

- Add Business, Family, or Friend contacts with category-specific details.
- Display all contacts in last-name order.
- Find a single contact by its saved ID.
- Search by the first letter of a last name.
- Display an existing contact before entering replacement values for an update.
- Confirm deletion before removing a saved record.
- Preserve saved contacts after the application closes.
- Validate menu choices, IDs, required fields, and five-digit ZIP codes.

## Setup and Run

1. Download the repository using **Code > Download ZIP**, then extract it.
2. Open the folder containing `ContactApp.java` in Visual Studio Code with Java support and a JDK installed.
3. Expand **Java Projects > Referenced Libraries**, click **+**, and select `sqlite-jdbc-3.36.0.3.jar` from the repository's main folder.
4. Open `ContactApp.java` and click **Run** above `main`.

The downloadable Week4 project ZIP has the driver inside `lib` and includes VS Code settings. For that package, open the extracted folder containing `ContactApp.java`, `lib`, and `.vscode`; add the JAR from `lib` if it is not already linked. The GitHub upload places the driver in the main folder, so select that location when running a GitHub download.

`Michelle_Cruz_contacts.db` is created in the working folder. Reopen the application from the same working folder to use the same saved data. The table is created and seeded with three fictional examples only on its first creation. Restarting does not duplicate examples or recreate deleted records. Do not publish databases containing personal contact information.

## User Interaction

The startup banner identifies the project week, assignment, student name, and database. The user types a menu number and presses Enter.

| Selection | Action |
| --- | --- |
| 1 | Add a contact |
| 2 | Display all contacts |
| 3 | Find a contact by ID |
| 4 | Find by first letter of last name |
| 5 | Update a contact |
| 6 | Delete a contact |
| 0 | Exit |

Adding prompts for contact type, first and last name, phone, optional email, street, city, state, ZIP, and the category detail. Business contacts store a company, family contacts store a relationship, and friend contacts store a shared interest. If email is omitted, the no-email constructor supplies `Not provided`. ZIP codes remain text to preserve leading zeros.

Updating loads the contact before requesting replacement values for all fields. Deleting asks the user to type `yes`; other answers cancel it. Missing records and invalid selections display readable messages.

## Example Input and Output

The following input illustrates adding a fictional friend contact:

```text
Selection: 1
Type (1 Business, 2 Family, 3 Friend): 3
First name: Alex
Last name: Rivera
Phone: 703-555-0104
Email (optional): alex@example.com
Street: 10 Example Way
City: Woodbridge
State: VA
ZIP code (5 digits): 22191
Shared interest: Music
```

On a new database the next generated ID is 4; existing records may change the ID:

```text
Contact 4 added successfully.
Contact ID: 4
Type: Friend
Name: Alex Rivera
Phone: 703-555-0104
Email: alex@example.com
Address: 10 Example Way, Woodbridge, VA 22191
Shared interest: Music
```

## Class Design

| Class or interface | Responsibility |
| --- | --- |
| `Displayable` | Declares the display contract |
| `Contact` | Abstract common contact state and final shared display method |
| `BusinessContact` | Business category and company information |
| `FamilyContact` | Family category and relationship information |
| `FriendContact` | Friend category and shared-interest information |
| `Address` | Owned address and formatted address text |
| `ContactRepository` | SQLite initialization, CRUD, filtering, and object reconstruction |
| `ContactApp` | User input, menu flow, validation, and action results |

## Database Design

One `contacts` table stores the fields needed by all three categories.

| Field | Storage and purpose |
| --- | --- |
| `id` | INTEGER PRIMARY KEY; generated saved-record ID |
| `type` | TEXT; Business, Family, or Friend |
| `first_name`, `last_name` | Required nonblank TEXT |
| `phone` | Required nonblank TEXT |
| `email` | TEXT; `Not provided` when omitted through the shorter constructor |
| `street`, `city`, `state`, `zip` | TEXT address components |
| `detail` | Required TEXT; company, relationship, or shared interest |

Prepared statements bind user data. Database reads reconstruct the corresponding contact subclass and assign the saved ID. Initialization creates and seeds the table in one transaction. Try-with-resources closes connections, statements, and result sets. All database access is handled by the repository rather than the domain classes.

## Design and Implementation Mapping

| Requirement | Implementation |
| --- | --- |
| Terminal input and output | `ContactApp` welcome message, menu, prompts, and results |
| Interface | `Displayable.display()` |
| Abstraction | Abstract `Contact`, abstract `getType()` and protected `getDetail()` |
| Inheritance | Business, Family, and Friend classes extend `Contact` |
| Composition | `Contact` creates and owns its `Address` |
| Polymorphism | Displayable references use shared `display()` and subtype-specific hooks |
| Constructors | Full and no-email overloads; protected base constructors; `super()` and `this()` |
| Access specifiers | Private data, protected constructors and hooks, public methods, package-level ID assignment |
| SQLite Create | `ContactRepository.create()` inserts a contact and returns its generated ID |
| SQLite Read | `find()`, `listAll()`, and `findByLastInitial()` query saved records |
| SQLite Update | `update()` replaces the selected record's values |
| SQLite Delete | `delete()` removes a record selected by ID after menu confirmation |
| Documentation | Source headers, inline comments, previous design documents, and this README |

## Verification

Development checks covered all three subtype mappings, database CRUD, missing IDs, last-initial searches, names containing apostrophes, leading-zero ZIP codes, and deleting all records without reseeding. Separate-process terminal tests confirmed persistence after restart and checked invalid input and cancelled deletion. The submitted screenshot document shows the welcome/menu view and the read operation displaying all three categories.

## Development Tasks and Skills

The project began with a user interaction plan and sample contact display. The next stage added the Displayable interface. Week 3 introduced the abstract Contact class, overloaded constructors, and access-specifier documentation. Week 4 added database-backed contact management. The final stage brings the existing code and documentation together.

Skills practiced include class design, inheritance, abstraction, composition, polymorphism, constructor chaining, terminal input validation, JDBC, SQL CRUD operations, and GitHub version tags.

## Language and Tools

Java, SQLite, JDBC, Visual Studio Code, and GitHub. SQLite JDBC 3.36.0.3 is supplied by the [Xerial SQLite JDBC project](https://github.com/xerial/sqlite-jdbc); license information is included inside the JAR.

## Documentation and Demonstration

The earlier Week 1 user interaction and Week 2 software design documents accompany the final submission. The application screenshot document provides execution evidence for startup and reading contacts. No video demonstration is included.

## Project Links

- [Repository](https://github.com/michelledccruz/Michelle_Cruz_Contacts)
- [Week 4 database release](https://github.com/michelledccruz/Michelle_Cruz_Contacts/releases/tag/Phase-4)

## Limitations and Future Improvements

The application is a local terminal program with no authentication or shared-user access. Updates request all replacement fields instead of editing one field at a time. Initial searches accept A-Z, and more detailed validation of phone and email formats could be added. Future improvements could include full-name searching, duplicate detection, data export, and a graphical interface.

## Project Summary

Contacts Directory provides a practical way to keep business, family, and friend contact information together. Each category shares common information while also keeping a company, relationship, or shared interest. SQLite makes the records available after the program closes, and the menu supports adding, displaying, finding, updating, and deleting them.

The design combines an interface and abstract class with inheritance and composition. Contact keeps the common fields and display process, while its subclasses provide the category behavior. Full and shorter constructors support records with or without email. Private fields and controlled public methods keep the data organized. Database queries rebuild these same classes instead of replacing the object-oriented design.

One challenge during completion was keeping the source files, SQLite driver, database, and GitHub uploads organized. Testing the menu one operation at a time made it easier to check the results. The existing sample records also provided a familiar starting point while database support was added. The final repository includes the Java sources, the JDBC driver, and setup instructions so the application can be run from a downloaded copy.

The most successful part of the project was extending the earlier design into a working application with persistent contact management. Further practice with JDBC and SQL would help make future improvements easier, especially more flexible searches and updates. The current application demonstrates the project's object-oriented concepts and database operations through a consistent terminal interface.
