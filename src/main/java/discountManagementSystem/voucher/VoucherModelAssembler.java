//package discountManagementSystem.assembler;
//
//import discountManagementSystem.voucher.Voucher;
//import discountManagementSystem.voucher.VoucherController;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.RepresentationModelAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Component
//public class VoucherModelAssembler implements RepresentationModelAssembler<Voucher, EntityModel<Voucher>> {
//
//    @Override
//    public EntityModel<Voucher> toModel(Voucher entity) {
//        return EntityModel.of(entity,
//                linkTo(methodOn(VoucherController.class).getOne(entity.getVoucherId())).withSelfRel(),
//                linkTo(methodOn(VoucherController.class).getAll()).withRel("vouchers"));
//    }
//}
