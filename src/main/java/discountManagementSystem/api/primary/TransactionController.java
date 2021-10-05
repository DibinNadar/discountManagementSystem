package discountManagementSystem.api.primary;

import discountManagementSystem.assembler.TransactionModelAssembler;
import discountManagementSystem.customException.exception.TransactionNotFoundException;
import discountManagementSystem.entity.Transaction;
import discountManagementSystem.repository.TransactionRepository;
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
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    private TransactionModelAssembler transactionAssembler;

    public TransactionController(TransactionRepository transactionRepository, TransactionModelAssembler transactionAssembler) {
        this.transactionRepository = transactionRepository;
        this.transactionAssembler = transactionAssembler;
    }

    @RequestMapping("/transaction/home")
    public String getHome(){
        return "This is the Transactions Home Page";
    }

    @PostMapping("/transaction") // @Valid?
    public ResponseEntity<?> addNewTransaction (@Valid @RequestBody Transaction newTransaction){

        EntityModel<Transaction> entityModel = transactionAssembler.toModel(transactionRepository.save(newTransaction));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/transactions")
    List<EntityModel<Transaction>> addMultipleNewTransactions(@Valid @RequestBody Transaction[] transactionArrayList) {

        List<EntityModel<Transaction>> response = new ArrayList<>();

        for (Transaction transaction : transactionArrayList) {
            response.add(transactionAssembler.toModel(transactionRepository.save(transaction)));
        }
        return response;
    }

    @GetMapping("/transaction/{serialNo}")
    public EntityModel<Transaction> findById(@PathVariable Long serialNo){

        Transaction transaction = transactionRepository.findById(serialNo)
                .orElseThrow(()->new TransactionNotFoundException(serialNo));

        return transactionAssembler.toModel(transaction);
    }

    @GetMapping("/transactions")
    public CollectionModel<EntityModel<Transaction>> findAll(){

        List<EntityModel<Transaction>> transactionList =
                transactionRepository
                .findAll()
                .stream()
                .map(transactionAssembler::toModel)
                .collect(Collectors.toList());

        if (transactionList.isEmpty()){
            throw new TransactionNotFoundException(null);
        }

        return CollectionModel.of(transactionList,
                linkTo(methodOn(TransactionController.class).findAll()).withSelfRel());
    }


    /***************************************************************************************************/
//    @PutMapping("/transactions/{serialNo}") Updating Not Allowed!
//    ResponseEntity<?> updateTransaction(@Valid @RequestBody Transaction newTransaction, @PathVariable Long serialNo){
//
//        Transaction updatedTransaction = transactionRepository.findById(serialNo)
//                .map(transaction -> {
//                    BeanUtils.copyProperties(newTransaction,transaction);
//                    return transactionRepository.save(newTransaction);
//                })
//                .orElseGet(()->{
//                    newTransaction.setSerialNo(serialNo);
//                    return transactionRepository.save(newTransaction);
//                });
//
//        EntityModel<Transaction> entityModel = transactionAssembler.toModel(updatedTransaction);
//
//        return ResponseEntity
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
//                .body(entityModel);
//
//    }

//    @DeleteMapping("/transactions/{serialNo}") //  Not Allowed
//    ResponseEntity<?> deleteCoupon(@PathVariable Long serialNo){
//        transactionRepository.deleteById(serialNo);
//        return ResponseEntity.noContent().build();
//    }
    /***************************************************************************************************/

}
