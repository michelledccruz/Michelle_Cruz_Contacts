# Contacts Application - Week 4
Michelle Cruz - SDC330L - Database Interactions

Continues the existing Week 3 Contacts project. It preserves the Displayable
interface, abstract Contact class, protected constructors and getDetail hook,
final display method, owned Address, and three concrete contact categories.
Getters and a saved ID support database operations while fields remain private.

## Run
Extract All and open the extracted folder containing ContactApp.java, lib,
and .vscode directly in VS Code. Open ContactApp.java and select Run above main.
No second inner folder is required. The course SQLite driver is included in lib.
If needed, add lib/sqlite-jdbc-3.36.0.3.jar through Java Projects > Referenced Libraries > +.

## Menu
1 Add, 2 Display all, 3 Find by ID, 4 Find by last-name initial,
5 Update, 6 Delete, 0 Exit. Updates show the saved contact before asking for all
replacement fields. Delete requires typing yes. Email is optional; ZIP is five
digits and stored as text. Fictional examples include all three contact types.

## SQLite
Michelle_Cruz_contacts.db is created in the working folder. The contacts table
contains id, type, first_name, last_name, phone, email, street, city, state, zip,
and detail (company, relationship, or shared interest depending on the category).
Initialization creates and seeds the table in one transaction only on first
creation. Later runs reuse it without duplicating or restoring deleted examples.
Prepared statements bind data. Queries reconstruct BusinessContact, FamilyContact,
and FriendContact objects. Connections, statements, and results close automatically.
Do not upload a database containing personal information to GitHub.

## Verification
Database CRUD, all subtype reconstruction, last-initial searches, missing IDs,
updates, deletion, restart persistence, and absence of repeated seeding are tested.

## Submission
Capture your own VS Code runs for the required Word document; follow
SCREENSHOT_STEPS.txt. Update the existing Michelle_Cruz_Contacts repository,
then create a new Phase-4 tag and Phase #4 - Database Interactions release.
Keep Phase-1, Phase-2, and Phase-3 unchanged. Submit the published Phase-4 URL
and your Word screenshot document. Add the instructor as a collaborator when
their account details are available.

SQLite JDBC 3.36.0.3: https://github.com/xerial/sqlite-jdbc
Upstream licensing is distributed inside the JAR.
