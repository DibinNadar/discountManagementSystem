package discountManagementSystem.assembler;

import discountManagementSystem._01entity.Coupon;
import discountManagementSystem._04api.CouponController;
import discountManagementSystem._04api.CustomerController;
import discountManagementSystem._01entity.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

    @Override
    public EntityModel<Customer> toModel(Customer entity) {


        return EntityModel.of(entity,
                linkTo(methodOn(CustomerController.class).getOne(entity.getCustomerId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getCustomerCoupons(entity.getCustomerId())).withRel(entity.getName()+"'s Coupons"),
                linkTo(methodOn(CustomerController.class).getAll()).withRel("Customer List"));

//        return EntityModel.of(entity,
//                linkTo(methodOn(CustomerController.class).getOne(entity.getCustomerId())).withSelfRel(),
//                linkTo(methodOn(CustomerController.class).getAll()).withRel("customers"));
    }

}
