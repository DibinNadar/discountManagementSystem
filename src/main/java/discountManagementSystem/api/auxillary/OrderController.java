package discountManagementSystem.api.auxillary;

import discountManagementSystem.api.primary.CouponController;
import discountManagementSystem.api.primary.CustomerController;
import discountManagementSystem.api.primary.TransactionController;
import discountManagementSystem.api.primary.VoucherController;
import discountManagementSystem.customException.exception.*;
import discountManagementSystem.entity.Coupon;
import discountManagementSystem.entity.CustomerVoucher;
import discountManagementSystem.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class OrderController {

    @Autowired
    private CouponController couponController;

    @Autowired
    private VoucherController voucherController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private OfferController offerController;


    @RequestMapping("/order/{customerId}/{offerId}/{totalAmount}")
    public ResponseEntity<?> placeOrder(@PathVariable Long customerId, @PathVariable String offerId, @PathVariable Double totalAmount){

        if (totalAmount<0){
            throw new IllegalTotalAmountException(totalAmount);
        }

        Coupon coupon = null;
//        Voucher voucher = null;
        CustomerVoucher customerVoucher = null;
        Long voucherSerial = null;
        Boolean voucherFlag = false;

        try {
            voucherSerial =Long.parseLong(offerId);
            voucherFlag = true;
        }catch (NumberFormatException ignored){
        }

        Double savedAmount;

        try {
            coupon = couponController.findById(offerId).getContent();
        }catch (CouponNotFoundException ignored){}

        if (voucherFlag){
        try {
            customerVoucher = offerController.getOneCustomerVoucher(customerId,voucherSerial).getContent();
        }catch (VoucherNotFoundException | IllegalArgumentException ignored){}
        }

        if (coupon!=null){
            offerController.getOneCustomerCoupon(customerId,offerId); // Coupon validation
            int globalUsageLimit = coupon.getGlobalUsageLimit();
            if (globalUsageLimit<=0){
                System.out.println("Sorry Global usage count has exceeded for offer :" + offerId);
                throw new GlobalUsageExceededException(offerId);
            }
            coupon.setGlobalUsageLimit(globalUsageLimit-1);
            savedAmount = totalAmount * (coupon.getPercentageDiscount()/100.00);
        }
        else if (customerVoucher!=null){
        offerController.getOneCustomerVoucher(customerId,voucherSerial); // Voucher validation
            Double remainingVoucherAmount = customerVoucher.getRemainingAmount();
            if (totalAmount>=remainingVoucherAmount){
                savedAmount = remainingVoucherAmount;
                customerVoucher.setRemainingAmount(0.0);
            }else {
                savedAmount = totalAmount;
                customerVoucher.setRemainingAmount(remainingVoucherAmount-totalAmount);
            }
        }
        else {
            throw new OfferNotFoundException(offerId);
        }

        Transaction transaction = new Transaction(customerId,offerId,LocalDate.now(),totalAmount, savedAmount);

        return transactionController.addNewTransaction(transaction);
    }
}