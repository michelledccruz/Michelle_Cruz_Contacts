/**
 * Name: Michelle Cruz
 * Date: October 26, 2026
 * Purpose: Represents a friend contact using inheritance.
 */
// Inheritance: FriendContact extends the shared Contact base class.
public class FriendContact extends Contact {
    private final String sharedInterest;

    public FriendContact(String firstName, String lastName, String phone, String email,
                         String street, String city, String state, String zip,
                         String sharedInterest) {
        super(firstName, lastName, phone, email, street, city, state, zip);
        this.sharedInterest = sharedInterest;
    }

    @Override
    public String getType() { return "Friend"; }

    @Override
    public void display() {
        super.display();
        System.out.println("Shared interest: " + sharedInterest);
    }
}
