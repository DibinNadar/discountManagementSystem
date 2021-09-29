package discountManagementSystem.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity (name = "customer")
public class Customer implements Serializable {

    @Id
    private Long customerId;
    @NotEmpty (message = "Customer Name is mandatory")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "customers_coupons",
            joinColumns = {
//                                     name of Col                          taken from Col
                    @JoinColumn(name = "customerId", referencedColumnName = "customerId",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "couponId", referencedColumnName = "couponId",
                            nullable = false, updatable = false)})
    public Set<Coupon> coupons = new HashSet<>();

//    @JsonIgnore  // TODO: CHECK
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinTable(name = "customers_vouchers",
//            joinColumns = {
////                                     name of Col                          taken from Col
//                    @JoinColumn(name = "customerId", referencedColumnName = "customerId",
//                            nullable = false, updatable = false)},
//            inverseJoinColumns = {
//                    @JoinColumn(name = "voucherId", referencedColumnName = "voucherId",
//                            nullable = false, updatable = false)})
//    public Set<Voucher> vouchers = new HashSet<>();
    public Customer() {
    }

    public Customer(Long customerId, @NotEmpty(message = "Customer Name is mandatory") String name, Set<Coupon> coupons) {
        this.customerId = customerId;
        this.name = name;
        this.coupons = coupons;
    }

//    public Customer(Long customerId, @NotEmpty(message = "Customer Name is mandatory") String name, Set<Coupon> coupons, Set<Voucher> vouchers) {
//        this.customerId = customerId;
//        this.name = name;
//        this.coupons = coupons;
//        this.vouchers = vouchers;
//    }

    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<Coupon> getCoupons() {
        return coupons;
    }
    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

//    public Set<Voucher> getVouchers() {
//        return vouchers;
//    }
//    public void setVouchers(Set<Voucher> vouchers) {
//        this.vouchers = vouchers;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) &&
                Objects.equals(name, customer.name) &&
//                Objects.equals(vouchers, customer.vouchers) &&
                Objects.equals(coupons, customer.coupons);
    }

    @Override  // TODO: DO NOT HASH COUPONS!! Why does it break?
    public int hashCode() {
        return Objects.hash(customerId, name);
//        return Objects.hash(customerId, name, coupons, vouchers);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", coupons=" + coupons +
//                ", vouchers=" + vouchers +
                '}';
    }
}
