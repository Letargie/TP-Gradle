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
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;


    @ModelAttribute("addresses")
    @GetMapping(value ="/addresses", produces = { MediaType.APPLICATION_XML_VALUE })
    List<Address> all() {
        return addressRepository.findAll();
    }

    @GetMapping(value ="/api/addresses/{id}", produces = { MediaType.APPLICATION_XML_VALUE })
    Address one(@PathVariable Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

    @GetMapping("/addresses/{id}")
    String consult(@PathVariable Long id, Model model){
        model.addAttribute("address", this.one(id));
        return "consultAddress";
    }

    @PostMapping("/addresses/delete/{id}")
    RedirectView delete(@PathVariable Long id){
        this.apiDelete(id);
        return new RedirectView("/addresses");
    }

    @DeleteMapping(value="/api/addresses/{id}", produces = { MediaType.APPLICATION_XML_VALUE })
    void apiDelete(@PathVariable Long id) {
        Address a = this.one(id);
        List<Contact> contacts = a.getContacts();
        for (Contact c: contacts) {
            List<Address> addresses = c.getAddresses();
            addresses.remove(a);
            c.setAddresses(addresses);
        }
        contactRepository.saveAll(contacts);
        a.setContacts(new ArrayList<>());
        addressRepository.save(a);
        addressRepository.deleteById(id);
    }



    @GetMapping("/addresses/edit")
    String showAdd(Address address,Model model){
        model.addAttribute("contacts", contactRepository.findAll());
        return "editAddress";
    }

    @PostMapping("/addresses/edit")
    String edit(@RequestParam("id") Long id,
                @RequestParam("postalCode") String postalCode,
                @RequestParam("address") String address,
                @RequestParam("apartment") String apartment,
                @RequestParam(name = "contactIds", required=false) List<Long> contactIds, Model model) {
        Address newAddress = new Address(address, postalCode, apartment);
        newAddress.setId(id);

        if(contactIds != null){
            ArrayList<Contact> contacts = new ArrayList<>();
            for (Long contactId: contactIds) {
                Contact c = contactRepository.findById(contactId).orElse(null);
                if(c != null){
                    contacts.add(c);
                    ArrayList<Address> l=new ArrayList<>(c.getAddresses());
                    l.add(newAddress);
                    c.setAddresses(l);
            }
            }
            newAddress.setContacts(contacts);
            //contactRepository.saveAll(contacts);
        }
        this.apiEdit(newAddress);
        model.addAttribute("address", newAddress);
        return "redirect:/addresses";
    }

    @PostMapping(value = "/api/addresses/edit", produces = { MediaType.APPLICATION_XML_VALUE })
    Address apiEdit(Address newAddress) {
        return addressRepository.save(newAddress);
    }

    @GetMapping("/addresses/edit/{id}")
    String showEdit(@PathVariable Long id, Model model){
        model.addAttribute("address", this.one(id));
        model.addAttribute("contacts", contactRepository.findAll());
        return "editAddress";
    }
}

