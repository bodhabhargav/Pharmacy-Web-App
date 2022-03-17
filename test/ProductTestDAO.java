package com.nttdata.casestudy.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import com.caseStudy.dao.ProductDAO;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.nttdata.casestudy.model.Product;

public class ProductTestDAO {

	
	private DataSource getDataSource() {	        		        	
		MysqlDataSource ds1 = new MysqlDataSource();
		ds1.setURL("jdbc:mysql://localhost:3306/case_study");
		ds1.setUser("root");
		ds1.setPassword("1234");	            
		return ds1;
	}


	@Test
	public void displayAllTest() {
		ProductDAO dao = new ProductDAO(getDataSource());
		List<Product> products=dao.displayAllByCategory(1);
		assertEquals(products.size(), 3);
	}
	
	@Test
	public void getAllProductsTest()
	{
		ProductDAO dao = new ProductDAO(getDataSource());
		List<Product> products=dao.getAllProducts();
		assertEquals(products.size(), 10);
	}
	
	@Test
	public void Test()
	{
		ProductDAO dao = new ProductDAO(getDataSource());
		int price = dao.getPrice(6);
		
		assertEquals(price,85);
	}

}




//  package com.ntt.junit;
//import static org.junit.Assert.*;
//
//import org.junit.Before; 
//import org.junit.Test;
//
//import com.ntt.dao.BookDAO;
//import com.ntt.domain.Book;
//
//public class InsertBookTest {
//
//	BookDAO book; int Book;
//	Book b=new Book();
//
//	@Before public void initialize() 
//	{ 
//		book = new BookDAO(); 
//	}
//
//
//	public void InsertBookTester(int Book) { 
//		this.Book = Book;
//
//	}
//
//
//	@Test public void testInsert() {
//
//		int testresult=1;
//
//		testresult=book.bookInsertion(b); assertEquals(0,testresult);
//		//System.out.println(result);
//
//
//	}
//
//
//}


