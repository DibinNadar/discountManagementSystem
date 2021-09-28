package discountManagementSystem.assembler;

import discountManagementSystem.api.CustomerController;
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
                linkTo(methodOn(CustomerController.class).getOne(entity.getCustomerId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getCustomerCoupons(entity.getCustomerId())).withRel(entity.getName()+"'s Coupons"),
                linkTo(methodOn(CustomerController.class).getAll()).withRel("Customer List"));

//        return EntityModel.of(entity,
//                linkTo(methodOn(CustomerController.class).getOne(entity.getCustomerId())).withSelfRel(),
//                linkTo(methodOn(CustomerController.class).getAll()).withRel("customers"));
    }

}
