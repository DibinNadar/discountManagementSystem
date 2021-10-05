//package discountManagementSystem.voucher;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import discountManagementSystem.entity.Customer;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Set;
//
//@Entity
//@Table (name = "voucher")
//public class Voucher {
//    // TODO:  vocher custoer extra fields
//    @Id
//    private String voucherId;
//    private Integer flatDiscount;
//
//    @JsonIgnore
//    @ManyToMany(mappedBy = "vouchers", fetch = FetchType.LAZY)
//    private Set<Customer> customers = new HashSet<>();
//
///**
//    customers_vouchers
//    Start/End
//            amountRemainig
// **/
//    public Voucher() {
//    }
//
//    public Voucher(String voucherId, Integer flatDiscount, Set<Customer> customers) {
//        this.voucherId = voucherId;
//        this.flatDiscount = flatDiscount;
//        this.customers = customers;
//    }
//
//    public String getVoucherId() {
//        return voucherId;
//    }
//    public void setVoucherId(String voucherId) {
//        this.voucherId = voucherId;
//    }
//    public Integer getFlatDiscount() {
//        return flatDiscount;
//    }
//    public void setFlatDiscount(Integer flatDiscount) {
//        this.flatDiscount = flatDiscount;
//    }
//    public Set<Customer> getCustomers() {
//        return customers;
//    }
//    public void setCustomers(Set<Customer> customers) {
//        this.customers = customers;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Voucher voucher = (Voucher) o;
//        return Objects.equals(voucherId, voucher.voucherId) &&
//                Objects.equals(flatDiscount, voucher.flatDiscount) &&
//                Objects.equals(customers, voucher.customers);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(voucherId, flatDiscount, customers);
//    }
//
//    @Override
//    public String toString() {
//        return "Voucher{" +
//                "voucherId='" + voucherId + '\'' +
//                ", flatDiscount=" + flatDiscount +
//                ", customers=" + customers +
//                '}';
//    }
//}
