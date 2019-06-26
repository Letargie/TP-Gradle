package contact.controller;

import contact.model.Contact;
import contact.model.ContactNotFoundException;
import contact.model.ContactRepository;
import contact.model.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private EmailRepository emailRepo;

    @ModelAttribute("contacts")
    @GetMapping(value ="/contacts", produces = { MediaType.APPLICATION_XML_VALUE })
    List<Contact> all() {
        return contactRepository.findAll();
    }

    @GetMapping("/contacts/{id}")
    String consult(@PathVariable Long id, Model model){
        model.addAttribute("contact", this.one(id));
        return "consultContact";
    }

    @GetMapping(value ="/api/contacts/{id}", produces = { MediaType.APPLICATION_XML_VALUE })
    Contact one(@PathVariable Long id) {
        return contactRepository.findById(id)
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
        return contactRepository.save(newContact);
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
        contactRepository.deleteById(id);
    }
}

