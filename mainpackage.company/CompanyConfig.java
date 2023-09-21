package mainpackage.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyConfig {

	private final DataSource dataSource;
    private int TCLNUM;
    private static int costOfTask1PerHour = 7;
    private static int costOfTask2PerHour = 9;
    private static int costOfTask3PerHour = 8;

    @Autowired
    public CompanyConfig(DataSource dataSource) {
        this.dataSource = dataSource;
	this.initializeTCLNUMFromDatabase();
    }
    
    public int getTCLNUM() {
        return TCLNUM;
    }

    public void setTCLNUM(int TCLNUM) {
        this.TCLNUM = TCLNUM;
    }
    
    public void incrementTCLNUM() {
        this.TCLNUM++;
    }

    public void updateTCLNUMInDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            String updateQuery = "UPDATE COMPANYCONFIG SET TCLNUM = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, this.TCLNUM);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initializeTCLNUMFromDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT TCLNUM FROM COMPANYCONFIG";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int initialTCLNUM = resultSet.getInt("TCLNUM");
                this.TCLNUM = initialTCLNUM;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	public static int getCostOfTask1PerHour() {
		return costOfTask1PerHour;
	}

	public static int getCostOfTask2PerHour() {
		return costOfTask2PerHour;
	}

	public static int getCostOfTask3PerHour() {
		return costOfTask3PerHour;
	}

    
    
}
