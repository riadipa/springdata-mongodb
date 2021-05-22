package aritra.edu.springmongo;

import aritra.edu.springmongo.service.ImportData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"aritra.edu.springmongo"})
public class SpringMongoApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringMongoApp.class, args);

        ImportData importData = context.getBean(ImportData.class);
        importData.importData();

    }

}
