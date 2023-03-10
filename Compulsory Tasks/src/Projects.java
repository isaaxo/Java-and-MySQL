
import java.sql.*;
import java.util.Scanner;

public class Projects {
	private String projectName;
	private String buildingType;
	private String address;
	private int erf;
	private double totalFee;
	private double totalPaid;
	private String deadline;
	private String completion;
	private String status;
	private int customerID;
	private int architectID;
	private int contractorID;


	// this method asks the user to enter the details of the project
	public void createProject() {

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
					"otheruser",
					"swordfish");
			// Creating a direct line to the database for running our queries
			Statement statement = connection.createStatement();
			// declaring variables that will be used later on to display to the user information about the database after it has been affected
			int rowsAffected;
			Scanner projInput = new Scanner(System.in);

			// setting the variables as constants because it is a new project therefore it cannot be completion or finalised
			this.completion = "0001-01-01";

			this.status = "Not finalised";

			System.out.print("Enter the name of the project: ");
			this.projectName = projInput.next();     // toUpper case method is used to minimise input discrepancies
			projInput.nextLine();

			System.out.print("Enter the building type of the project: ");
			this.buildingType = projInput.next();
			projInput.nextLine();

			System.out.print("Enter the address: ");
			this.address = projInput.next();
			projInput.nextLine();

			System.out.print("Enter the ERF: ");
			this.erf = projInput.nextInt();
			projInput.nextLine();

			System.out.print("Enter the total fee of the project: ");
			this.totalFee = projInput.nextDouble();
			projInput.nextLine();

			System.out.print("Enter the amount paid to date: ");
			this.totalPaid = projInput.nextDouble();
			projInput.nextLine();

			System.out.print("Enter the deadline of the project (YYYY-MM-DD): ");
			this.deadline = projInput.next();
			projInput.nextLine(); 

			// asking the user if they want to add a customer or select from existing customer
			System.out.print("Add new customer or select from existing? "
					+ "\n1 - add new"
					+ "\n2 - existing: ");
			int cusInput = projInput.nextInt();
			projInput.nextLine();

			// if else statement to control the users input
			if (cusInput == 1) {
				Customer customerDet = new Customer();
				customerDet.addCustomer();
			} else if (cusInput == 2) {
				Customer customerDet = new Customer();
				customerDet.printAllFromTableCustomer(statement);
			} else {
				System.out.println("Invalid option!");
			}
			// the user then enters the customer ID generated by the table so that it can be added to the project details
			System.out.print("Enter the customer ID as displayed in the table: ");
			this.customerID = projInput.nextInt();
			projInput.nextLine();

			// asking the user if they want to add a architect or select from existing architect
			System.out.print("Add new architect or select from existing? "
					+ "\n1 - add new"
					+ "\n2 - existing: ");
			int archInput = projInput.nextInt();
			projInput.nextLine();

			// if else statement to control the users input
			if (archInput == 1) {
				Architect architectDet = new Architect();
				architectDet.addArchitect();
			} else if (archInput == 2) {
				Architect architectDet = new Architect();
				architectDet.printAllFromTableArchitect(statement);
			} else {
				System.out.println("Invalid option!");
			}
			// the user then enters the customer ID generated by the table so that it can be added to the project details
			System.out.print("Enter the architect ID as displayed in the table: ");
			this.architectID = projInput.nextInt();
			projInput.nextLine();

			// asking the user if they want to add a contractor or select from existing architect
			System.out.print("Add new contractor or select from existing? "
					+ "\n1 - add new"
					+ "\n2 - existing: ");
			int conInput = projInput.nextInt();
			projInput.nextLine();

			// if else statement to control the users input
			if (conInput == 1) {
				Contractor contractorDet = new Contractor();
				contractorDet.addContractor();
			} else if (conInput == 2) {
				Contractor customerDet = new Contractor();
				customerDet.printAllFromTableContractor(statement);
			} else {
				System.out.println("Invalid option");
			}
			// the user then enters the customer ID generated by the table so that it can be added to the project details
			System.out.print("Enter the contractor ID as displayed in the table: ");
			this.contractorID = projInput.nextInt();
			projInput.nextLine();

			System.out.println("Details captured!\r\n");

			// SQL query that inserts all the values entered by the user into the database
			rowsAffected = statement.executeUpdate("INSERT INTO projects (projectName, buildingType, address, erf, totalFee, totalPaid, deadline,"
					+ "completion, status, customerID, architectID, contractorID) VALUES (" + "'" + projectName + "', " + "'" + buildingType + "', " 
					+ "'" + address + "', " + erf + ", " + totalFee + ", " + totalPaid + ", " + "'" + deadline + "', " + "'" + completion + "', "
					+ "'" + status + "', " + customerID  + ", " + architectID + ", " + contractorID + ")");

			System.out.println("Query complete, " + rowsAffected + " rows added.");

			printAllFromTable(statement);
			
			projInput.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method printing all values in all rows.
	 * Takes a statement to try to avoid spreading DB access too far.
	 *
	 * @param statement on an existing connection
	 * @throws SQLException
	 */
	public void printAllFromTable(Statement statement) throws SQLException {
		ResultSet results = statement.executeQuery("SELECT projectNumber, projectName, buildingType, address,"
				+ "erf, totalFee, totalPaid, deadline, completion, status FROM projects");
		while (results.next()) {
			System.out.println(
					results.getInt("projectNumber") + ", "
							+ results.getString("projectName") + ", "
							+ results.getString("buildingType") + ", "
							+ results.getString("address") + ", "
							+ results.getInt("erf") + ", "
							+ results.getDouble("totalFee") + ", "
							+ results.getDouble("totalPaid") + ", "
							+ results.getString("deadline") + ", "
							+ results.getString("completion") + ", "
							+ results.getString("status") + ", ");
		}
	}

	// method to update a project in the database
	public void updateProject() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false", "otheruser", "swordfish");
			// Creating a direct line to the database for running our queries
			Statement statement = connection.createStatement();
			// declaring variables that will be used later on to display to the user information about the database after it has been affected
			int rowsAffected;
			Scanner updateInput = new Scanner(System.in);

			// shows the user all the projects
			System.out.println("All projects:");
			printAllFromTable(statement);

			// the user identifies the project by the project number
			System.out.print("Select the project number of the project you want to update: ");
			int input1 = updateInput.nextInt();
			updateInput.nextLine();

			System.out.print("Which detail would you like to update:"
					+ "\n1 - address"
					+ "\n2 - deadline"
					+ "\n3 - name: ");
			int input2 = updateInput.nextInt();
			updateInput.nextLine();

			if (input2 == 1) {
				System.out.print("Enter the new address: ");
				String input3 = updateInput.nextLine();
				rowsAffected = statement.executeUpdate("UPDATE projects SET address = " + "'" + input3 + "'" + "WHERE projectNumber = " + "'" + input1 + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
			} else if (input2 == 2) {
				System.out.print("Enter the new deadline: ");
				String input4 = updateInput.nextLine();
				rowsAffected = statement.executeUpdate("UPDATE projects SET deadline = " + "'" + input4 + "'" + "WHERE projectNumber = " + "'" + input1 + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
			} else if (input2 == 3) {
				System.out.print("Enter the new project name: ");
				String input5 = updateInput.nextLine();
				rowsAffected = statement.executeUpdate("UPDATE projects SET projectName = " + "'" + input5 + "'" + "WHERE projectNumber = " + "'" + input1 + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
			} else {
				System.out.println("Invalid option");
			}

			// Displays the table with the update records
			printAllFromTable(statement);
			
			updateInput.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// method to delete a project from the database
	public void deleteProject() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false", "otheruser", "swordfish");
			// Creating a direct line to the database for running our queries
			Statement statement = connection.createStatement();
			// declaring variables that will be used later on to display to the user information about the database after it has been affected
			int rowsAffected;
			Scanner deleteInput = new Scanner(System.in);

			// shows the user all the projects
			System.out.println("All projects:");
			printAllFromTable(statement);

			System.out.print("\nEnter the project number of the project you want to delete: ");
			int input6 = deleteInput.nextInt();
			deleteInput.nextLine();
			rowsAffected = statement.executeUpdate("DELETE FROM projects WHERE projectNumber = " + input6);
			System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
			printAllFromTable(statement);

			deleteInput.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// method to finalise a project and print out an invoice
	public void finaliseProject() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false", "otheruser", "swordfish");
			// Creating a direct line to the database for running our queries
			Statement statement = connection.createStatement();
			// declaring variables that will be used later on to display to the user information about the database after it has been affected
			int rowsAffected;
			Scanner finaliseInput = new Scanner(System.in);

			// shows the user all the projects
			System.out.println("All projects:");
			printAllFromTable(statement);
			System.out.print("\nEnter the project number of the project you want to finalise: ");
			int input6 = finaliseInput.nextInt();
			finaliseInput.nextLine();
			System.out.println("Enter the date of completion (YYYY-MM-DD): ");
			String input7 = finaliseInput.nextLine();
			rowsAffected = statement.executeUpdate("UPDATE projects SET status = 'finalised' WHERE projectNumber = " + input6);
			System.out.println("\nQuery complete, " + rowsAffected + " rows updated.");
			rowsAffected = statement.executeUpdate("UPDATE projects SET completion = " + "'" + input7 + "'" + " WHERE projectNumber = " + input6);
			System.out.println("\nQuery complete, " + rowsAffected + " rows updated.");
			printAllFromTable(statement);

			// 
			ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE projectNumber = " + input6);
			int fee = 0;
			int paid = 0;
			int total = 0;
			int custID = 0;
			while (results.next()) {
				paid = results.getInt("totalPaid");
				fee = results.getInt("totalFee");
				total = fee - paid;
				custID = results.getInt("customerID");
			}

			if (fee == paid) {
				System.out.println("Everything has been paid for ");
				System.out.println("No Invoice");
			} else if (paid < fee) {
				System.out.println("Please find invoice below: ");
				ResultSet results2 = statement.executeQuery("SELECT * FROM customer WHERE customerID = " + custID);
				while (results2.next()) {
					System.out.println(
							results.getInt("name") + ", "
									+ results.getString("telnumber") + ", "
									+ results.getString("email") + ", "
									+ results.getString("address") + ", ");
				}

			} else {
				System.out.println("Invalid option!");
			}

			finaliseInput.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// a method to display incomplete and overdue projects
	public void searchProject() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false", "otheruser", "swordfish");
			// Creating a direct line to the database for running our queries
			Statement statement = connection.createStatement();
			// declaring variables that will be used later on to display to the user information about the database after it has been affected
			ResultSet results;
			Scanner searchInput = new Scanner(System.in);

			System.out.print("Enter what you want to do: "
					+ "\n1 - view overdue projects"
					+ "\n2 - view incomplete projects: ");
			int input = searchInput.nextInt();
			searchInput.nextLine();

			if (input == 1) {
				results = statement.executeQuery("SELECT projectNumber, projectName, buildingType, address,"
						+ "erf, totalFee, totalPaid, deadline, completion, status FROM projects WHERE deadline < curdate();");
				while (results.next()) {
					System.out.println(
							results.getInt("projectNumber") + ", "
									+ results.getString("projectName") + ", "
									+ results.getString("buildingType") + ", "
									+ results.getString("address") + ", "
									+ results.getInt("erf") + ", "
									+ results.getDouble("totalFee") + ", "
									+ results.getDouble("totalPaid") + ", "
									+ results.getString("deadline") + ", "
									+ results.getString("completion") + ", "
									+ results.getString("status") + ", ");
				}
			} else if (input == 2) {
				results = statement.executeQuery("SELECT projectNumber, projectName, buildingType, address,"
						+ "erf, totalFee, totalPaid, deadline, completion, status FROM projects WHERE status != 'finalised';");
				while (results.next()) {
					System.out.println(
							results.getInt("projectNumber") + ", "
									+ results.getString("projectName") + ", "
									+ results.getString("buildingType") + ", "
									+ results.getString("address") + ", "
									+ results.getInt("erf") + ", "
									+ results.getDouble("totalFee") + ", "
									+ results.getDouble("totalPaid") + ", "
									+ results.getString("deadline") + ", "
									+ results.getString("completion") + ", "
									+ results.getString("status") + ", ");
				}
			} else {
				System.out.println("Invalid option!");
			}
			
			searchInput.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}