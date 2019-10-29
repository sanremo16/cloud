package org.san.home.clients;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.san.home.clients.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ClientControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @Test
    @DatabaseSetup({"/dataset/client.xml"})
    public void getAll() throws Exception {
        this.mockMvc.perform(get("http://localhost:"+ port + "/clients")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(100, 200)))
                .andExpect(jsonPath("$[*].links[*].rel", containsInAnyOrder("self", "self")));
    }
/**
    @Test
    @DatabaseSetup({"/dataset/account.xml"})
    public void getByAccNum() throws Exception {
        this.mockMvc.perform(get("http://localhost:"+ port + "/accounts/show/11111111111111111111")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(100)));
    }

    @Test
    @DatabaseSetup({"/dataset/account.xml"})
    public void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:"+ port + "/accounts/delete/11111111111111111111")).andDo(print())
                .andExpect(status().isOk());
        assertEquals(1, accountService.findAll().size());
    }

    @Test
    public void add() throws Exception {
        String s = "{\"num\":\"555\",\"currencyType\":\"USD\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:"+ port + "/accounts/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(s))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @DatabaseSetup({"/dataset/account.xml"})
    public void update() throws Exception {
        String s = "{\"num\":\"11111111111111111111\",\"currencyType\":\"USD\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:"+ port + "/accounts/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(s))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencyType", is("USD")));
    }
**/
}
