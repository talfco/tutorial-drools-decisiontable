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
        // Add a Customer with its personal data and needs, used for the LHS Decision
        Customer customer = new Customer();
        customer.setLifeStage(Customer.CustomerLifeStage.CAREERFOCUSED);
        customer.setAssets(Customer.CustomerAssets.FROM150KTO300K);
        customer.addNeed(Customer.CustomerNeed.LIFEINSURANCE);
        customer.addNeed(Customer.CustomerNeed.SAVINGACCOUNT);
        customer.addNeed(Customer.CustomerNeed.MORTAGE);
        kSession.insert(customer);
        // Now we add the global variable which we use to communicate back our
        Offer offer = new Offer();
        kSession.setGlobal("offer", offer);
        kSession.fireAllRules();
        assertEquals(offer.getDiscount(), 10);
        assertEquals(offer.getFinancialPackage(), Offer.ProductPackage.CAREERFOCUSED_PACKAGE);
        assertEquals(offer.getProducts().size(),2);
    }


}
