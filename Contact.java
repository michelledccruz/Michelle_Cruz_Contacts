/**
 * Name: Michelle Cruz
 * Date: October 26, 2026
 * Purpose: Defines shared contact information and demonstrates composition.
 */
public class Contact implements Displayable {
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;
    // Composition: each contact creates and owns an Address object.
    private final Address address;

    public Contact(String firstName, String lastName, String phone, String email,
                   String street, String city, String state, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = new Address(street, city, state, zip);
    }

    public String getType() { return "General"; }

    // Interface implementation: supplies the display behavior required by Displayable.
    @Override
    public void display() {
        System.out.println("Type: " + getType());
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
    }
}
