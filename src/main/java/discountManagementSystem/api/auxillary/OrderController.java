package discountManagementSystem.api.auxillary;

import discountManagementSystem.api.primary.CouponController;
import discountManagementSystem.api.primary.CustomerController;
import discountManagementSystem.api.primary.TransactionController;
import discountManagementSystem.customException.exception.CouponNotFoundException;
import discountManagementSystem.customException.exception.GlobalUsageExceededException;
import discountManagementSystem.customException.exception.OfferNotFoundException;
import discountManagementSystem.entity.Coupon;
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

//    TODO
//    @Autowired
//    private VoucherController voucherController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private OfferController offerController;


    @RequestMapping("/order/{customerId}/{offerId}/{totalAmount}")
    public ResponseEntity<?> placeOrder(@PathVariable Long customerId, @PathVariable String offerId, @PathVariable Double totalAmount){

        Coupon coupon = null;
//        Voucher voucher = null;

        Double savedAmount;

        try {
            coupon = couponController.findById(offerId).getContent();
        }catch (CouponNotFoundException ignored){}
//        try {
//            voucher = voucherController.findById(offerId).getContent();
//        }catch (VoucherNotFoundException ignored){}

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
//        else if (voucher!=null){
//        offerController.getOneCustomerVoucher(customerId,offerId); // Voucher validation
//            System.out.println("Voucher1111111");
//        }
        else {
            System.out.println("INVALIDDDDDO");
            throw new OfferNotFoundException(offerId);
        }

        Transaction transaction = new Transaction(customerId,offerId,LocalDate.now(),totalAmount, savedAmount);

        return transactionController.addNewTransaction(transaction);
    }
}