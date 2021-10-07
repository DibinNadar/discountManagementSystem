package discountManagementSystem.api.primary;

import discountManagementSystem.assembler.VoucherModelAssembler;
import discountManagementSystem.customException.exception.VoucherNotFoundException;
import discountManagementSystem.entity.Voucher;
import discountManagementSystem.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class VoucherController {

    @Autowired
    private VoucherRepository voucherRepository;

    private VoucherModelAssembler voucherAssembler;

    public VoucherController(VoucherRepository voucherRepository, VoucherModelAssembler voucherAssembler) {
        this.voucherRepository = voucherRepository;
        this.voucherAssembler = voucherAssembler;
    }
    

    @PostMapping("/voucher")
    ResponseEntity<?> addNewVoucher (@Valid @RequestBody Voucher newVoucher){

        EntityModel<Voucher> entityModel = voucherAssembler.toModel(voucherRepository.save(newVoucher));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/vouchers")
    List<EntityModel<Voucher>> addMultipleNewVouchers(@Valid @RequestBody Voucher[] voucherArrayList) {

        List<EntityModel<Voucher>> response = new ArrayList<>();

        for (Voucher voucher : voucherArrayList) {
            response.add(voucherAssembler.toModel(voucherRepository.save(voucher)));
        }
        return response;
    }

    @GetMapping("/voucher/{voucherId}")
    public EntityModel<Voucher> findById(@PathVariable String voucherId){

        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(()->new VoucherNotFoundException(voucherId));

        return voucherAssembler.toModel(voucher);
    }

    @GetMapping("/vouchers")
    public CollectionModel<EntityModel<Voucher>> findAll(){

        List<EntityModel<Voucher>> voucherList =
                voucherRepository
                        .findAll()
                        .stream()
                        .map(voucherAssembler::toModel)
                        .collect(Collectors.toList());

        if (voucherList.isEmpty()){
            throw new VoucherNotFoundException(null);
        }

        return CollectionModel.of(voucherList,
                linkTo(methodOn(VoucherController.class).findAll()).withSelfRel());

    }

    @PutMapping("/voucher/{voucherId}")
    ResponseEntity<?> updateVoucher(@Valid @RequestBody Voucher newVoucher, @PathVariable String voucherId){

        Voucher updatedVoucher = voucherRepository.findById(voucherId)
                .map(voucher -> voucherRepository.save(newVoucher))
                .orElseGet(()->{
                    newVoucher.setVoucherId(voucherId);
                    return voucherRepository.save(newVoucher);
                });

        EntityModel<Voucher> entityModel = voucherAssembler.toModel(updatedVoucher);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/voucher/{voucherId}")
    ResponseEntity<?> deleteVoucher(@PathVariable String voucherId){

        if (voucherRepository.existsById(voucherId)){
            voucherRepository.deleteById(voucherId);
        }else {
            throw new VoucherNotFoundException(voucherId);
        }

        return ResponseEntity.noContent().build();
    }

}
