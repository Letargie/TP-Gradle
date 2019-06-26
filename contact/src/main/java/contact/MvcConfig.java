package contact;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/contacts").setViewName("contacts");
        registry.addViewController("/login").setViewName("login");

        registry.addViewController("/emails").setViewName("emails");
        registry.addViewController("/addresses").setViewName("addresses");
    }

}