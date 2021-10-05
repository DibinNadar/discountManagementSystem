package discountManagementSystem.assembler;

import discountManagementSystem.entity.Transaction;
import discountManagementSystem.api.primary.TransactionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {
    @Override
    public EntityModel<Transaction> toModel(Transaction entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(TransactionController.class).findById(entity.getSerialNo())).withSelfRel(),
                linkTo(methodOn(TransactionController.class).findAll()).withRel("transactions"));
    }
}
