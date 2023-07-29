package mainpackage.customer;

import org.springframework.stereotype.Component;

@Component
public class PermCustomer {
	
	private String nameAndSurname;

	public String getNameAndSurname() {
		return nameAndSurname;
	}
	
	public void setNameAndSurname(String nameAndSurname) {
		this.nameAndSurname = nameAndSurname;
	}
	
	public PermCustomer() {
		
	}
	
	public PermCustomer(String nameAndSurname) {
		setNameAndSurname(nameAndSurname);
	}
	
}
