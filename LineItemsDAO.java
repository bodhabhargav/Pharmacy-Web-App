package com.caseStudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.dbfw.DBHelper;
import com.nttdata.casestudy.dbfw.ParamMapper;
import com.nttdata.casestudy.model.LineItem;

public class LineItemsDAO {
	
static  DataSource ds = null;
	
	public LineItemsDAO(DataSource ds) {
		this.ds = ds;		
	}


	public void createLineItem(LineItem item, String userId)
	{
		try(Connection con1=ds.getConnection())
		{
			final ParamMapper orderParamMapper=new ParamMapper()
			{

				public void mapParam(PreparedStatement preStmt) throws SQLException {
					preStmt.setString(1,userId);
				}

			};

			List<Integer> li = DBHelper.executeSelect(con1,
					SQLMapper.getMaxOrderID,
					SQLMapper.retrieveMaxOrderID,
					orderParamMapper);

			final int maxOrderId =  Integer.parseInt( ""+li.get(0));


			final ParamMapper createLineItems=new ParamMapper()
			{
				@Override
				public void mapParam(PreparedStatement statement) throws SQLException {
					statement.setInt(1, item.getProductID());
					statement.setInt(2, item.getQuantity());
					statement.setInt(3,maxOrderId);

				}

			};

			DBHelper.executeUpdate(con1, SQLMapper.insertIntoLineItems, createLineItems);
		}
		catch(SQLException ex)
		{
			System.out.println(ex);
		}
		System.out.println("Your order has been successfully placed");
	}
}
