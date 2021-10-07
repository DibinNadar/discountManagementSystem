package discountManagementSystem.repository;

import discountManagementSystem.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false) // to disable default links TODO Learn the default links available
public interface CouponRepository extends JpaRepository<Coupon, String> {}
