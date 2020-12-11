package br.com.jonathas.poctestcontainer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class MongoDbContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        TestPropertyValues values = TestPropertyValues.of(
                "spring.data.mongodb.host=" + PocTestcontainerApplicationTests.mongoDbContainer.getContainerIpAddress(),
                "spring.data.mongodb.port=" + PocTestcontainerApplicationTests.mongoDbContainer.getPort()
        );
        values.applyTo(configurableApplicationContext);
    }
}