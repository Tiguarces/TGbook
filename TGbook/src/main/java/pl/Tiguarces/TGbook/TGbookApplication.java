 package pl.Tiguarces.TGbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TGbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(TGbookApplication.class, args);
	}

}
