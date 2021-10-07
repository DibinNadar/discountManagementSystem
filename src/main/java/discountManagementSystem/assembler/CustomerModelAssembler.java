package discountManagementSystem.assembler;

import discountManagementSystem.api.auxillary.OfferController;
import discountManagementSystem.api.primary.CustomerController;
import discountManagementSystem.entity.Customer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

    @Override
    public EntityModel<Customer> toModel(Customer entity) {


        return EntityModel.of(entity,
                linkTo(methodOn(CustomerController.class).findById(entity.getCustomerId())).withSelfRel(),
                linkTo(methodOn(OfferController.class).getCustomerCoupons(entity.getCustomerId())).withRel(entity.getName()+"'s Coupons"),
                linkTo(methodOn(OfferController.class).getCustomerVouchers(entity.getCustomerId())).withRel(entity.getName()+"'s Vouchers"),
                linkTo(methodOn(CustomerController.class).findAll()).withRel("Customer List"));


    }

}
