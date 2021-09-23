package discountManagementSystem._01entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity (name = "customer")
public class Customer implements Serializable {

    @Id
    private Long customerId; // Phone
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
    private Set<Coupon> coupons = new HashSet<>();  // TODO: are we sure about a hashSet??


    public Customer() {
    }

    public Customer(Long customerId, @NotEmpty(message = "Customer Name is mandatory") String name) {
        this.customerId = customerId;
        this.name = name;
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
        return Objects.hash(customerId, name, coupons);
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
