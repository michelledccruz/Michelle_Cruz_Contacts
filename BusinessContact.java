/**
 * Name: Michelle Cruz
 * Date: November 1, 2026
 * Purpose: Represents a business contact using inheritance.
 */
// Inheritance: BusinessContact extends the shared Contact base class.
public class BusinessContact extends Contact {
    private final String company;

    public BusinessContact(String firstName, String lastName, String phone, String email,
                         String street, String city, String state, String zip,
                         String company) {
        super(firstName, lastName, phone, email, street, city, state, zip);
        this.company = company;
    }

    @Override
    public String getType() { return "Business"; }

    // Protected: only the class hierarchy needs this display hook.
    @Override
    protected String getDetail() { return "Company: " + company; }

    // Overload: callers can create a contact before an email is available.
    public BusinessContact(String firstName, String lastName, String phone,
                         String street, String city, String state, String zip,
                         String company) {
        super(firstName, lastName, phone, street, city, state, zip);
        this.company = company;
    }
}
