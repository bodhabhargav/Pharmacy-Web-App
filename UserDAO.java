package com.caseStudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.dbfw.DBConnectionException;
import com.nttdata.casestudy.dbfw.DBFWException;
import com.nttdata.casestudy.dbfw.DBHelper;
import com.nttdata.casestudy.dbfw.ParamMapper;
import com.nttdata.casestudy.model.User;
public class UserDAO {

	private DataSource ds = null;
	
	public UserDAO(DataSource ds) {
		this.ds = ds;
	}
	
	//Register a user to the site in case the user is new.
	public  void registerUser(User user)
	{


		try(Connection con1=ds.getConnection())
		{
			final ParamMapper INSERTUSER=new ParamMapper()
			{
				public void mapParam(PreparedStatement statement) throws SQLException 
				{
					statement.setString(1, user.getUserId());
					statement.setString(2, user.getPassword());
					statement.setString(3, user.getName());
					statement.setString(4, user.getEmail());
					statement.setDate(5, user.getDob());
					statement.setString(6, user.getPhoneNum());
					statement.setString(7, user.getAddress());
					statement.setInt(8, user.getPinCode());
					statement.setString(9, user.getRole());
				}
			};
			DBHelper.executeUpdate(con1,SQLMapper.registerUser,INSERTUSER);

			//				String sql="insert into user values(?,?,?,?,?,?,?,?,?)";
			//				PreparedStatement statement=con1.prepareStatement(sql);
			//				
			//				statement.setString(1, user.getUserId());
			//				statement.setString(2, user.getPassword());
			//				statement.setString(3, user.getName());
			//				statement.setString(4, user.getEmail());
			//				statement.setDate(5, user.getDob());
			//				statement.setString(6, user.getPhoneNum());
			//				statement.setString(7, user.getAddress());
			//				statement.setInt(8, user.getPinCode());
			//				statement.setString(9, user.getRole());
			//				statement.executeUpdate();

		}	
		catch(SQLException ex)
		{
			System.out.println(ex);
		}

	}


	//check whether the entered User Id and Password are legit credentials
	public  User authenticate(String userId,String password)
	{
		User user = new User();

		try(Connection con1=ds.getConnection();)
		{
			List<User> users=new ArrayList<User>();

			final ParamMapper userProfileMapper=new ParamMapper()
			{
				public void mapParam(PreparedStatement preStmt) throws SQLException {
					preStmt.setString(1, userId);
					preStmt.setString(2,password);
				}

			};

			users = DBHelper.executeSelect(con1,
					SQLMapper.GET_USER_PROFILE, 
					SQLMapper.getUserProfile, 
					userProfileMapper);
			if (users == null || users.size() == 0) {
				return user;
			}else{
				user = users.get(0);
			}
		}catch (DBFWException e) {
			throw new DBFWException("Exception in DBFW : " + e);
		}catch (SQLException se) {
			throw new DBConnectionException("Unable to connect to db"+se);
		}

		return user;
	}

}
