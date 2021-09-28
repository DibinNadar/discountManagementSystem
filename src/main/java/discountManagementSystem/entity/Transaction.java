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
    private Long userId;     // From C/V db
    private String offerId;  // From C/V db
//    private String couponId;
//    private String voucherId;
    private LocalDate transactionDate; // Auto
//    private OfferType offerType; // TODO: To be added later, maybe
    private Double totalAmount;  // from orderPlacing mechanism
    private Double savedAmount;  // from orderPlacing mechanism, assuming that I want it

    public Transaction() {
    }

    public Transaction(Long serialNo, Long userId, String offerId, LocalDate transactionDate, Double totalAmount, Double savedAmount) {
        this.serialNo = serialNo;
        this.userId = userId;
        this.offerId = offerId;
        this.transactionDate = transactionDate; //TODO: make it Real-Time?
        this.totalAmount = totalAmount;
        this.savedAmount = savedAmount;
    }

    public Long getSerialNo() {
        return serialNo;
    }
    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
                Objects.equals(userId, that.userId) &&
                Objects.equals(offerId, that.offerId) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(savedAmount, that.savedAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNo, userId, offerId, transactionDate, totalAmount, savedAmount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "serialNo=" + serialNo +
                ", userId=" + userId +
                ", offerId='" + offerId + '\'' +
                ", transactionDate=" + transactionDate +
                ", totalAmount=" + totalAmount +
                ", savedAmount=" + savedAmount +
                '}';
    }
}
