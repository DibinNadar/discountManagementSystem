package discountManagementSystem.assembler;

import discountManagementSystem._04api.CustomerController;
import discountManagementSystem._01entity.Customer;
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
                linkTo(methodOn(CustomerController.class).getAll()).withRel("customers"));
    }
}
