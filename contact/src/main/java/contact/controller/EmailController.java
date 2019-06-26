package contact.controller;

import contact.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class EmailController {
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private ContactRepository contactRepository;

    @ModelAttribute("emails")
    @GetMapping(value ="/emails", produces = { MediaType.APPLICATION_XML_VALUE })
    List<Email> all() {
        return emailRepository.findAll();
    }

    @GetMapping(value ="/api/emails/{id}", produces = { MediaType.APPLICATION_XML_VALUE })
    Email one(@PathVariable Long id) {
        return emailRepository.findById(id)
                .orElseThrow(() -> new EmailNotFoundException(id));
    }

    @PostMapping("/emails/delete/{id}")
    RedirectView delete(@PathVariable Long id){
        this.apiDelete(id);
        return new RedirectView("/emails");
    }

    @DeleteMapping(value="/api/emails/{id}", produces = { MediaType.APPLICATION_XML_VALUE })
    void apiDelete(@PathVariable Long id) {
        emailRepository.deleteById(id);
    }

    @GetMapping("/emails/edit")
    String showAdd(Email email, Model model){
        model.addAttribute("contacts", contactRepository.findAll());
        return "editEmail";
    }

    @PostMapping("/emails/edit")
    String edit(@RequestParam("id") Long id,
                @RequestParam("email") String email,
                @RequestParam("contactId") Long contactId,
                Model model) {
        Email newEmail = new Email(email, contactRepository.findById(contactId).orElse(null));
        newEmail.setId(id);
        this.apiEdit(newEmail);
        model.addAttribute("contacts", contactRepository.findAll());
        return "redirect:/emails";
    }

    @PostMapping(value = "/api/emails/edit", produces = { MediaType.APPLICATION_XML_VALUE })
    Email apiEdit(Email newEmail) {
        return emailRepository.save(newEmail);
    }
}

