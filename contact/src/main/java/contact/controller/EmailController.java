package contact.controller;

import contact.model.Contact;
import contact.model.ContactNotFoundException;
import contact.model.ContactRepository;
import contact.model.EmailRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class EmailController {

    private final EmailRepository repository;

    EmailController(EmailRepository repository) {
        this.repository = repository;
    }


}

