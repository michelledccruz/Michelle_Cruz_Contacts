/**
 * Name: Michelle Cruz
 * Date: November 9, 2026
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
    public String getCategoryValue() { return sharedInterest; }

    // Protected: only the class hierarchy needs this display hook.
    @Override
    protected String getDetail() { return "Shared interest: " + sharedInterest; }

    // Overload: callers can create a contact before an email is available.
    public FriendContact(String firstName, String lastName, String phone,
                         String street, String city, String state, String zip,
                         String sharedInterest) {
        super(firstName, lastName, phone, street, city, state, zip);
        this.sharedInterest = sharedInterest;
    }
}
