/**
 * Name: Michelle Cruz
 * Date: October 18, 2026
 * Purpose: Runs the Week 1 console menu and displays sample contact objects.
 */
import java.util.Scanner;

public class ContactApp {
    public static void main(String[] args) {
        Contact[] contacts = {
            new BusinessContact("Elena", "Torres", "703-555-0101", "elena@example.com",
                "120 Sample Lane", "Woodbridge", "VA", "22191", "Sample Design Studio"),
            new FamilyContact("Daniel", "Cruz", "571-555-0102", "daniel@example.com",
                "45 Example Court", "Manassas", "VA", "20110", "Cousin"),
            new FriendContact("Maya", "Bennett", "540-555-0103", "maya@example.com",
                "82 Demo Drive", "Fredericksburg", "VA", "22401", "Hiking")
        };
        System.out.println("Project Week 1 - Contacts Application");
        System.out.println("Inheritance, Composition, and User Interactions");
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
                        for (Contact contact : contacts) {
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
