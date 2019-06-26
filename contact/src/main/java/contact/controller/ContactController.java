package contact.controller;

import contact.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private AddressRepository addressRepository;

    @ModelAttribute("contacts")
    @GetMapping(value ="/contacts", produces = { MediaType.APPLICATION_XML_VALUE })
    List<Contact> all() {
        return contactRepository.findAll();
    }

    @GetMapping("/contacts/{id}")
    String consult(@PathVariable Long id, Model model){
        model.addAttribute("contact", this.one(id));
        model.addAttribute("addresses", addressRepository.findAll());
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
    String edit(Contact newContact, Model model, @RequestParam(value = "addressesIds", required = false) List<Long> addressesIds) {
        this.apiEdit(newContact);
        model.addAttribute("contact", newContact);
        model.addAttribute("addresses", addressRepository.findAll());
        return "redirect:/contacts/"+newContact.getId();
    }

    @PostMapping(value = "/api/contacts/edit", produces = { MediaType.APPLICATION_XML_VALUE })
    Contact apiEdit(Contact newContact) {
        return contactRepository.save(newContact);
    }

    @GetMapping("/contacts/edit/{id}")
    String showEdit(@PathVariable Long id, Model model){
        model.addAttribute("contact", this.one(id));
        model.addAttribute("addresses", addressRepository.findAll());
        return "editContact";
    }

    @PostMapping("/contacts/delete/{id}")
    RedirectView delete(@PathVariable Long id){
        this.apiDelete(id);
        return new RedirectView("/contacts");
    }

    @DeleteMapping(value="/api/contacts/{id}", produces = { MediaType.APPLICATION_XML_VALUE })
    void apiDelete(@PathVariable Long id) {
        Contact c = this.one(id);
        List<Address> addresses = c.getAddresses();
        for(Address a : addresses){
            List<Contact> contacts = a.getContacts();
            contacts.remove(c);
            a.setContacts(contacts);
        }
        addressRepository.saveAll(addresses);
        c.setAddresses(new ArrayList<>());
        contactRepository.save(c);
        contactRepository.deleteById(id);
    }
}

