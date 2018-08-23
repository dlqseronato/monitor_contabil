package connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DBPoolManager
{
	private static Map<String, ApacheDBCPConnectionManager>	dbPools	= null;

	private static DBPoolManager	instance;

	private DBPoolManager() throws DBPoolConfigFileFormatException, ClassNotFoundException {
		reloadConfig();
	}

	public static DBPoolManager getInstance() throws DBPoolConfigFileFormatException, ClassNotFoundException{
		if (instance == null) 
			instance = new DBPoolManager();
		return instance;
	}

	public void reloadConfig() throws DBPoolConfigFileFormatException, ClassNotFoundException{
		
		dbPools = new HashMap<String, ApacheDBCPConnectionManager>(0);

		DBPoolConfigFileManager configFileMan = new DBPoolConfigFileManager();

		Class.forName("oracle.jdbc.driver.OracleDriver");

		Iterator<DBPoolConfigBean> configs = configFileMan.loadPoolConfig();

		while (configs.hasNext())	{
			DBPoolConfigBean configBean = (DBPoolConfigBean) configs.next();
			String poolName1 = configBean.getPoolName() + "-1";

			ApacheDBCPConnectionManager manager =
						new ApacheDBCPConnectionManager(configBean.getUrl1(), configBean.getUserName(), configBean
									.getPassword(), 0, configBean.getMaxSize(), configBean.getValidationQuery());

			dbPools.put(poolName1, manager);

//			Log.infoDbPool("OK!");

			// Se a URL2 foi informada, então cria um "Pool_" de contingêngia.
			if (configBean.getUrl2() != null)
			{
				String poolName2 = configBean.getPoolName() + "-2";
//				Log.infoDbPool("Criando DataSource 2 " + poolName2 + " ...");
//				Log.infoDbPool("[" + poolName2 + "] URL        : " + configBean.getUrl2());
//				Log.infoDbPool("[" + poolName2 + "] UserName   : " + configBean.getUserName());
//				Log.infoDbPool("[" + poolName2 + "] MaxSize    : " + configBean.getMaxSize());

				manager =
							new ApacheDBCPConnectionManager(configBean.getUrl2(), configBean.getUserName(), configBean
										.getPassword(), 0, configBean.getMaxSize(), configBean.getValidationQuery());

				dbPools.put(poolName2, manager);
			}
		}
	}

	/**
	 * Retorna uma conexão do Pool de Conexão.
	 * @param poolName
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(String poolName) throws SQLException {
		String poolName1 = poolName + "-1";
		String poolName2 = poolName + "-2";

		ApacheDBCPConnectionManager manager = (ApacheDBCPConnectionManager) dbPools.get(poolName1);		

		Connection conn = null;
		try	{
			testConnection(poolName1);
			conn = manager.getDataSource().getConnection();
		} catch (SQLException sqlEx)	{
			try{
				System.out.println("[" + poolName1 + "] URL1 falhou!");

				System.out.println(sqlEx.getMessage());

				System.out.println("[" + poolName2 + "] Tentando URL2 ...");
				// A tentativa de conexão com a URL1 falhou,
				// agora tenta utilizar a URL2.
				// Se falhar agora, lança SQLException
				manager = (ApacheDBCPConnectionManager) dbPools.get(poolName2);
				conn = manager.getDataSource().getConnection();
			} catch(Exception ex){
				throw sqlEx;
			}			
		}
		return conn;
	}

	private synchronized void testConnection(String poolName) {
		ApacheDBCPConnectionManager manager = (ApacheDBCPConnectionManager) dbPools.get(poolName);
		Connection connTeste = null;
		try {
			connTeste = manager.getDataSource().getConnection();
		} catch (SQLException e) {
			
			if(e.getMessage().toLowerCase().contains("ora-01017")){
				System.out.println("Erro de usuario/senha. Favor verificar configuração do arquivo DBPOOL: ");
				System.out.println("Aplicacao sera finalizada");
				System.exit(-1);
			}
			else if(e.getMessage().toLowerCase().contains("ora-12514")){
				System.out.println("A string de conexão com o banco está incorreta. Favor verificar junto aos DBAs: ");
				System.out.println("Aplicacao sera finalizada");
				System.exit(-1);
			}
			else if(e.getMessage().toLowerCase().contains("ora-12528")){
				System.out.println("O banco de dados rejeitou abertura de novas conexões. Favor verificar junto aos DBAs: " );
				System.out.println("Aplicacao sera finalizada");
				System.exit(-1);
			}
			else if(e.getMessage().toLowerCase().contains("ora-00028")){
				System.out.println("A sessão da base foi derrubada. Favor verificar junto aos DBAs: ");
				System.out.println("Aplicacao sera finalizada");
				System.exit(-1);
			}
			
			System.out.println("Erro ao testar a conexao: ");
		} finally {
			try {
				if(connTeste != null) connTeste.close();
			} catch (Exception e){	
				System.out.println("Erro ao fechar a conexao apos o teste: ");
			}
		}
	}
	
	public List<String> listarDbpoolsCarregados (){
		List<String> listaDbpools = new ArrayList<String>();
		if(dbPools != null){
			for(String dbpool : dbPools.keySet()){
				listaDbpools.add(dbpool.replace("-1", "").replace("-2", ""));
			}
			
			return listaDbpools;
		}
		else{
			return null;
		}
	}
}