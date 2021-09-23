//package discountManagementSystem.assembler;
//
//import discountManagementSystem._04api.VoucherController;
//import discountManagementSystem._01entity.Voucher;
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
//    public EntityModel<Voucher> toModel(Voucher _01entity) {
//        return EntityModel.of(_01entity,
//                linkTo(methodOn(VoucherController.class).getOne(_01entity.getVoucherId())).withSelfRel(),
//                linkTo(methodOn(VoucherController.class).getAll()).withRel("vouchers"));
//    }
//}
