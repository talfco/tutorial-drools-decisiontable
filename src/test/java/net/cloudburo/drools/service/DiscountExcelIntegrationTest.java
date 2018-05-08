package net.cloudburo.drools.service;

import static org.junit.Assert.assertEquals;

import net.cloudburo.drools.model.Offer;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import net.cloudburo.drools.config.DroolsBeanFactory;
import net.cloudburo.drools.model.Customer;
import net.cloudburo.drools.model.Customer.CustomerType;

public class DiscountExcelIntegrationTest {

    private KieSession kSession;

    @Before
    public void setup() {
        Resource resource = ResourceFactory.newClassPathResource("net/cloudburo/drools/rules/DroolsDiscount.xlsx", getClass());
        kSession = new DroolsBeanFactory().getKieSession(resource);
        System.out.println(new DroolsBeanFactory().getDrlFromExcel("net/cloudburo/drools/rules/DroolsDiscount.xlsx"));
    }

    @Test
    public void giveIndvidualLongStanding_whenFireRule_thenCorrectDiscount() throws Exception {
        Customer customer = new Customer(CustomerType.INDIVIDUAL, 5);
        customer.addNeed(Customer.CustomerNeed.EBANKING);
        customer.addNeed(Customer.CustomerNeed.CASH);
        kSession.insert(customer);

        Offer offer = new Offer();
        kSession.insert(offer);

        kSession.fireAllRules();

        assertEquals(customer.getDiscount(), 15);
    }

    @Test
    public void giveIndvidualRecent_whenFireRule_thenCorrectDiscount() throws Exception {

        Customer customer = new Customer(CustomerType.INDIVIDUAL, 1);
        kSession.insert(customer);

        kSession.fireAllRules();

        assertEquals(customer.getDiscount(), 5);
    }

    @Test
    public void giveBusinessAny_whenFireRule_thenCorrectDiscount() throws Exception {
        Customer customer = new Customer(CustomerType.BUSINESS, 0);
        kSession.insert(customer);

        kSession.fireAllRules();

        assertEquals(customer.getDiscount(), 20);
    }

}
