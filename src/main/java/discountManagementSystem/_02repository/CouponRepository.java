package discountManagementSystem._02repository;

import discountManagementSystem._01entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface CouponRepository extends JpaRepository<Coupon, String> {}
