package discountManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table (name = "voucher")
public class Voucher {
    @Id
    private String voucherId;
    private Double flatDiscount;

    @JsonIgnore
    @OneToMany(mappedBy = "voucher",fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    public Set<CustomerVoucher> customers_vouchers = new HashSet<>();

    public Voucher() {
    }

    public Voucher(String voucherId, Double flatDiscount, Set<CustomerVoucher> customers_vouchers) {
        this.voucherId = voucherId;
        this.flatDiscount = flatDiscount;
        this.customers_vouchers = customers_vouchers;
    }

    public String getVoucherId() {
        return voucherId;
    }
    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }
    public Double getFlatDiscount() {
        return flatDiscount;
    }
    public void setFlatDiscount(Double flatDiscount) {
        this.flatDiscount = flatDiscount;
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
        Voucher voucher = (Voucher) o;
        return Objects.equals(voucherId, voucher.voucherId) &&
                Objects.equals(flatDiscount, voucher.flatDiscount) &&
                Objects.equals(customers_vouchers, voucher.customers_vouchers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, flatDiscount, customers_vouchers);
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId='" + voucherId + '\'' +
                ", flatDiscount=" + flatDiscount +
                ", customers=" + customers_vouchers +
                '}';
    }
}
