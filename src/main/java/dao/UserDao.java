package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entity.Gender;
import Entity.InforAge;
import Entity.InforAsset;
import Entity.InforGender;
import Entity.InteractUser;
import Entity.MessageUser;
import Entity.User;
import Exception.SQLException1;
import database.JDBC_User;
import javafx.scene.image.Image;

public class UserDao implements InterfaceDao<User>{

	public static UserDao getInstance() {
		return new UserDao();
	}
	
	@Override
	public int regesterUser(User u) throws SQLException1{
		int result = 0 ; 
		try {
			Connection connection = JDBC_User.getConnection(); 
			
			String sql = "INSERT INTO user (username , password1 , fullname , phonenumber , email , dayofbirth , gender , asset ) VALUES (? , ? , ? , ? , ? , ? , ? , ? )" ; 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getPassWord());
			ps.setString(3, u.getFullName());
			ps.setString(4, u.getPhoneNumber());
			ps.setString(5, u.getEmail());
			java.sql.Date sqlDate ; 
			if (u.getDayOfBirth() != null ) {
				sqlDate = java.sql.Date.valueOf(u.getDayOfBirth());
			} else {
				sqlDate = null ; 
			}
			ps.setDate(6, sqlDate);
			
			ps.setString(7, u.getGender().getValue());
			
			ps.setFloat(8, u.getAsset());
			
			result = ps.executeUpdate();
			
			connection.close();
		} catch (SQLException e) {
			if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
                throw new SQLException1("Username already exists !");
            } else {          
                e.printStackTrace();
            }
		}
		return result ; 
	}
	
	public boolean checkInforLogIn(User user) {
		boolean check = false ; 
        try {
        	Connection connection = JDBC_User.getConnection() ; 
        	String sql = "SELECT * FROM user WHERE username = ? AND password1 = ?";
        	PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassWord());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	check = true ; 
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        if (check == true) {
        	return true ; 
        } else {
        	return false ; 
        }
	}
	
	public void saveImageToDatabase(User user, File imageFile) {
        try {
            Connection connection = JDBC_User.getConnection();
            String sql = "UPDATE user SET avata = ? WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
            	FileInputStream fis = new FileInputStream(imageFile);
                statement.setBinaryStream(1, fis, imageFile.length());
                statement.setString(2, user.getUserName()); // Thay thế bằng cột ID của đối tượng trong cơ sở dữ liệu
                statement.executeUpdate();
                fis.close();
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public Image getImageFromDatabase(User user) {
        try {
            Connection connection = JDBC_User.getConnection();
            String sql = "SELECT avata FROM user WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getUserName());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    InputStream inputStream = resultSet.getBinaryStream("avata");
	                    if (inputStream != null ) {
		                    File imageFile = new File("downloaded_image.jpg");
		                    try (FileOutputStream fos = new FileOutputStream(imageFile)) {
		                        byte[] buffer = new byte[1024];
		                        while (inputStream.read(buffer) > 0) {
		                            fos.write(buffer);
		                        }
		                    }
		                    inputStream.close();
		                    Image image = new Image(imageFile.toURI().toString());
		                    imageFile.delete(); // Xóa tệp tạm sau khi sử dụng
		                    return image;
	                    }
                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không có ảnh
    }

	@Override
	public void logInUser(User user) throws SQLException , SQLException1{
		if (!checkInforLogIn(user)) {
			throw new SQLException1("Username or password incorect !"); 
		}
	}
	
	@Override
	public User getInforUser(User u){
		User user = null ; 
		try {
			Connection connection = JDBC_User.getConnection(); 
			String sql =  "SELECT * FROM user WHERE username =? AND password1 =? "; 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getPassWord());
			
			ResultSet infor = ps.executeQuery(); 
			
			while (infor.next()) {
				String username = infor.getString("username");
				String password = infor.getString("password1"); 
				String fullname = infor.getString("fullname");
				String phonenumber = infor.getString("phonenumber");
				String email = infor.getString("email");
				
				LocalDate dayofbrith ; 
				if (infor.getDate("dayofbirth") != null ) {
					dayofbrith = infor.getDate("dayofbirth").toLocalDate();
				} else {
					dayofbrith = null ; 
				}
				
				Gender gender = Gender.fromString(infor.getString("gender"));
				float asset = infor.getFloat("asset"); 
				
				user = new User (username , password , fullname , phonenumber , email , dayofbrith , gender , asset ); 
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user ; 
	}
	
	public List<User> getListUser(String frist) {
		frist = frist + "%" ; 
		List<User> list = new ArrayList<User>();
		
		try {
			Connection connection = JDBC_User.getConnection(); 
			String sql = "SELECT * FROM user WHERE user.username LIKE ? " ;
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, frist);
			
			ResultSet infor = ps.executeQuery() ; 
			while (infor.next()) {
				String username = infor.getString("username"); 
				String password = infor.getString("password1");
				String fullname = infor.getString("fullname"); 
				String phonenumber = infor.getString("phonenumber");
				String email = infor.getString("email");
				
				LocalDate dayofbrith ; 
				if (infor.getDate("dayofbirth") != null ) {
					dayofbrith = infor.getDate("dayofbirth").toLocalDate();
				} else {
					dayofbrith = null ; 
				}
				
				Gender gender = Gender.fromString(infor.getString("gender"));
				
				float asset = infor.getFloat("asset");
				
				User user = new User (username , password , fullname , phonenumber , email , dayofbrith , gender , asset) ; 
				list.add(user);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list ; 
		
	}
	
	public int deleteUser(User user) {
		int result = 0 ; 
		try {
			Connection connection = JDBC_User.getConnection() ; 
			
			String sql = "DELETE FROM user WHERE user.username = ?" ; 
			
			PreparedStatement ps = connection.prepareStatement(sql); 
			ps.setString(1, user.getUserName());
			
			result = ps.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ; 
	}
	
	public int getNumberUser() {
		int result = 0 ; 
		try {
			Connection connection = JDBC_User.getConnection(); 
			String sql = "SELECT COUNT(user.username) as numberuser FROM user" ; 
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt("numberuser") ;
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ; 
	}
	
	public int updateUser(User user , String nameuser) throws SQLException1 {
		int result = 0 ; 
		
		try {
			Connection connection = JDBC_User.getConnection();
			String sql = "UPDATE user SET user.username = ? , user.password1 = ? , user.fullname = ? , user.phonenumber = ? , user.email = ? , user.dayofbirth = ? , user.gender = ? , user.asset = ? "
					+ " WHERE user.username = ? " ; 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassWord());
			ps.setString(3, user.getFullName());
			ps.setString(4, user.getPhoneNumber());
			ps.setString(5, user.getEmail());
			
			java.sql.Date sqlDate ; 
			if (user.getDayOfBirth() != null ) {
				sqlDate = java.sql.Date.valueOf(user.getDayOfBirth());
			} else {
				sqlDate = null ; 
			}
			ps.setDate(6, sqlDate);
			
			ps.setString(7 , user.getGender().getValue());
			ps.setFloat(8, user.getAsset());
			ps.setString(9, nameuser);
			
			result = ps.executeUpdate(); 
			
		} catch (SQLException e) {
			if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
                throw new SQLException1("Username already exists !");
            } else {          
                e.printStackTrace();
            }
		} 
		return result ; 
	}
	
	public List<InforGender> getListGender() {
		List<InforGender> list = new ArrayList<InforGender>(); 
		try {
			Connection connection = JDBC_User.getConnection(); 
			
			String sql = "SELECT user.gender , COUNT(user.gender) AS quantity FROM user GROUP BY (user.gender)" ; 
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ResultSet infor = ps.executeQuery();
			
			while (infor.next()) {
				String gender = infor.getString("gender");
				int quantity = infor.getInt("quantity");
				InforGender ig = new InforGender(gender, quantity);
				list.add(ig);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list; 
		
	}
	
	public List<InforAge> getListAge() {
		List<InforAge> list = new ArrayList<InforAge>();
		try {
			Connection connection = JDBC_User.getConnection() ; 
			
			String sql = "SELECT displayAge(user.username) AS age ,  COUNT(displayAge(user.username)) AS quantityAge  FROM user WHERE user.dayofbirth IS NOT NULL GROUP BY (age)" ; 
			PreparedStatement ps = connection.prepareStatement(sql); 
			
			ResultSet infor = ps.executeQuery();
			while (infor.next()) {
				String age = String.valueOf(infor.getInt("age"));
				int quantity = infor.getInt("quantityAge");
				InforAge ia = new InforAge(age, quantity);
				list.add(ia);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list ; 
	}
	
	public List<InforAsset> groupAsset() {
		List<InforAsset> list = new ArrayList<InforAsset>();
		try {
			Connection connection = JDBC_User.getConnection();
			
			String sql = "SELECT 100 AS value, groupuser(100) AS quantity\r\n"
					+ "UNION\r\n"
					+ "SELECT 200 AS value, groupuser(200) AS quantity\r\n"
					+ "UNION\r\n"
					+ "SELECT 300 AS value, groupuser(300) AS quantity\r\n"
					+ "UNION \r\n"
					+ "SELECT 400 AS value, groupuser(400) AS quantity\r\n"
					+ "UNION \r\n"
					+ "SELECT 500 AS value, groupuser(500) AS quantity;\r\n"
					+ "";
			PreparedStatement ps = connection.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String value = String.valueOf(rs.getString("value"));
				int quantity = rs.getInt("quantity");
				InforAsset ia = new InforAsset(value, quantity) ; 
				list.add(ia);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list ; 
		
	}
	
	public List<MessageUser> getMessage(User user){
		List<MessageUser> list = new ArrayList<MessageUser>();
	 	try {
			Connection connection = JDBC_User.getConnection() ; 
			
			String sql = "SELECT * FROM message WHERE message.sender = ? OR message.receiver = ? ORDER BY message.daysend DESC" ;
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserName());
			
			ResultSet infor = ps.executeQuery();
			while(infor.next()) {
				String sender = infor.getString("sender");
				String namesender = infor.getString("namesender");
				String receiver = infor.getString("receiver");
				String namereceiver = infor.getString("namereceiver");
				String message = infor.getString("messages");
				String daysend = infor.getString("daysend");
				MessageUser mu = new MessageUser(sender, namesender,receiver, namereceiver ,  message, daysend);
				list.add(mu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	 	return list ; 
	}
	
	public int sendMessage(String sender , String receiver , String message) {
		int result = 0 ; 
		try {
			Connection connection = JDBC_User.getConnection();
			
			String sql = "CALL setMassage(? , ? , ?);" ; 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, sender);
			ps.setString(2, receiver);
			ps.setString(3, message);
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result ;
	}
	
	public List<InteractUser> getListInteract(String username){
		List<InteractUser> list = new ArrayList<InteractUser>();
		try {
			Connection connection = JDBC_User.getConnection();
			
			String sql = "CALL GetInteractingUsers(?);" ;
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet infor = ps.executeQuery();
			while (infor.next()) {
				String usernameinteract = infor.getString("interacting_user");
				String fullnameinteract = infor.getString("interacting_user_name");
				String timeinteract = infor.getString("last_interaction_date");
				
				InteractUser iu = new InteractUser(usernameinteract, fullnameinteract, timeinteract);
				list.add(iu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list ;	
	}
	
	public boolean checkExistUsername(String username) {
		int result = -1; 
		try {
			Connection connection = JDBC_User.getConnection(); 
			String sql = "SELECT checkexistusername(?) AS result " ; 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet infor = ps.executeQuery();
			
			while (infor.next()) {
				result = infor.getInt("result") ; 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (result == 0) {
			return true ; 
		} else {
			return false ; 
		}
		
	}
}
