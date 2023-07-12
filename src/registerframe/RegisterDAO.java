package registerframe;


import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class RegisterDAO {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;
    
    public int registerStudent(Student student){
        con = ConnectionFactory.getConnection();
        try{
            String query = "insert into "
                    + "student "
                    + "(name,dob,gender,mailid,mobile_no,password,program,branch,semester) "
                    + "values(?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, student.getName());
            ps.setDate(2, (Date) student.getDob());
            ps.setString(3, student.getGender());
            ps.setString(4, student.getMailId());
            ps.setString(5, student.getMobileNo());
            ps.setString(6, student.getEncPassword());
            ps.setString(7, student.getProgram());
            ps.setString(8, student.getBranch());
            ps.setInt(9, student.getSemester());
            st = ps.executeUpdate();
            System.out.println("Inserted student " + st);          
        }
        catch(SQLException e){
            e.printStackTrace();
            st = -2;
        }
        return st;
    }
}
