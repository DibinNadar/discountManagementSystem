package discountManagementSystem._01entity;

import java.time.LocalDate;

public class Transaction {

    private Long serialNo;

    private String couponId;
    private String voucherId;
    private Long userId;

    private LocalDate transactionDate;

//    private OfferType offerType; // TODO: To be added later, maybe

    private Double totalAmount;
    private Double savedAmount;

}
