//package discountManagementSystem.entity;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.validation.constraints.NotEmpty;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.Objects;
//
//@Entity
//@Table (name = "voucher")
//public class Voucher {
//
////    Flat discount : Fixed amount, used till exhausted or expired
//    @Id
//    private Integer voucherId;
//    @NotEmpty (message = "Voucher Name is mandatory")
//    private String name;
//    private double percentageDiscount;  //TODO, make sure precision is maintained
//    private Integer flatDiscount;
//    private Integer usageNo;
//    private Integer usageAmount;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private Integer assignedTo;
//
//    public Voucher() {
//    }
//
//    // Default value for %=0, usageNo = -1, usageAmount = 0 // TODO: test if usageNo needs to be changed to 0
//    public Voucher(Integer voucherId, String name, Integer flatDiscount, LocalDate startDate, LocalDate endDate, Integer assignedTo) {
//        this.voucherId = voucherId;
//        this.name = name;
//        this.percentageDiscount = 0.00;
//        this.flatDiscount = flatDiscount;
//        this.usageNo = -1;
//        this.usageAmount = 0;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.assignedTo = assignedTo;
//    }
//
//    public Voucher(Integer voucherId, String name, double percentageDiscount, Integer flatDiscount, Integer usageNo,Integer usageAmount, LocalDate startDate, LocalDate endDate, Integer assignedTo) {
//        this.voucherId = voucherId;
//        this.name = name;
//        this.percentageDiscount = percentageDiscount;
//        this.flatDiscount = flatDiscount;
//        this.usageNo = usageNo;
//        this.usageAmount = usageAmount;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.assignedTo = assignedTo;
//    }
//
//    public Integer getVoucherId() {
//        return voucherId;
//    }
//    public void setVoucherId(Integer voucherId) {
//        this.voucherId = voucherId;
//    }
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public double getPercentageDiscount() {
//        return percentageDiscount;
//    }
//    public void setPercentageDiscount(double percentageDiscount) {
//        this.percentageDiscount = percentageDiscount;
//    }
//    public Integer getFlatDiscount() {
//        return flatDiscount;
//    }
//    public void setFlatDiscount(Integer flatDiscount) {
//        this.flatDiscount = flatDiscount;
//    }
//    public Integer getUsageNo() {
//        return usageNo;
//    }
//    public void setUsageNo(Integer usageNo) {
//        this.usageNo = usageNo;
//    }
//    public Integer getUsageAmount() {
//        return usageAmount;
//    }
//    public void setUsageAmount(Integer usageAmount) {
//        this.usageAmount = usageAmount;
//
//    }
//    public LocalDate getStartDate() {
//        return startDate;
//    }
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//    public LocalDate getEndDate() {
//        return endDate;
//    }
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }
//    public Integer getAssignedTo() {
//        return assignedTo;
//    }
//    public void setAssignedTo(Integer assignedTo) {
//        this.assignedTo = assignedTo;
//    }
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (!(o instanceof Voucher)) return false;
//
//        Voucher voucher = (Voucher) o;
//
//        return Objects.equals(voucherId, voucher.voucherId) && Objects.equals(name, voucher.name)
//                && Objects.equals(percentageDiscount, voucher.percentageDiscount)
//                && Objects.equals(flatDiscount, voucher.flatDiscount) && Objects.equals(usageNo, voucher.usageNo)
//                && Objects.equals(usageAmount, voucher.usageAmount) && Objects.equals(startDate, voucher.startDate)
//                && Objects.equals(endDate, voucher.endDate) && Objects.equals(assignedTo, voucher.assignedTo) ;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(this.voucherId, this.name, this.percentageDiscount,this.flatDiscount,this.usageNo
//                ,this.usageAmount,this.startDate,this.endDate, this.assignedTo);
//    }
//
//    @Override
//    public String toString() {
//        return "Voucher {" + "Id=" + this.voucherId + ", Name='" + this.name + '\'' + ", Percentage Discount ='" +
//                this.percentageDiscount +
//                '\'' + ", Flat Discount='" + this.flatDiscount + '\'' +
//                '\'' + ", Usage No='" + this.usageNo + '\'' +
//                '\'' + ", Usage Amount='" + this.usageAmount + '\'' +
//                '\'' + ", Start Date='" + this.startDate + '\'' +
//                '\'' + ", End Date='" + this.endDate + '\'' +
//                '\'' + ", Assigned To='" + this.assignedTo + '\'' +
//                '}';
//    }
//
//}
