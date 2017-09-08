package ru.academits;

import com.google.gson.Gson;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.academits.model.Contact;

import java.util.List;

import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhonebookSpringApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Test
    public void contextLoads() {
    }

    @Autowired
    private TestRestTemplate restTemplate;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test1GetAll() {
        List<?> contactList = this.restTemplate.getForObject("/phoneBook/rcp/api/v1/getAllContacts", List.class);
        assertEquals(contactList.size(), 1);
    }

    @Test
    public void test2AddContact() throws Exception {
        List<?> contactList = this.restTemplate.getForObject("/phoneBook/rcp/api/v1/getAllContacts", List.class);
        assertEquals(contactList.size(), 1);

        Contact contact = new Contact();
        contact.setFirstName("Вася");
        contact.setLastName("Пупкин");
        contact.setPhone("1234567890");

        Gson gson = new Gson();
        String json = gson.toJson(contact);

        mockMvc.perform(post("/phoneBook/rcp/api/v1/addContact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());

        contactList = this.restTemplate.getForObject("/phoneBook/rcp/api/v1/getAllContacts", List.class);
        assertEquals(contactList.size(), 2);
    }

    @Test
    public void test3RemoveContact() throws Exception {
        List<?> contactList = this.restTemplate.getForObject("/phoneBook/rcp/api/v1/getAllContacts", List.class);
        assertEquals(contactList.size(), 2);

        mockMvc.perform(post("/phoneBook/rcp/api/v1/removeContact")
                .param("index", "1"))
                .andExpect(status().isOk());

        contactList = this.restTemplate.getForObject("/phoneBook/rcp/api/v1/getAllContacts", List.class);
        assertEquals(contactList.size(), 1);

        mockMvc.perform(post("/phoneBook/rcp/api/v1/removeContact")
                .param("index", "2"))
                .andExpect(status().isOk());

        contactList = this.restTemplate.getForObject("/phoneBook/rcp/api/v1/getAllContacts", List.class);
        assertEquals(contactList.size(), 0);
    }
}
