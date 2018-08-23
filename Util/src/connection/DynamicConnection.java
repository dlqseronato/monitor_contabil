package connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * 
 * Classe construída a partir da classe Connection, que possui uma Flag
 * indicando se a conexao esta ou nao sendo utilizada.
 * 
 * @author G0030353 - Rhuan Pablo Ribeiro Krum
 * @since 04/07/2013
 */
public class DynamicConnection{
	
	private Connection conn;
	private Boolean used;
	private String connectionName;
	
	public DynamicConnection(){
		super();
		used = false;
	}
	
	public DynamicConnection(String connectionName) throws SQLException{
		super();
		used = false;
		this.connectionName = connectionName;
		conn = Connections.getConn(connectionName);
	}
	
	public Boolean isUsed() {
		return used;
	}
	
	public synchronized Boolean isUsable() {
		if(!used){
			if(!isValid()){
				recoverConnection();
				return false;
			}
			else{
				used = true;
				return true;
			}
		}
		else return false;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public Connection getConn() {
		return conn;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	// Calls the Connection's prepareStatement method
	public PreparedStatement prepareStatement(String sql) throws SQLException{
		return this.conn.prepareStatement(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException{
		return this.conn.prepareCall(sql);
	}
	
	/**
	 * Metodo para validar se uma conexao e valida.
	 * 
	 * @author G0030353 - Rhuan Pablo Ribeiro Krum
	 * @since 26/11/2014
	 */
	public boolean isValid() {
		String sql = null;
		Statement stmt = null;
		boolean valid = false;
		
		try {
			sql = "select 1 from dual";
			stmt = conn.createStatement();
			valid = (stmt.execute(sql));
			stmt.close();
		} catch (SQLException e) {
			valid = false;
		} catch (Exception e) {
			valid = false;
		} 
		return valid;
	}

	/**
	 * Metodo para recuperar uma conexao invalida, limitando-se a 1000 tentativas.
	 * Uma excecao e gerada caso a conexao nao seja recuperada.
	 * 
	 * @author G0030353 - Rhuan Pablo Ribeiro Krum
	 * @since 26/11/2014
	 * @return boolean recovered
	 */
	public boolean recoverConnection(){
		System.out.println("Conexao " + connectionName + " nao e valida. Iniciando tentativa de recuperacao");

		int tries = 0;
		boolean recovered = false;
		
		while(!recovered && ++tries < 1000){
			try{
				if(!conn.isClosed()){
					conn.close();
				}
				
				Thread.sleep(200);
				
				if(conn.isClosed()){
					conn = Connections.getConn(connectionName);
					recovered = isValid();
				}
			} catch (SQLException e){
				System.out.println("Falha ao recuperar conexao com a base " );
			} catch (Exception e){
				System.out.println("Falha ao recuperar conexao com a base " );
			}
		}
		
		if(tries == 1000 && !recovered){
			System.out.println("Falha ao recuperar conexao com a base " + connectionName);
		}
		else{
			System.out.println("Conexao " + connectionName + " recuparada na tentativa de numero " + tries);
		}
		
		return recovered;
	}
	
	public void close(){
		Connections.close(conn);
	}
}
