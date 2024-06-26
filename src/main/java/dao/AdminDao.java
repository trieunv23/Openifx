package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Entity.Admin1;
import Entity.Gender;
import Entity.User;
import database.JDBC_User;

public class AdminDao {
	
	public static AdminDao getInstance() {
		return new AdminDao();
	}
	
	public boolean Login(Admin1 admin) {
		try {
			Connection connection = JDBC_User.getConnection(); 
			String sql =  "SELECT * FROM admin1 WHERE username =? AND password =? "; 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, admin.getUsername());
			ps.setString(2, admin.getPassword());
			
			ResultSet infor = ps.executeQuery(); 
			
			while (infor.next()) {
				return true ; 
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false ; 
	}
}
