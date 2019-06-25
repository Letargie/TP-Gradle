package contact.model;

import javax.persistence.*;

@Entity
public class Email {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Contact contact;

    protected Email() {}

    public Email(String email, Contact contact) {
        this.contact = contact;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
                "Email[id=%d, email='%s']",
                id, email);
    }

}