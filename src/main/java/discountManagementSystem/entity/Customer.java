package discountManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

@Entity (name = "customer")
public class Customer implements Serializable {

    @Id
    private Long customerId; // Phone
    @NotEmpty (message = "Customer Name is mandatory")
    private String name;

    @JsonIgnore  // TODO: CHECK
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


    public Customer() {
    }
// TODO: copy Const
//    public Customer(Customer source) {
//        customerId = source.customerId;
//        name = source.name;
//        coupons = source.coupons;
////        for (Coupon sourceCoupon : source.coupons) {
////            coupons.add(new Coupon(sourceCoupon));
////        }
//    }

    public Customer(Long customerId, @NotEmpty(message = "Customer Name is mandatory") String name, Set<Coupon> coupons) {
        this.customerId = customerId;
        this.name = name;
        this.coupons = coupons;
    }

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
    public Collection<Coupon> getCoupons() {
        return coupons;
    }
    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(coupons, customer.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
