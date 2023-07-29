package mainpackage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import mainpackage.company.CompanyConfig;
import mainpackage.dao.NamedParamJdbcDaoImpl;
public class EngineersLoginApp {
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("engineersinfo.xml");
		Engineers engineers = ctx.getBean("engineers", Engineers.class);
		Scanner scanner = new Scanner(System.in);
		boolean loginFail = true;
		boolean ifNotExit = true;
		while (loginFail) {
			System.out.println("Enter 'exit' for both username and password if you want to exit.");
			System.out.println("Enter username: ");
	        String inputname = scanner.nextLine();
	        System.out.println("Enter password: ");
	        String inputpw = scanner.nextLine();
	        ifNotExit = !inputname.equals("exit") || !inputpw.equals("exit");
	        loginFail = engineers.login(inputname, inputpw) && ifNotExit;
		}
		if(loginFail == false && ifNotExit == true) {
			AbstractApplicationContext ctx2 = new ClassPathXmlApplicationContext("namedparamjdbcdaoimpl.xml");
			NamedParamJdbcDaoImpl dao = ctx2.getBean("namedParamJdbcDaoImpl", NamedParamJdbcDaoImpl.class);
			String[] leavingTempCustomers = {"tempCustomerNandS2", "tempCustomerNandS3", "tempCustomerNandS5", "tempCustomerNandS7"};
			String inputCsvFile = "C:\\Users\\berkehan\\Downloads\\allTCHourTaskNandS.csv";
			try (BufferedReader br = new BufferedReader(new FileReader(inputCsvFile))) {
			    String outputCsvFile = "remainingTCHourTaskNandS.csv";
			    try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputCsvFile))) {
			    	try {
			    	    String line;
			    	    while ((line = br.readLine()) != null) {
			    	        String[] parts = line.split(",");
			    	        if (parts.length >= 3) {
			    	        	int int1 = Integer.parseInt(parts[0].replaceAll("\"", "").trim());
			    	        	String string1 = parts[1].replaceAll("\"", "").trim();
			    	            String string2 = parts[2].replaceAll("\"", "").trim();
			    	            if(ifContains(leavingTempCustomers, string2)) {
			    	            	if(string1.equals("Task1")) {
			    	            		dao.displayTemporaryCustomer(string2, int1, CompanyConfig.getCostOfTask1PerHour());
			    	            	}
			    	            	else if(string1.equals("Task2")) {
			    	            		dao.displayTemporaryCustomer(string2, int1, CompanyConfig.getCostOfTask2PerHour());
			    	            	}
			    	            	else if(string1.equals("Task3")) {
			    	            		dao.displayTemporaryCustomer(string2, int1, CompanyConfig.getCostOfTask3PerHour());
			    	            	}
			    	            	else {
			    	            		System.err.println("Error: Invalid task - " + line);
			    	            	}
			    	            }
			    	            else {
			    	            	bw.write(int1 + "," + string1 + "," + string2);
			    	            }
			    	            bw.newLine();
			    	        } else {
			    	            System.err.println("Error: Invalid line format - " + line);
			    	        }
			    	    }
			    	} catch (IOException e) {
			    	    e.printStackTrace();
			    	}
			        System.out.println("Data has been written to " + outputCsvFile);
			    } catch (IOException e) {
			        e.printStackTrace();
			    }

			} catch (IOException e) {
			    e.printStackTrace();
			}
			ctx2.close();
		}
		scanner.close();
		ctx.close();
	}
	
	public static boolean ifContains(String[] stringarray, String string) {
		for (String item : stringarray) {
            if(item.equals(string)) {
            	return true;
            }
        }
		return false;
	}
}