package al.ikubinfo.hrmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import java.util.Arrays;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class HrManagementApplication {


    public static void main(String[] args) {
        SpringApplication.run(HrManagementApplication.class, args);
    }
}
