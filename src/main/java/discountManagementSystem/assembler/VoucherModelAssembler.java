package discountManagementSystem.assembler;

import discountManagementSystem.entity.Voucher;
import discountManagementSystem.api.primary.VoucherController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VoucherModelAssembler implements RepresentationModelAssembler<Voucher, EntityModel<Voucher>> {

    @Override
    public EntityModel<Voucher> toModel(Voucher entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(VoucherController.class).findById(entity.getVoucherId())).withSelfRel(),
                linkTo(methodOn(VoucherController.class).findAll()).withRel("vouchers"));
    }
}
