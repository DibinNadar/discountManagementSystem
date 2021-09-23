package discountManagementSystem._02repository;

import discountManagementSystem._01entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,String> {
}
