package ru.academits.phonebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;
import ru.academits.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Anna on 17.07.2017.
 */

@Controller
@RequestMapping("/phoneBook/rcp/api/v1")
public class PhoneBookController {

    private final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "getAllContacts", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllContacts() {
        logger.info("getAllContacts");
        return contactService.getAllContacts();
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody Contact contact) {
        logger.info(String.format("addContact: %s", contact));
        return contactService.addContact(contact);
    }

    @RequestMapping(value = "removeContact", method = RequestMethod.POST)
    public void removeContact(@RequestParam("index") int id) {
        logger.info(String.format("removeContact: пытаюсь удалить контакт с id - %d", id));
        boolean isRemove = false;
        isRemove = contactService.removeContact(id);
        if (!isRemove) {
            logger.info("removeContact: его уже нет");
        } else {
            logger.info("removeContact: успешно");
        }
    }

}


