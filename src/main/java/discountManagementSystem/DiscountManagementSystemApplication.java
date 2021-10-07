package discountManagementSystem;

import discountManagementSystem.entity.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@SpringBootApplication
public class DiscountManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountManagementSystemApplication.class, args);
	}

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer()
	{
		return RepositoryRestConfigurer.withConfig(config -> {
			config.exposeIdsFor(Coupon.class, Voucher.class, CustomerVoucher.class, Customer.class, Transaction.class);
		});
	}


}

