package ru.academits.shedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.academits.model.Contact;
import ru.academits.service.ContactService;

import java.util.List;

/**
 * Created by Anna on 24.07.2017.
 */

@Component
public class WormScheduler {

    private final Logger logger = LoggerFactory.getLogger(WormScheduler.class);


    @Autowired
    private ContactService contactService;

    @Scheduled(initialDelay = 10000, fixedRate = 15000)
    public void damageContactList() {
        List<Contact> list = contactService.getAllContacts();
        int listSize = list.size();
        if (listSize == 0) {
            logger.info("WormScheduler: список пуст");
            return;
        }
        int index = (int) (Math.random() * (listSize - 1));
        Contact contact = list.get(index);
        int id = contact.getId();
        contactService.removeContact(id);
        logger.info(String.format("WormScheduler delete: %s", contact));
    }
}
