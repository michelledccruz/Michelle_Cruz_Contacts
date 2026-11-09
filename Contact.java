/**
 * Name: Michelle Cruz
 * Date: November 9, 2026
 * Purpose: Defines shared contact information and demonstrates composition.
 */
public abstract class Contact implements Displayable {
    private int id;
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

    public int getId() { return id; }
    // Package access: the repository assigns the saved record ID.
    void assignId(int value) { id = value; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public Address getAddress() { return address; }
    public abstract String getCategoryValue();

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
        System.out.println("Contact ID: " + id);
        System.out.println("Type: " + getType());
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println(getDetail());
    }
}
