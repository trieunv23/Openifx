package dao;

import java.sql.SQLException;

import Entity.User;
import Exception.SQLException1;

public interface InterfaceDao<T> {
	public int regesterUser (T t) throws SQLException1 ; 
	
	public void logInUser (T t) throws SQLException1 , SQLException  ; 
	
	public User getInforUser(T t);
	
	
}
