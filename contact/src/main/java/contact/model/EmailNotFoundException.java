package contact.model;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(Long id) {
        super("Could not find email " + id);
    }
}
