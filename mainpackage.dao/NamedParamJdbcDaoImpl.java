package mainpackage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;
import mainpackage.company.CompanyConfig;
import mainpackage.customer.TempCustomer;

@Repository
public class NamedParamJdbcDaoImpl extends NamedParameterJdbcDaoSupport {

    private final CompanyConfig companyConfig;

    @Autowired
    public NamedParamJdbcDaoImpl(CompanyConfig companyConfig) {
        this.companyConfig = companyConfig;
    }
    
    public void displayTemporaryCustomer(String string2, int int1, int int2) {
    	int TCLNUM = companyConfig.getTCLNUM();
    	int value = int1*int2+TempCustomer.getInitCharge();
    	System.out.println("Temporary customer "+string2+"with temporary customer login number"+TCLNUM+" has a total charge of "+value);
    	companyConfig.incrementTCLNUM();
        companyConfig.updateTCLNUMInDatabase();
    }
	
}