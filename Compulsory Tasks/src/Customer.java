import java.sql.*;
import java.util.*;

public class Customer {

	private String name;
	private int telnumber;
	private String email;
	private String address;

	public void addCustomer() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
					"otheruser",
					"swordfish");
			// Creating a direct line to the database for running our queries
			Statement statement = connection.createStatement();
			// declaring variables that will be used later on to display to the user information about the database after it has been affected
			int rowsAffected;
			Scanner addInput = new Scanner(System.in);
			
			System.out.print("Enter the name: ");
			this.name = addInput.nextLine();
			
			System.out.print("Enter the telephone number: ");
			this.telnumber = addInput.nextInt();
			addInput.nextLine();
			
			System.out.print("Enter the email: ");
			this.email = addInput.nextLine();
			
			System.out.print("Enter the address ");
			this.address = addInput.nextLine();


			// SQL query that inserts all the values entered by the user into the database
			rowsAffected = statement.executeUpdate("INSERT INTO customer (name, telnumber, email , address) "
					+ "VALUES (" + "'" + name + "', " + telnumber + ", " +  "'" + email + "', " + "'" + address + "');");
			System.out.println("Query complete, " + rowsAffected + " rows added.");
			printAllFromTableCustomer(statement);
			
			addInput.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method printing all values in all rows.
	 * Takes a statement to try to avoid spreading DB access too far.
	 *
	 * @param  statement on an existing connection
	 * @throws SQLException
	 */
	public void printAllFromTableCustomer(Statement statement) throws SQLException {
		ResultSet results = statement.executeQuery("SELECT customerID, name, telnumber, address FROM customer");
		while (results.next()) {
			System.out.println(
					results.getInt("customerID") + ", "
							+ results.getString("name") + ", "
							+ results.getInt("telnumber") + ", "
							+ results.getString("address") + ", ");
		}
	}
}
