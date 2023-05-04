package dao;

import java.sql.Connection;
import java.sql.Statement;

public abstract class GGeneral {
	protected String table;
	protected Statement stmt;
	protected Connection conn;
}
