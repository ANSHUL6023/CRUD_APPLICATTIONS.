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
	private static final String AddStudent = "INSERT INTO STUDENT VALUES (?,?,?,?,?,?)";
	private static final String AllStudent = "SELECT * FROM STUDENT";
	private static final String getByStudentRollno = "SELECT * FROM STUDENT WHERE ROLLNO = ?";
	private static final String updateStudentTotMarks = "UPDATE STUDENT SET TOTMARKS= ? WHERE ROLLNO= ?";
	private static final String deleteStudent = "DELETE FROM STUDENT WHERE ROLLNO= ?";
	
	public static void main(String[] args) throws Exception{
		
		Class.forName(driver_ClassName);
		Connection con = DriverManager.getConnection(url, username, password);
		
		PreparedStatement ps1 = con.prepareStatement(AddStudent);
		PreparedStatement ps2 = con.prepareStatement(AllStudent);
		PreparedStatement ps3 = con.prepareStatement(getByStudentRollno);
		PreparedStatement ps4 = con.prepareStatement(updateStudentTotMarks);
		PreparedStatement ps5 = con.prepareStatement(deleteStudent);
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		while(true) {
			System.out.println("\n\n1. Add Student.");
			System.out.println("2. See All Student.");
			System.out.println("3. See All Student by Rollno.");
			System.out.println("4. Update Student TotMarks by Rollno.");
			System.out.println("5. Delete Student by Rollno.");
			System.out.println("6. Exit.\n\n");
			
			System.out.println("Enter Your Choice : ");
			String opt = br.readLine();
			
			switch(opt) {
			case "1":{
				System.out.println("Enter your RollNo: ");
				int RollNo = Integer.parseInt(br.readLine());
				
				System.out.println("Enter your Name: ");
				String name = br.readLine();
				
				System.out.println("Enter your Branch: ");
				String branch = br.readLine();
				
				System.out.println("Enter your TotMarks: ");
				int TotMarks = Integer.parseInt(br.readLine());
				
				double per = (TotMarks / 600.0) * 100;
		        String grade = calculateGrade(per);
		        
		        ps1.setInt(1, RollNo);
		        ps1.setString(2, name);
		        ps1.setString(3, branch);
		        ps1.setInt(4, TotMarks);
		        ps1.setDouble(5, per);
				ps1.setString(6, grade);
				
				int k = ps1.executeUpdate();
				if (k > 0)
					System.out.println("RECORD INSERTED");
				else
					System.out.println("FAILED");
			}
				break;
				
			case "2":{
				ResultSet rs2 = ps2.executeQuery();
				
				while(rs2.next()) {
					System.out.println(rs2.getInt(1)+ "\t" + rs2.getString(2)+ "\t" + rs2.getString(3)+ "\t" + rs2.getInt(4)+ "\t" + rs2.getDouble(5)+ "\t" + rs2.getString(6));
				}
			}
				break;
				
			case "3":{
				System.out.println("Enter Student RollNo: ");
				int RollNo = Integer.parseInt(br.readLine());
				ps3.setInt(1, RollNo);
				
				ResultSet rs = ps3.executeQuery();
				if(rs.next()) {
					System.out.println(rs.getInt(1)+ "\t" + rs.getString(2)+ "\t" + rs.getString(3)+ "\t" + rs.getInt(4)+ "\t" + rs.getDouble(5)+ "\t" + rs.getString(6));
				}else {
					System.err.println("Record not found.");
				}
			}
				break;
				
			case "4":{
				System.out.print("Enter RollNo: ");
		        int RollNo = Integer.parseInt(br.readLine());
		        
		        System.out.print("Enter New Total Marks: ");
		        int totMarks = Integer.parseInt(br.readLine());

		        double per = (totMarks / 600.0) * 100;
		        String grade = calculateGrade(per);
		        
		        ps4.setInt(1, totMarks);
		        ps4.setDouble(2, per);
		        ps4.setString(3, grade);
		        ps4.setInt(4, RollNo);

		        int result = ps4.executeUpdate();
		        
		        if (result > 0) {
		            System.out.println("Student updated successfully.");
		        } else {
		            System.out.println("Failed to update student.");
		        }
			}
				break;
				
			case "5":{
				System.out.println("Enter Student RollNo: ");
                int RollNo = Integer.parseInt(br.readLine());
                ps5.setInt(1, RollNo);
                
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
	private static String calculateGrade(double per) {
        if (per >= 90) return "A";
        if (per >= 80) return "B";
        if (per >= 70) return "C";
        if (per >= 60) return "D";
        return "F";
    }
}
