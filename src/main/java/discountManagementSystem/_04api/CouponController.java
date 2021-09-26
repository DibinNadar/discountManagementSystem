package discountManagementSystem._04api;

import discountManagementSystem._01entity.Customer;
import discountManagementSystem.assembler.CouponModelAssembler;
import discountManagementSystem._02repository.CouponRepository;
import discountManagementSystem._01entity.Coupon;
import discountManagementSystem.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    private CouponModelAssembler couponAssembler;

    public CouponController(CouponRepository couponRepository, CouponModelAssembler couponAssembler) {
        this.couponRepository = couponRepository;
        this.couponAssembler = couponAssembler;
    }

    @RequestMapping("/coupon_home")
    public String getHome(){
        return "This is the Coupon Home Page";
    }

//    @PostMapping("/coupons") // TODO setMany()

    @GetMapping("/coupons")
    public CollectionModel<EntityModel<Coupon>> getAll(){

        List<EntityModel<Coupon>> couponList =
                couponRepository
                .findAll()
                .stream()
                .map(couponAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(couponList,
                linkTo(methodOn(CouponController.class).getAll()).withSelfRel());

    }


    @GetMapping("/coupons/{couponId}")
    public EntityModel<Coupon> getOne(@PathVariable String couponId){

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(()->new CouponNotFoundException(couponId));

        return couponAssembler.toModel(coupon);
    }

    @PostMapping("/coupons")
    ResponseEntity<?> addNewCoupon (@Valid @RequestBody Coupon newCoupon){

        EntityModel<Coupon> entityModel = couponAssembler.toModel(couponRepository.save(newCoupon));

        return ResponseEntity.
                created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/coupons/{couponId}")
    ResponseEntity<?> updateCoupon(@Valid @RequestBody Coupon newCoupon, @PathVariable String couponId){

        Coupon updatedCoupon = couponRepository.findById(couponId)
                .map(coupon -> {
                    coupon.setCouponId(newCoupon.getCouponId());
                    coupon.setPercentageDiscount(newCoupon.getPercentageDiscount());
                    coupon.setUpperAmountLimit(newCoupon.getUpperAmountLimit());
                    coupon.setGlobalUsageLimit(newCoupon.getGlobalUsageLimit());
                    coupon.setStartDate(newCoupon.getStartDate());
                    coupon.setExpiryDate(newCoupon.getExpiryDate());
                    coupon.setCustomers(newCoupon.getCustomers());
                    return couponRepository.save(newCoupon);
                })
                .orElseGet(()->{
                    newCoupon.setCouponId(couponId);
                    return couponRepository.save(newCoupon);
                });

        EntityModel<Coupon> entityModel = couponAssembler.toModel(updatedCoupon);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @DeleteMapping("/coupons/{couponId}")
    ResponseEntity<?> deleteCoupon(@PathVariable String id){
        couponRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }



}
