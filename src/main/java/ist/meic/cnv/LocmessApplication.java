package ist.meic.cnv;

import ist.meic.cnv.config.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class LocmessApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocmessApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean tokenFilter(){
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new TokenFilter());
		frb.addUrlPatterns("/user/logout");
		frb.addUrlPatterns("/user/listpairs");
		frb.addUrlPatterns("/user/addpair");
		frb.addUrlPatterns("/user/removepair");
		return frb;
	}
}
