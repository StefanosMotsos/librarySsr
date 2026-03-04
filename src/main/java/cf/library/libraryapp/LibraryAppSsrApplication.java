package cf.library.libraryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LibraryAppSsrApplication {

	public static void main(String[] args) {

        SpringApplication.run(LibraryAppSsrApplication.class, args);
	}

}
