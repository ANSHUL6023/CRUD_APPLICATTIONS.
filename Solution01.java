package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class demo {
	private static final String driver_ClassName = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String username = "c##anshul";
	private static final String password = "anshul";
	private static final String AddEmployee = "INSERT INTO EMPLOYEE VALUES (?,?,?,?,?)";
	private static final String AllEmployee = "SELECT * FROM EMPLOYEE";
	private static final String getByEmployeeId = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";
	private static final String updateEmployeeBsal = "UPDATE EMPLOYEE SET BSAL= ?, TOTSAL= ? WHERE EMPLOYEE_ID= ?";
	private static final String deleteEmployee = "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID= ?";
	
	public static void main(String[] args) throws Exception{
		
		Class.forName(driver_ClassName);
		Connection con = DriverManager.getConnection(url, username, password);
		
		PreparedStatement ps1 = con.prepareStatement(AddEmployee);
		PreparedStatement ps2 = con.prepareStatement(AllEmployee);
		PreparedStatement ps3 = con.prepareStatement(getByEmployeeId);
		PreparedStatement ps4 = con.prepareStatement(updateEmployeeBsal);
		PreparedStatement ps5 = con.prepareStatement(deleteEmployee);
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		while(true) {
			System.out.println("\n\n1. Add Employee.");
			System.out.println("2. See All Employee.");
			System.out.println("3. See All Employee by ID.");
			System.out.println("4. Update Employee Bsal by ID.");
			System.out.println("5. Delete Record by ID.");
			System.out.println("6. Exit.\n\n");
			
			System.out.println("Enter Your Choice : ");
			String opt = br.readLine();
			
			switch(opt) {
			case "1":{
				System.out.println("Enter your Employee_ID: ");
				int id = Integer.parseInt(br.readLine());
				
				System.out.println("Enter your NAME: ");
				String name = br.readLine();
				
				System.out.println("Enter your DESG: ");
				String desg = br.readLine();
				
				System.out.println("Enter your BSAL: ");
				int bsal = Integer.parseInt(br.readLine());
				
				double hra = 0.93 * bsal;
                double da = 0.61 * bsal;
                double totsal = bsal + hra + da;
				
				
				ps1.setInt(1, id);
				ps1.setString(2, name);
				ps1.setString(3, desg);
				ps1.setDouble(4, bsal);
				ps1.setDouble(5, totsal);
				
				int k = ps1.executeUpdate();
				if (k > 0)
					System.out.println("RECORD INSERTED");
				else
					System.out.println("FAILED");
			}
				break;
				
			case "2":{
				ResultSet rs = ps2.executeQuery();
				
				while(rs.next()) {
					System.out.println(rs.getInt(1)+ "\t" + rs.getString(2)+ "\t" + rs.getString(3)+ "\t" + rs.getInt(4)+ "\t" + rs.getInt(5));
				}
			}
				break;
				
			case "3":{
			
				System.out.println("Enter Employee_Id: ");
				int id = Integer.parseInt(br.readLine());
				ps3.setInt(1, id);
				
				ResultSet rs = ps3.executeQuery();
				if(rs.next()) {
					System.out.println(rs.getInt(1)+ "\t" + rs.getString(2)+ "\t" + rs.getString(3)+ "\t" + rs.getInt(4)+ "\t" + rs.getInt(5));
				}else {
					System.err.println("Record not found.");
				}
			}
				break;
				
			case "4":{
				
				 System.out.println("Enter Employee Id: ");
                 int id = Integer.parseInt(br.readLine());
                 ps3.setInt(1, id);
                 ResultSet rs = ps3.executeQuery();
                 if (rs.next()) {
                     double oldBsal = rs.getDouble("BSAL");
                     System.out.println("OldBsal: " + oldBsal);
                     System.out.println("OldtotSal: " + rs.getDouble("TOTSAL"));
                     
                     System.out.println("Enter new BSAL: ");
                     double newBsal = Double.parseDouble(br.readLine());
                     
                     double hra = 0.93 * newBsal;
                     double da = 0.61 * newBsal;
                     double totSal = newBsal + hra + da;
                     
                     ps4.setDouble(1, newBsal);
                     ps4.setDouble(2, totSal);
                     ps4.setInt(3, id);
                     
                     int k = ps4.executeUpdate();
                     if (k > 0) {
                         System.out.println("Record Updated");
                     } else {
                         System.err.println("Failed to update.");
                     }
                 } else {
                     System.err.println("Invalid Employee Id.");
                 }
             }
             break;
			case "5":{
				
				System.out.println("Enter Employee Id: ");
                int id = Integer.parseInt(br.readLine());
                ps5.setInt(1, id);
                
                int k = ps5.executeUpdate();
                if (k > 0) {
                    System.out.println("Record Deleted.");
                } else {
                    System.err.println("Failed to delete.");
                }
			}
				break;
			case "6":{
				System.out.println("Good Bye");
				System.exit(0);
			}
				break;
			default:
				System.out.println("INVALID OPTION SELECTED.");
			}
		}
	}
}
