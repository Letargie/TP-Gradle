package contact.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String address;

    private String postalCode;

    private String apartment;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Contact> contacts;

    protected Address() {
        this.contacts = new ArrayList<>();
    }

    public Address(String address, String postalCode) {
        this.address = address;
        this.postalCode = postalCode;
        this.contacts = new ArrayList<>();
    }

    public Address(String address, String postalCode,String apartment) {
        this.address = address;
        this.postalCode = postalCode;
        this.apartment = apartment;
        this.contacts = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApartment() {
        return apartment;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return String.format(
                "Address[id=%d, address='%s', postalCode='%s', apartment='%s']",
                id, address, postalCode, apartment);
    }

}