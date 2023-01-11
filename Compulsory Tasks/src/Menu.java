
import java.sql.*;
import java.util.*;

/**
 * 
 * @author Isaac
 *
 * This project management system reads and writes and updates data about projects and people associated with projects from the database
 */

public class Menu {

	public static void main(String[] args) {
		
		// try catch block is used to test the connection to the database
		try {
			//
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
					"otheruser",
					"swordfish");

			// Creating a direct line to the database for running our queries
			Statement statement = connection.createStatement();
			// main menu
			Scanner scan1 = new Scanner(System.in);
			System.out.print("Enter the number of what you would like to do: "
					+ "\n1 - add project"
					+ "\n2 - view all projects"
					+ "\n3 - delete/update projects"
					+ "\n4 - finalise project"
					+ "\n5 - view overdue/incomplete projects: "
					+ "\n6 - exit: ");
			int input1 = scan1.nextInt();
			scan1.nextLine();
			
			// if else-if statement to control the users input get do specific operations
			if (input1 == 1) {
				System.out.println("Add details");
				Projects newProject = new Projects();
				newProject.createProject();
			} else if (input1 == 2) {
				System.out.println("view all");
				Projects viewAll = new Projects();
				viewAll.printAllFromTable(statement);
			} else if (input1 == 3) {
				Scanner scan2 = new Scanner(System.in);
				System.out.print("\n1 - delete"
						+ "\n2 - update: ");
				int input2 = scan2.nextInt();
				scan2.nextLine();
				if (input2 == 1) {
					System.out.println("delete");
					Projects deleteProject = new Projects();
					deleteProject.deleteProject();
				} else if (input2 == 2) {
					System.out.println("update");
					Projects updateProject = new Projects();
					updateProject.updateProject();
				} else {
					System.out.println("Incorrect input!");
				}
				scan2.close();
			} else if (input1 == 4) {
				System.out.println("Finalise project");
				Projects finalise = new Projects();
				finalise.finaliseProject();
			} else if (input1 == 5) {
				Projects search = new Projects();
				search.searchProject();
			}else if (input1 == 5) {
				System.out.println("Bye");
			} else {
				System.out.println("Incorrect input!");
			}
			
			scan1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}