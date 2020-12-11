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


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = PocTestcontainerApplicationTests.MongoDbInitializer.class)
class PocTestcontainerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private static MongoDbContainer mongoDbContainer;

    @ClassRule
    public static GenericContainer mongo = new GenericContainer("mongo:4.2")
            .withExposedPorts(27017);


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

    public static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {


            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongoDbContainer.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongoDbContainer.getPort()
//                    "spring.data.mongodb.uri=mongodb://" + mongoDbContainer.getContainerIpAddress() + "/testes"

            );
            values.applyTo(configurableApplicationContext);
        }
    }
}