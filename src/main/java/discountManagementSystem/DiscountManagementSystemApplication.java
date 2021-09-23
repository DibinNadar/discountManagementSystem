package discountManagementSystem;

import discountManagementSystem._01entity.Coupon;
import discountManagementSystem._01entity.Customer;
import discountManagementSystem._02repository.CouponRepository;
import discountManagementSystem._02repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class DiscountManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountManagementSystemApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner mappingDemo(CustomerRepository customerRepository,
//										 CouponRepository couponRepository) {
//		return args -> {
//
//			// create a Customer
//			Customer customer = new Customer(123L, "Rogan");
//
//			// save the customer
//			customerRepository.save(customer);
//
//			// create three coupons
//			Coupon coupon1 = new Coupon("FiftyFive",20, 4550, 12, LocalDate.of(2019, 7,11) ,LocalDate.now());
//			Coupon coupon2 = new Coupon("555",40, 4550, 12, LocalDate.of(2019, 3,11) ,LocalDate.now());
//			Coupon coupon3 = new Coupon("666Six",10, 4550, 12, LocalDate.of(2019, 9,11) ,LocalDate.now());
//
//
//				// save coupons
//			couponRepository.saveAll(Arrays.asList(coupon1, coupon2, coupon3));
//
//			// add coupons to the customer
//			customer.getCoupons().addAll(Arrays.asList(coupon1, coupon2, coupon3));
//
//			// update the customer
//			customerRepository.save(customer);
//		};
//	}
	

}

