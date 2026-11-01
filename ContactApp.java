/**
 * Name: Michelle Cruz
 * Date: November 1, 2026
 * Purpose: Runs the Week 3 console menu and displays sample contact objects.
 */
import java.util.Scanner;

public class ContactApp {
    public static void main(String[] args) {
        // Both full and no-email constructors are exercised below.
        // Interface references hold three different concrete contact types.
        Displayable[] contacts = {
            new BusinessContact("Elena", "Torres", "703-555-0101", "elena@example.com",
                "120 Sample Lane", "Woodbridge", "VA", "22191", "Sample Design Studio"),
            new FamilyContact("Daniel", "Cruz", "571-555-0102",
                "45 Example Court", "Manassas", "VA", "20110", "Cousin"),
            new FriendContact("Maya", "Bennett", "540-555-0103",
                "82 Demo Drive", "Fredericksburg", "VA", "22401", "Hiking")
        };
        System.out.println("Project Week 3 - Contacts Application");
        System.out.println("Abstraction, Constructors, and Access Specifiers");
        System.out.println("Michelle Cruz");
        System.out.println("Welcome! Type a menu number and press Enter.");
        System.out.println("This demo contains fictional sample contacts.");
        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n1. Display sample contacts");
                System.out.println("2. Exit");
                System.out.print("Selection: ");
                if (!input.hasNextLine()) { break; }
                String selection = input.nextLine().trim();
                switch (selection) {
                    case "1":
                        System.out.println("\nCONTACT DIRECTORY");
                        // Shared display() calls the abstract hooks implemented by each subtype.
                        for (Displayable contact : contacts) {
                            contact.display();
                            System.out.println();
                        }
                        break;
                    case "2":
                        System.out.println("Thank you for using the Contacts Application. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid selection. Please enter 1 or 2.");
                }
            }
        }
    }
}
