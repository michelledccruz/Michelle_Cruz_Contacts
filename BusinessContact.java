/**
 * Name: Michelle Cruz
 * Date: October 18, 2026
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

    @Override
    public void display() {
        super.display();
        System.out.println("Company: " + company);
    }
}
