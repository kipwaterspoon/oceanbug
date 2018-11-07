package au.com.oceanbug.cloud.oceanbug;
//set PATH="D:\Trudi\Dev\apache-maven-3.6.0\bin";%PATH%
//mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
import au.com.oceanbug.cloud.oceanbug.service.SheetsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class OceanbugApplication {

	public static void main(String[] args) {
		SpringApplication.run(OceanbugApplication.class, args);
	}
}
