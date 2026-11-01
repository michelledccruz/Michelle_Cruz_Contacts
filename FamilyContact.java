/**
 * Name: Michelle Cruz
 * Date: November 1, 2026
 * Purpose: Represents a family contact using inheritance.
 */
// Inheritance: FamilyContact extends the shared Contact base class.
public class FamilyContact extends Contact {
    private final String relationship;

    public FamilyContact(String firstName, String lastName, String phone, String email,
                         String street, String city, String state, String zip,
                         String relationship) {
        super(firstName, lastName, phone, email, street, city, state, zip);
        this.relationship = relationship;
    }

    @Override
    public String getType() { return "Family"; }

    // Protected: only the class hierarchy needs this display hook.
    @Override
    protected String getDetail() { return "Relationship: " + relationship; }

    // Overload: callers can create a contact before an email is available.
    public FamilyContact(String firstName, String lastName, String phone,
                         String street, String city, String state, String zip,
                         String relationship) {
        super(firstName, lastName, phone, street, city, state, zip);
        this.relationship = relationship;
    }
}
