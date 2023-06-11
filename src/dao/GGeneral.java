package dao;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Abstract class for the connectors.
 * 
 * @author Daniel Serrano Rodriguez
 */
public abstract class GGeneral {
	protected String table;
	protected Statement stmt;
	protected Connection conn;
}
