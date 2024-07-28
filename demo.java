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
	private static final String AddBookDetails = "INSERT INTO BOOKDETAILS VALUES (?,?,?,?,?)";
	private static final String AllBookDetails = "SELECT * FROM BOOKDETAILS";
	private static final String getBookbyCode = "SELECT * FROM BOOKDETAILS WHERE CODE = ?";
	private static final String updateBookDetails = "UPDATE BOOKDETAILS SET PRICE = ?, QTY = ? WHERE CODE = ?";
	private static final String deleteBook = "DELETE FROM BOOKDETAILS WHERE CODE= ?";
	
	public static void main(String[] args) throws Exception{
		
		Class.forName(driver_ClassName);
		Connection con = DriverManager.getConnection(url, username, password);
		
		PreparedStatement ps1 = con.prepareStatement(AddBookDetails);
		PreparedStatement ps2 = con.prepareStatement(AllBookDetails);
		PreparedStatement ps3 = con.prepareStatement(getBookbyCode);
		PreparedStatement ps4 = con.prepareStatement(updateBookDetails);
		PreparedStatement ps5 = con.prepareStatement(deleteBook);
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		while(true) {
			System.out.println("\n\n1. Add Book Details.");
			System.out.println("2. See All Books.");
			System.out.println("3. See All Book Details by Code.");
			System.out.println("4. Update Book Price/QTY by Code.");
			System.out.println("5. Delete Book Detail by Code.");
			System.out.println("6. Exit.\n\n");
			
			System.out.println("Enter Your Choice : ");
			String opt = br.readLine();
			
			switch(opt) {
			case "1":{
				System.out.println("Enter Book Code: ");
				int code = Integer.parseInt(br.readLine());
				
				System.out.println("Enter Book Name: ");
				String name = br.readLine();
				
				System.out.println("Enter Author Name: ");
				String author = br.readLine();
				
				System.out.println("Enter Book Price: ");
				int price = Integer.parseInt(br.readLine());
				
				System.out.println("Enter Book QTY: ");
				int qty = Integer.parseInt(br.readLine());
				
				ps1.setInt(1, code);
		        ps1.setString(2, name);
		        ps1.setString(3, author);
		        ps1.setInt(4, price);
		        ps1.setInt(5, qty);
				
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
					System.out.println(rs2.getInt(1)+ "\t" + rs2.getString(2)+ "\t" + rs2.getString(3)+ "\t" + rs2.getInt(4)+ "\t" + rs2.getInt(5));
				}
			}
				break;
				
			case "3":{
				System.out.println("Enter Book Code: ");
				int code = Integer.parseInt(br.readLine());
				ps3.setInt(1, code);
				
				ResultSet rs = ps3.executeQuery();
				if(rs.next()) {
					System.out.println(rs.getInt(1)+ "\t" + rs.getString(2)+ "\t" + rs.getString(3)+ "\t" + rs.getInt(4)+ "\t" + rs.getInt(5));
				}else {
					System.err.println("Record not found.");
				}
			}
				break;
				
			case "4":{
				System.out.println("Enter Book Code: ");
				int code = Integer.parseInt(br.readLine());
				
				System.out.println("Enter New Price: ");
				int price = Integer.parseInt(br.readLine());
				
				System.out.println("Enter New Qty: ");
				int qty = Integer.parseInt(br.readLine());
				
				 ps4.setInt(1, price);
			     ps4.setInt(2, qty);
			     ps4.setInt(3, code);

			     int result = ps4.executeUpdate();
			     if (result > 0) {
			    	 System.out.println("Book details updated successfully.");
			     }
			     else {
			            System.out.println("Failed to update book details.");
			        }
			}
				break;
				
			case "5":{
				System.out.println("Enter Book Code: ");
                int code = Integer.parseInt(br.readLine());
                ps5.setInt(1, code);
                
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
