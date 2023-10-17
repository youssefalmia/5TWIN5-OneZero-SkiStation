package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.services.*;

@SpringBootTest
class GestionStationSkiApplicationTests {

	@Autowired
	ICourseServices courseServices;

	@Test
	void contextLoads() {
	}

}
