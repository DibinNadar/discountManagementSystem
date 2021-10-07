package discountManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
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


    @JsonIgnore
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    public Set<CustomerVoucher> customers_vouchers = new HashSet<>();


    public Customer() {
    }

    public Customer(Long customerId, @NotEmpty(message = "Customer Name is mandatory") String name, Set<Coupon> coupons, Set<CustomerVoucher> customers_vouchers) {
        this.customerId = customerId;
        this.name = name;
        this.coupons = coupons;
        this.customers_vouchers = customers_vouchers;
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
    public Set<Coupon> getCoupons() {
        return coupons;
    }
    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    public Set<CustomerVoucher> getCustomers_vouchers() {
        return customers_vouchers;
    }
    public void setCustomers_vouchers(Set<CustomerVoucher> customers_vouchers) {
        this.customers_vouchers = customers_vouchers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(customers_vouchers, customer.customers_vouchers) &&
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
                ", vouchers=" + customers_vouchers +
                '}';
    }
}
