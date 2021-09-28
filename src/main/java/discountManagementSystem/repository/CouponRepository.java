package discountManagementSystem.repository;

import discountManagementSystem.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, String> {}
