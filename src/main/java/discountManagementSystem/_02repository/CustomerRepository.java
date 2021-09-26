package discountManagementSystem._02repository;

import discountManagementSystem._01entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query(value = "SELECT * FROM customer",
            nativeQuery = true)
    public List<Customer> test();

    @Query(value = "SELECT * FROM customers_coupons",
            nativeQuery = true)
    public List<Customer> test2(); // create customers_coupons entity? Surely Not
}
