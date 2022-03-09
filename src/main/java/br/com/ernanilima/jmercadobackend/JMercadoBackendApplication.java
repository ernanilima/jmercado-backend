package br.com.ernanilima.jmercadobackend;

import br.com.ernanilima.jmercadobackend.utils.DataTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class JMercadoBackendApplication implements CommandLineRunner {

	@Autowired
	private DataTemp dataTemp;

	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR"));
		SpringApplication.run(JMercadoBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dataTemp.createDataDatabase();
	}
}
