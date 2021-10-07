package discountManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "customers_vouchers")
public class CustomerVoucher {

    @Id
    @Column(name = "serialNo", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serialNo;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "voucherId")
    private Voucher voucher;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "remainingAmount")
    private Double remainingAmount;

    public CustomerVoucher() {
    }

    public CustomerVoucher(Customer customer, Voucher voucher, LocalDate startDate, LocalDate endDate, Double remainingAmount) {
        this.customer = customer;
        this.voucher = voucher;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingAmount = remainingAmount;
    }


    public long getSerialNo() {
        return serialNo;
    }
//    public void setSerialNo(long serialNo) {
//        this.serialNo = serialNo;
//    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Voucher getVoucher() {
        return voucher;
    }
    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Double getRemainingAmount() {
        return remainingAmount;
    }
    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerVoucher that = (CustomerVoucher) o;
        return serialNo == that.serialNo &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(voucher, that.voucher) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(remainingAmount, that.remainingAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNo, startDate, endDate, remainingAmount);
    }

    @Override
    public String toString() {
        return "CustomerVoucher{" +
                "serialNo=" + serialNo +
                ", customer=" + customer +
                ", voucher=" + voucher +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", remainingAmount=" + remainingAmount +
                '}';
    }
}
