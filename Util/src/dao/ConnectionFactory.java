package dao;

import java.sql.Connection;
import java.sql.SQLException;
import connection.Connections;;


public abstract class ConnectionFactory<V> {
	public Connection getConnection(V connName) throws ClassNotFoundException {
		try {
			Connection 	conn = Connections.getConn((String) connName);
			conn.setAutoCommit(false);
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}