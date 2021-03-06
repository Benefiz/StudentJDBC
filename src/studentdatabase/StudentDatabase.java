package studentdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ekkasit.tan
 */
public class StudentDatabase {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        

        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        Class.forName(derbyClientDriver);
        String url = "jdbc:derby://localhost:1527/student";
        //String url="jdbc:mysql://localhost:3306/student?serverTimezone=UTC";
        String user = "app";
        //String user = "root";
        String passwd = "app";
        //String passwd = "root";

        Connection con = DriverManager.getConnection(url, user, passwd);
        //create statement
       Statement stmt = con.createStatement();
       Student std1 = new Student(1, "Wack", 3.56);
       Student std2 = new Student(2, "Seen", 2.56);
       insertStudent(stmt, std1);
       insertStudent(stmt, std2);
  
        stmt.close();
        con.close();
    }
    public static void printAllStudent(ArrayList<Student> studentList) {
        for(Student std : studentList) {
           System.out.print(std.getId() + " ");
           System.out.print(std.getName() + " ");
           System.out.println(std.getGpa() + " ");
       }
    }
    
    public static ArrayList<Student> getAllStudent (Connection con) throws SQLException {
        String sql = "select * from student order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           Student student = new Student();
           student.setId(rs.getInt("id"));
           student.setName(rs.getString("name"));
           student.setGpa(rs.getDouble("gpa"));
           studentList.add(student);
       }
       rs.close();
       return studentList;
       
    }
    
   public static Student getStudentById(Statement stmt, int id) throws SQLException {
       Student std = null;
       String sql = "select * from student where id = " + id;
       ResultSet rs = stmt.executeQuery(sql);
       if (rs.next()) {
           std = new Student();
           std.setId(rs.getInt("id"));
           std.setName(rs.getString("name"));
           std.setGpa(rs.getDouble("gpa"));
       }
       return std;
   } 
   public static void insertStudent(Statement stmt, Student std) throws SQLException {
       /*String sql = "insert into student (id, name, gpa)" +
                     " values (5, 'Mark', 12345)";*/
        String sql = "insert into student (id, name, gpa)" +
                     " values (" + std.getId() + "," + "'" + std.getName() + "'" + "," + std.getGpa() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
   } 
   public static void deleteStudent(Statement stmt, Student std) throws SQLException {
       String sql = "delete from student where id = " + std.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("delete " + result + " row");
   }
   public static void updateStudentGpa(Statement stmt, Student std) throws SQLException {
       String sql = "update student set gpa  = " + std.getGpa() + 
               " where id = " + std.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateStudentName(Statement stmt, Student std) throws SQLException {
       String sql = "update student set name  = '" + std.getName() + "'" + 
               " where id = " + std.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   
}
