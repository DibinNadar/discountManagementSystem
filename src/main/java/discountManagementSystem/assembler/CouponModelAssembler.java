package discountManagementSystem.assembler;

import discountManagementSystem.api.CouponController;
import discountManagementSystem.entity.Coupon;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CouponModelAssembler implements RepresentationModelAssembler<Coupon, EntityModel<Coupon>> {
    @Override
    public EntityModel<Coupon> toModel(Coupon entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CouponController.class).getOne(entity.getCouponId())).withSelfRel(),
                linkTo(methodOn(CouponController.class).getAll()).withRel("coupons"));
    }
}
