package discountManagementSystem._02repository;

import discountManagementSystem._01entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
