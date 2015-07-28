package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.model.TagCloud;
import app.repository.TagCloudRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	TagCloudRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Override
	public void run(String... strings) throws Exception {
		// save a couple of customers
		TagCloud tc = new TagCloud();

		tc.setNameTagCloud("Java");
		tc.setTipTagCloud("programming language");
		repository.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("C#");
		tc.setTipTagCloud("programming language");
		repository.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("C++");
		tc.setTipTagCloud("programming language");
		repository.save(tc);

		System.out.println("tcs found with findAll():");
		System.out.println("-------------------------------");
		for (TagCloud tc1 : repository.findAll()) {
			System.out.println(tc1.getNameTagCloud() + " ID:  "
					+ tc1.getIdTagCloud());
		}
		System.out.println();
	}

}