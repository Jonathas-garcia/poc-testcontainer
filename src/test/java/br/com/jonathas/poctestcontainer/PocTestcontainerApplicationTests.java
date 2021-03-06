package br.com.jonathas.poctestcontainer;

import br.com.jonathas.poctestcontainer.model.Mensagem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;


import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = MongoDbContextInitializer.class)
class PocTestcontainerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    protected static MongoDbContainer mongoDbContainer;

    @BeforeAll
    public static void setup(){
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }

    @Test
    void test() throws Exception {
        Mensagem msg = new Mensagem();
        msg.setTexto("Teste");
        msg.setId("abc123");
        msg.setAtivo(true);

        String json = new ObjectMapper().writeValueAsString(msg);

        mockMvc.perform(MockMvcRequestBuilders.post("/mensagens")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/mensagens")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].texto", Matchers.is("Teste")));

    }

    @Test
    void test2() {
        assertTrue(true);
    }

}
