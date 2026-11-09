/**
 * Name: Michelle Cruz
 * Date: November 9, 2026
 * Purpose: Runs the Week 4 database-backed Contacts menu and validates input.
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ContactApp {
    private final Scanner input;
    private final ContactRepository repository;

    // Constructor initializes the menu's input and database dependencies.
    public ContactApp(Scanner input, ContactRepository repository) {
        this.input = input;
        this.repository = repository;
    }
    public static void main(String[] args) {
        System.out.println("Project Week 4 - Contacts Application: Database Interactions");
        System.out.println("Michelle Cruz");
        System.out.println("Welcome! Enter a menu number and press Enter to manage saved contacts.");
        System.out.println("Use IDs to find, update, or delete. Records are saved in Michelle_Cruz_contacts.db.");
        System.out.println("Three fictional sample contacts are added only when the database table is first created.");
        try (Scanner input = new Scanner(System.in)) {
            ContactRepository repository = new ContactRepository("jdbc:sqlite:Michelle_Cruz_contacts.db");
            repository.initialize();
            new ContactApp(input, repository).run();
        } catch (SQLException exception) {
            System.out.println("Could not open the database: " + exception.getMessage());
            System.out.println("Check that the SQLite driver is in Referenced Libraries and this folder is writable.");
        }
    }
    private void run() {
        try {
            while (true) {
                System.out.println("\n1. Add contact\n2. Display all contacts\n3. Find contact by ID"
                    + "\n4. Find by first letter of last name\n5. Update contact\n6. Delete contact\n0. Exit");
                String choice = readLine("Selection: ");
                if (choice.equals("0")) break;
                try {
                    switch (choice) {
                        case "1":
                            int id = repository.create(readContact());
                            System.out.println("Contact " + id + " added successfully.");
                            show(repository.find(id).orElseThrow(() -> new IllegalStateException("Saved contact missing.")));
                            break;
                        case "2": showList(repository.listAll()); break;
                        case "3":
                            Contact found = repository.find(readId()).orElse(null);
                            if (found == null) System.out.println("Contact not found."); else show(found);
                            break;
                        case "4":
                            String initial;
                            do {
                                initial = readLine("First letter of last name (A-Z): ");
                                if (!initial.matches("[A-Za-z]")) System.out.println("Enter one letter from A to Z.");
                            } while (!initial.matches("[A-Za-z]"));
                            showList(repository.findByLastInitial(initial));
                            break;
                        case "5": update(); break;
                        case "6": delete(); break;
                        default: System.out.println("Invalid selection. Choose 0 through 6.");
                    }
                } catch (SQLException exception) {
                    System.out.println("Database action failed: " + exception.getMessage());
                }
            }
        } catch (NoSuchElementException exception) {
            System.out.println("Input ended.");
        }
        System.out.println("Thank you for using the Contacts Application. Goodbye!");
    }
    private void update() throws SQLException {
        int id = readId();
        Contact existing = repository.find(id).orElse(null);
        if (existing == null) { System.out.println("Contact not found."); return; }
        System.out.println("Current contact:");
        show(existing);
        System.out.println("Enter replacement values for all fields.");
        if (repository.update(id, readContact())) {
            System.out.println("Contact " + id + " updated successfully.");
            show(repository.find(id).get());
        } else System.out.println("Contact not found; no update was made.");
    }
    private void delete() throws SQLException {
        int id = readId();
        Contact existing = repository.find(id).orElse(null);
        if (existing == null) { System.out.println("Contact not found."); return; }
        show(existing);
        if (!readLine("Delete this contact? Type yes to confirm: ").equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled."); return;
        }
        System.out.println(repository.delete(id) ? "Contact " + id + " deleted successfully."
            : "Contact not found; no deletion was made.");
    }
    private Contact readContact() {
        String type;
        do {
            type = readLine("Type (1 Business, 2 Family, 3 Friend): ");
            if (!type.matches("[123]")) System.out.println("Choose 1, 2, or 3.");
        } while (!type.matches("[123]"));
        String first = required("First name: "), last = required("Last name: ");
        String phone = required("Phone: "), email = readLine("Email (optional): ");
        String street = required("Street: "), city = required("City: ");
        String state = required("State: "), zip;
        do {
            zip = readLine("ZIP code (5 digits): ");
            if (!zip.matches("[0-9]{5}")) System.out.println("Enter exactly five digits.");
        } while (!zip.matches("[0-9]{5}"));
        String detail = required(type.equals("1") ? "Company: " : type.equals("2") ? "Relationship: " : "Shared interest: ");
        // Overloaded constructors preserve Week 3's no-email behavior.
        if (type.equals("1")) return email.isEmpty()
            ? new BusinessContact(first,last,phone,street,city,state,zip,detail)
            : new BusinessContact(first,last,phone,email,street,city,state,zip,detail);
        if (type.equals("2")) return email.isEmpty()
            ? new FamilyContact(first,last,phone,street,city,state,zip,detail)
            : new FamilyContact(first,last,phone,email,street,city,state,zip,detail);
        return email.isEmpty()
            ? new FriendContact(first,last,phone,street,city,state,zip,detail)
            : new FriendContact(first,last,phone,email,street,city,state,zip,detail);
    }
    private void showList(ArrayList<Contact> contacts) {
        System.out.println("\nCONTACT DIRECTORY - " + contacts.size() + " record(s)");
        if (contacts.isEmpty()) System.out.println("No matching contacts.");
        for (Contact contact : contacts) show(contact);
    }
    private void show(Displayable contact) {
        // Interface polymorphism: final display() invokes each subtype's abstract hooks.
        contact.display();
        System.out.println();
    }
    private int readId() {
        while (true) {
            try {
                int id = Integer.parseInt(readLine("Contact ID: "));
                if (id > 0) return id;
            } catch (NumberFormatException exception) { /* Repeat the prompt. */ }
            System.out.println("Enter a positive whole-number ID.");
        }
    }
    private String required(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (!value.isEmpty()) return value;
            System.out.println("This field is required.");
        }
    }
    private String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }
}
