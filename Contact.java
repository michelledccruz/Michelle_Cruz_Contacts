/**
 * Name: Michelle Cruz
 * Date: November 1, 2026
 * Purpose: Defines shared contact information and demonstrates composition.
 */
public abstract class Contact implements Displayable {
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;
    // Composition: each contact creates and owns an Address object.
    private final Address address;

    protected Contact(String firstName, String lastName, String phone, String email,
                   String street, String city, String state, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = new Address(street, city, state, zip);
    }

    // Abstraction: every concrete contact must supply its category and detail.
    public abstract String getType();
    protected abstract String getDetail();

    // Overloaded constructor supplies an explicit default for a missing email.
    protected Contact(String firstName, String lastName, String phone,
                      String street, String city, String state, String zip) {
        this(firstName, lastName, phone, "Not provided", street, city, state, zip);
    }

    // Interface implementation: supplies the display behavior required by Displayable.
    @Override
    public final void display() {
        System.out.println("Type: " + getType());
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println(getDetail());
    }
}
