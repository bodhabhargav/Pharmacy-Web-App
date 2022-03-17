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
import com.nttdata.casestudy.model.Product;

public class ProductDAO {
	
	static  DataSource ds = null;
	
	public ProductDAO(DataSource ds) {
		this.ds = ds;		
	}

	public List<Product> displayAllByCategory(int categoryVar) 
	{

		List<Product> products=new ArrayList<Product>();
		try(Connection con1=ds.getConnection();)
		{

			final ParamMapper prodParamMapper=new ParamMapper()
			{

				public void mapParam(PreparedStatement preStmt) throws SQLException {
					preStmt.setInt(1,categoryVar);
				}

			};

			products=DBHelper.executeSelect(con1,
					SQLMapper.GET_ALL_PRODUCTS_By_CATEGORY, 
					SQLMapper.getAllProductsMapper, 
					prodParamMapper);
		}catch (DBFWException e) {
			throw new DBFWException("Exception in DBFW : " + e);
		}catch (SQLException se) {
			throw new DBConnectionException("Unable to connect to db"+se);
		}

		return products;
	}


	public int getPrice(int productID)
	{
		int price=0;
		try(Connection con1=ds.getConnection();)
		{
			final ParamMapper prodParamMapper=new ParamMapper()
			{

				public void mapParam(PreparedStatement preStmt) throws SQLException {
					preStmt.setInt(1,productID);
				}

			};

			List<Integer> l1 = DBHelper.executeSelect(con1,
					SQLMapper.GET_PRICE_BY_PROD_ID, 
					SQLMapper.getPriceByProdIdMapper, 
					prodParamMapper);
			price = l1.get(0);
		}catch (DBFWException e) {
			throw new DBFWException("Exception in DBFW : " + e);
		}catch (SQLException se) {
			throw new DBConnectionException("Unable to connect to db"+se);
		}
		return price;
	}

	public List<Product> getAllProducts()
	{
		List<Product> products=new ArrayList<Product>();
		try(Connection con1=ds.getConnection();)
		{

			//			final ParamMapper prodParamMapper=new ParamMapper()
			//			{
			//
			//				public void mapParam(PreparedStatement preStmt) throws SQLException {
			//					preStmt.setInt(1,categoryVar);
			//				}
			//				
			//			};

			products=DBHelper.executeSelect(con1,
					SQLMapper.GET_ALL_PRODUCTS, 
					SQLMapper.getAllProductsMapper);
		}catch (DBFWException e) {
			throw new DBFWException("Exception in DBFW : " + e);
		}catch (SQLException se) {
			throw new DBConnectionException("Unable to connect to db"+se);
		}

		return products;
	}
}
