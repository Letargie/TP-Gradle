package contact.model;

public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException(Long id) {
        super("Could not find contact " + id);
    }
}
