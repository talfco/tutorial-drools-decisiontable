package net.cloudburo.drools.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private CustomerType type;

    private CustomerOccupation occupation = CustomerOccupation.NONE;

    private int years;

    private int discount;
    
    private List<CustomerNeed> customerNeeds = new ArrayList<>();

    public Customer(CustomerType type, int numOfYears) {
        super();
        this.type = type;
        this.years = numOfYears;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public int getYears() {
        return years;
    }

    public List<CustomerNeed> getNeeds() {

        return customerNeeds;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void addNeed(CustomerNeed need) {
        customerNeeds.add(need);
    }

    public enum CustomerType {
        INDIVIDUAL, BUSINESS
    }

    public enum CustomerNeed {
        MONEYABROAD, EBANKING, CASH, SECURITIES
    }

    public enum CustomerOccupation {
        HIGHSCHOOLER,
        APPRENTICE,
        STUDENT,
        YOUNGPROFESSIONAL,
        EMPLOYED,
        UNEMPLOYED,
        RETIRED,
        NONE
    }
}
