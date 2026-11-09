/**
 * Name: Michelle Cruz
 * Date: November 9, 2026
 * Purpose: Performs SQLite CRUD and reconstructs the existing contact subclasses.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class ContactRepository {
    private final String databaseUrl;
    private static final String COLUMNS = "type,first_name,last_name,phone,email,street,city,state,zip,detail";

    public ContactRepository(String databaseUrl) { this.databaseUrl = databaseUrl; }
    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    // Seed only when the table is first created, not whenever it becomes empty.
    public void initialize() throws SQLException {
        try (Connection conn = openConnection()) {
            conn.setAutoCommit(false);
            try {
                boolean exists;
                try (PreparedStatement pst = conn.prepareStatement(
                        "SELECT name FROM sqlite_master WHERE type='table' AND name='contacts'");
                     ResultSet rs = pst.executeQuery()) {
                    exists = rs.next();
                }
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("CREATE TABLE IF NOT EXISTS contacts ("
                        + "id INTEGER PRIMARY KEY, type TEXT NOT NULL CHECK(type IN ('Business','Family','Friend')),"
                        + "first_name TEXT NOT NULL CHECK(length(trim(first_name))>0),"
                        + "last_name TEXT NOT NULL CHECK(length(trim(last_name))>0),"
                        + "phone TEXT NOT NULL CHECK(length(trim(phone))>0),email TEXT NOT NULL,"
                        + "street TEXT NOT NULL,city TEXT NOT NULL,state TEXT NOT NULL,zip TEXT NOT NULL,"
                        + "detail TEXT NOT NULL CHECK(length(trim(detail))>0))");
                }
                if (!exists) {
                    insert(conn, new BusinessContact("Elena", "Torres", "703-555-0101", "elena@example.com",
                        "120 Sample Lane", "Woodbridge", "VA", "22191", "Sample Design Studio"));
                    insert(conn, new FamilyContact("Daniel", "Cruz", "571-555-0102",
                        "45 Example Court", "Manassas", "VA", "20110", "Cousin"));
                    insert(conn, new FriendContact("Maya", "Bennett", "540-555-0103",
                        "82 Demo Drive", "Fredericksburg", "VA", "22401", "Hiking"));
                }
                conn.commit();
            } catch (SQLException exception) {
                conn.rollback();
                throw exception;
            }
        }
    }

    public int create(Contact contact) throws SQLException {
        try (Connection conn = openConnection()) { return insert(conn, contact); }
    }
    private int insert(Connection conn, Contact contact) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO contacts(" + COLUMNS + ") VALUES(?,?,?,?,?,?,?,?,?,?)")) {
            bind(pst, contact);
            pst.executeUpdate();
        }
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
            if (!rs.next()) throw new SQLException("Saved ID was not returned.");
            int id = rs.getInt(1);
            contact.assignId(id);
            return id;
        }
    }
    public Optional<Contact> find(int id) throws SQLException {
        try (Connection conn = openConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM contacts WHERE id=?")) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }
    public ArrayList<Contact> listAll() throws SQLException {
        ArrayList<Contact> contacts = new ArrayList<>();
        try (Connection conn = openConnection(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM contacts ORDER BY last_name COLLATE NOCASE,first_name,id")) {
            while (rs.next()) contacts.add(map(rs));
        }
        return contacts;
    }
    public ArrayList<Contact> findByLastInitial(String initial) throws SQLException {
        if (initial == null || !initial.matches("[A-Za-z]"))
            throw new IllegalArgumentException("Enter one letter from A to Z.");
        ArrayList<Contact> contacts = new ArrayList<>();
        try (Connection conn = openConnection(); PreparedStatement pst = conn.prepareStatement(
                "SELECT * FROM contacts WHERE last_name LIKE ? ORDER BY last_name COLLATE NOCASE,first_name,id")) {
            pst.setString(1, initial + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) contacts.add(map(rs));
            }
        }
        return contacts;
    }
    public boolean update(int id, Contact contact) throws SQLException {
        String sql = "UPDATE contacts SET type=?,first_name=?,last_name=?,phone=?,email=?,"
            + "street=?,city=?,state=?,zip=?,detail=? WHERE id=?";
        try (Connection conn = openConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            bind(pst, contact);
            pst.setInt(11, id);
            return pst.executeUpdate() == 1;
        }
    }
    public boolean delete(int id) throws SQLException {
        try (Connection conn = openConnection();
             PreparedStatement pst = conn.prepareStatement("DELETE FROM contacts WHERE id=?")) {
            pst.setInt(1, id);
            return pst.executeUpdate() == 1;
        }
    }
    private void bind(PreparedStatement pst, Contact contact) throws SQLException {
        Address address = contact.getAddress();
        pst.setString(1, contact.getType());
        pst.setString(2, contact.getFirstName());
        pst.setString(3, contact.getLastName());
        pst.setString(4, contact.getPhone());
        pst.setString(5, contact.getEmail());
        pst.setString(6, address.getStreet());
        pst.setString(7, address.getCity());
        pst.setString(8, address.getState());
        pst.setString(9, address.getZip());
        pst.setString(10, contact.getCategoryValue());
    }
    private Contact map(ResultSet rs) throws SQLException {
        String first = rs.getString("first_name"), last = rs.getString("last_name");
        String phone = rs.getString("phone"), email = rs.getString("email");
        String street = rs.getString("street"), city = rs.getString("city");
        String state = rs.getString("state"), zip = rs.getString("zip"), detail = rs.getString("detail");
        Contact contact;
        switch (rs.getString("type")) {
            case "Business": contact = new BusinessContact(first,last,phone,email,street,city,state,zip,detail); break;
            case "Family": contact = new FamilyContact(first,last,phone,email,street,city,state,zip,detail); break;
            case "Friend": contact = new FriendContact(first,last,phone,email,street,city,state,zip,detail); break;
            default: throw new SQLException("Unknown stored contact category.");
        }
        contact.assignId(rs.getInt("id"));
        return contact;
    }
}
