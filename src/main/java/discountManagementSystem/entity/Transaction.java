package discountManagementSystem.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNo;  // Auto
    private Long customerId;     // From C/V db
    private String offerId;  // From C/V db
//    private String couponId;
//    private String voucherId;
    private LocalDate transactionDate; // Auto
//    private OfferType offerType; // TODO: To be added later, maybe
    private Double totalAmount;  // from orderPlacing mechanism
    private Double savedAmount;  // from orderPlacing mechanism, assuming that I want it

    public Transaction() {
    }

    public Transaction(Long customerId, String offerId, LocalDate transactionDate, Double totalAmount, Double savedAmount) {
        this.customerId = customerId;
        this.offerId = offerId;
        this.transactionDate = transactionDate;
        this.totalAmount = totalAmount;
        this.savedAmount = savedAmount;
    }

    public Long getSerialNo() {
        return serialNo;
    }
//    public void setSerialNo(Long serialNo) {
//        this.serialNo = serialNo;
//    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getOfferId() {
        return offerId;
    }
    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Double getSavedAmount() {
        return savedAmount;
    }
    public void setSavedAmount(Double savedAmount) {
        this.savedAmount = savedAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(serialNo, that.serialNo) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(offerId, that.offerId) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(savedAmount, that.savedAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNo, customerId, offerId, transactionDate, totalAmount, savedAmount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "serialNo=" + serialNo +
                ", userId=" + customerId +
                ", offerId='" + offerId + '\'' +
                ", transactionDate=" + transactionDate +
                ", totalAmount=" + totalAmount +
                ", savedAmount=" + savedAmount +
                '}';
    }
}
