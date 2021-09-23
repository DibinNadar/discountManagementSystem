package discountManagementSystem._01entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table (name = "coupon")
public class Coupon implements Serializable {

    @Id
    private String couponId;
    private double percentageDiscount;  //TODO, make sure precision is maintained
    private Integer upperAmountLimit;
    private Integer globalUsageLimit;  // TODO: Add this limit to the database?
    private LocalDate startDate;
    private LocalDate expiryDate;

    @ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY)
    private Set<Customer> customers = new HashSet<>(); // TODO: are we sure about a hashSet??

    public Coupon() {
    }

    public Coupon(String couponId, double percentageDiscount, Integer upperAmountLimit, Integer globalUsageLimit, LocalDate startDate, LocalDate expiryDate) {
        this.couponId = couponId;
        this.percentageDiscount = percentageDiscount;
        this.upperAmountLimit = upperAmountLimit;
        this.globalUsageLimit = globalUsageLimit;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    public String getCouponId() {
        return couponId;
    }
    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
    public double getPercentageDiscount() {
        return percentageDiscount;
    }
    public void setPercentageDiscount(double percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }
    public Integer getUpperAmountLimit() {
        return upperAmountLimit;
    }
    public void setUpperAmountLimit(Integer upperAmountLimit) {
        this.upperAmountLimit = upperAmountLimit;
    }
    public Integer getGlobalUsageLimit() {
        return globalUsageLimit;
    }
    public void setGlobalUsageLimit(Integer globalUsageLimit) {
        this.globalUsageLimit = globalUsageLimit;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return Double.compare(coupon.percentageDiscount, percentageDiscount) == 0 &&
                couponId.equals(coupon.couponId) &&
                Objects.equals(upperAmountLimit, coupon.upperAmountLimit) &&
                Objects.equals(globalUsageLimit, coupon.globalUsageLimit) &&
                Objects.equals(startDate, coupon.startDate) &&
                Objects.equals(expiryDate, coupon.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId, percentageDiscount, upperAmountLimit, globalUsageLimit, startDate, expiryDate);
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "couponId='" + couponId + '\'' +
                ", percentageDiscount=" + percentageDiscount +
                ", upperAmountLimit=" + upperAmountLimit +
                ", globalUsageLimit=" + globalUsageLimit +
                ", startDate=" + startDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
