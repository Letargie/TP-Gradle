package contact.controller;

import contact.model.Contact;
import contact.model.ContactNotFoundException;
import contact.model.ContactRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ContactController {

    private final ContactRepository repository;

    ContactController(ContactRepository repository) {
        this.repository = repository;
    }

    @ModelAttribute("contacts")
    @GetMapping(value ="/contacts", produces = { MediaType.APPLICATION_XML_VALUE })
    List<Contact> all() {
        return repository.findAll();
    }

    @GetMapping("/contacts/{id}")
    String consult(@PathVariable Long id, Model model){
        model.addAttribute("contact", this.one(id));
        return "consultContact";
    }

    @GetMapping(value ="/api/contacts/{id}", produces = { MediaType.APPLICATION_XML_VALUE })
    Contact one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
    }

    @GetMapping("/contacts/edit")
    String showAdd(Contact contact){
        return "editContact";
    }

    @PostMapping("/contacts/edit")
    String edit(Contact newContact, Model model) {
        this.apiEdit(newContact);
        model.addAttribute("contact", newContact);
        return "redirect:/contacts/"+newContact.getId();
    }

    @PostMapping(value = "/api/contacts/edit", produces = { MediaType.APPLICATION_XML_VALUE })
    Contact apiEdit(Contact newContact) {
        return repository.save(newContact);
    }

    @GetMapping("/contacts/edit/{id}")
    String showEdit(@PathVariable Long id, Model model){
        model.addAttribute("contact", this.one(id));
        return "editContact";
    }

    @PostMapping("/contacts/delete/{id}")
    RedirectView delete(@PathVariable Long id){
        this.apiDelete(id);
        return new RedirectView("/contacts");
    }

    @DeleteMapping(value="/api/contacts/{id}", produces = { MediaType.APPLICATION_XML_VALUE })
    void apiDelete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

