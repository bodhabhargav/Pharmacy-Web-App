package com.nttdata.casestudy.dbfw;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParamMapper 
{
	void mapParam(PreparedStatement preStmt) throws SQLException;
}
