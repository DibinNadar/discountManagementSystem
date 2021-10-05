package discountManagementSystem.api.primary;

import discountManagementSystem.assembler.CouponModelAssembler;
import discountManagementSystem.customException.exception.CouponNotFoundException;
import discountManagementSystem.entity.Coupon;
import discountManagementSystem.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    @PostMapping("/coupon")
    ResponseEntity<?> addNewCoupon (@Valid @RequestBody Coupon newCoupon){

        EntityModel<Coupon> entityModel = couponAssembler.toModel(couponRepository.save(newCoupon));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/coupons")
    List<EntityModel<Coupon>> addMultipleNewCoupons(@Valid @RequestBody Coupon[] couponArrayList) {

        List<EntityModel<Coupon>> response = new ArrayList<>();

        for (Coupon coupon : couponArrayList) {
            response.add(couponAssembler.toModel(couponRepository.save(coupon)));
        }
        return response;
    }

    @GetMapping("/coupon/{couponId}")
    public EntityModel<Coupon> findById(@PathVariable String couponId){

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(()->new CouponNotFoundException(couponId));

        return couponAssembler.toModel(coupon);
    }

    @GetMapping("/coupons")
    public CollectionModel<EntityModel<Coupon>> findAll(){

        List<EntityModel<Coupon>> couponList =
                couponRepository
                .findAll()
                .stream()
                .map(couponAssembler::toModel)
                .collect(Collectors.toList());

        if (couponList.isEmpty()){
            throw new CouponNotFoundException(null);
        }

        return CollectionModel.of(couponList,
                linkTo(methodOn(CouponController.class).findAll()).withSelfRel());

    }
    
    @PutMapping("/coupon/{couponId}")
    ResponseEntity<?> updateCoupon(@Valid @RequestBody Coupon newCoupon, @PathVariable String couponId){

        Coupon updatedCoupon = couponRepository.findById(couponId)
                .map(coupon -> couponRepository.save(coupon))
                .orElseGet(()->{
                    newCoupon.setCouponId(couponId);
                    return couponRepository.save(newCoupon);
                });

        EntityModel<Coupon> entityModel = couponAssembler.toModel(updatedCoupon);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @DeleteMapping("/coupon/{couponId}")
    ResponseEntity<?> deleteCoupon(@PathVariable String couponId){
        couponRepository.deleteById(couponId);

        return ResponseEntity.noContent().build();
    }

}
