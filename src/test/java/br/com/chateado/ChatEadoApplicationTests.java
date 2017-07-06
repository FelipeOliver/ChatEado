package br.com.chateado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.chateado.ChatEadoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ChatEadoApplication.class)
@WebAppConfiguration
public class ChatEadoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
