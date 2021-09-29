//package discountManagementSystem.voucher;
//
//import discountManagementSystem.assembler.VoucherModelAssembler;
//import discountManagementSystem.customException.exception.VoucherNotFoundException;
//import discountManagementSystem.voucher.VoucherRepository;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.IanaLinkRelations;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@RestController
//public class VoucherController {
//
//    @Autowired
//    private VoucherRepository voucherRepository;
//
//    private VoucherModelAssembler voucherAssembler;
//
//    public VoucherController(VoucherRepository voucherRepository, VoucherModelAssembler voucherAssembler) {
//        this.voucherRepository = voucherRepository;
//        this.voucherAssembler = voucherAssembler;
//    }
//
//    @RequestMapping("/voucher_home")
//    public String getHome(){
//        return "This is the Voucher Home Page";
//    }
//
////    @PostMapping("/vouchers") // TODO setMany()
//
//    @GetMapping("/vouchers")
//    public CollectionModel<EntityModel<Voucher>> getAll(){
//
//        List<EntityModel<Voucher>> voucherList =
//                voucherRepository
//                        .findAll()
//                        .stream()
//                        .map(voucherAssembler::toModel)
//                        .collect(Collectors.toList());
//
//        return CollectionModel.of(voucherList,
//                linkTo(methodOn(VoucherController.class).getAll()).withSelfRel());
//
//    }
//
//    @GetMapping("/vouchers/{voucherId}")
//    public EntityModel<Voucher> getOne(@PathVariable String voucherId){
//
//        Voucher voucher = voucherRepository.findById(voucherId)
//                .orElseThrow(()->new VoucherNotFoundException(voucherId));
//
//        return voucherAssembler.toModel(voucher);
//    }
//
//    @PostMapping("/vouchers")
//    ResponseEntity<?> addNewVoucher (@Valid @RequestBody Voucher newVoucher){
//
//        EntityModel<Voucher> entityModel = voucherAssembler.toModel(voucherRepository.save(newVoucher));
//
//        return ResponseEntity
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
//                .body(entityModel);
//    }
//
//    @PutMapping("/vouchers/{voucherId}")
//    ResponseEntity<?> updateVoucher(@Valid @RequestBody Voucher newVoucher, @PathVariable String voucherId){
//
//        Voucher updatedVoucher = voucherRepository.findById(voucherId)
//                .map(voucher -> {
//                    BeanUtils.copyProperties(newVoucher,voucher);
//                    return voucherRepository.save(newVoucher);
//                })
//                .orElseGet(()->{
//                    newVoucher.setVoucherId(voucherId);
//                    return voucherRepository.save(newVoucher);
//                });
//
//        EntityModel<Voucher> entityModel = voucherAssembler.toModel(updatedVoucher);
//
//        return ResponseEntity
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
//                .body(entityModel);
//
//    }
//
//    @DeleteMapping("/vouchers/{voucherId}")
//    ResponseEntity<?> deleteVoucher(@PathVariable String id){
//        voucherRepository.deleteById(id);
//
//        return ResponseEntity.noContent().build();
//    }
//
//}
