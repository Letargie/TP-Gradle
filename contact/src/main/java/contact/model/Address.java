package contact.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String address;

    private String postalCode;

    private String apartment;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="CONTACTS")
    private List<Contact> contacts;

    protected Address() {}

    public Address(String address, String postalCode) {
        this.address = address;
        this.postalCode = postalCode;
    }

    public Address(String address, String postalCode,String apartment) {
        this.address = address;
        this.postalCode = postalCode;
        this.apartment = apartment;
    }


    @Override
    public String toString() {
        return String.format(
                "Address[id=%d, address='%s', postalCode='%s', apartment='%s']",
                id, address, postalCode, apartment);
    }

}