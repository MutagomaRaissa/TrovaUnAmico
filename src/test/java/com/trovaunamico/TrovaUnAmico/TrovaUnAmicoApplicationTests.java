package com.trovaunamico.TrovaUnAmico;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test") // Uses application-test.properties
class TrovaUnAmicoApplicationTests {

    @Test
    void contextLoads() {
        // Context load test
    }
}