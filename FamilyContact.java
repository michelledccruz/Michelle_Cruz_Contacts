/**
 * Name: Michelle Cruz
 * Date: October 26, 2026
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

    @Override
    public void display() {
        super.display();
        System.out.println("Relationship: " + relationship);
    }
}
