package com.proativo.cenario.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.proativo.cenario.vo.ErroVo;
import com.proativo.util.QueryWarehouse;
import com.proativo.util.connection.Connections;
import com.proativo.util.connection.DynamicConnection;
import com.proativo.util.dao.OraUtilProativo;
import com.proativo.util.log.Log;

public class OraProativo extends OraUtilProativo {

	public String buscarComponents() {
		return this.getListaElementos("proativoBuscarComponents", Connections.CONN_PROATIVO);
	}

	public void proativoInsereRejeitados(ErroVo ob, DynamicConnection dc, int idExecucao) {
		PreparedStatement pst = null;
		String sql = null;	
		
		try {
			sql = QueryWarehouse.getQuery("proativoInsereRejeitados");
			pst = dc.prepareStatement(sql);
			pst.setInt(1, ob.getJnlsCodeId());
			pst.setInt(2, ob.getIdProdutoKenan());
			pst.setInt(3, ob.getUseCode());
			pst.setInt(4, ob.getIdType());
			pst.setInt(5, ob.getAccountCategory());
			pst.setInt(6, ob.getOpenItemId());
			/*
			pst.setInt(7, ob.getcContabilDebitoFaturada());
			pst.setInt(8, ob.getJnlsCContabilDebitoFaturada());
			pst.setInt(9, ob.getcContabilCreditoFaturada());
			pst.setInt(10, ob.getJnlsCContabilCreditoFaturada());
			*/
			pst.setString(7, ob.getMsgError());
			pst.setInt(8, ob.getErrorType());
			pst.setInt(9, idExecucao);
			pst.setInt(10, ob.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			Log.error("Falha ao Inserir Tabela Rejeitados. ", e);
		} catch (Exception e) {
			Log.error("Falha ao Inserir Tabela Rejeitados. ", e);
		} finally{
			close(pst);
			dc.setUsed(false);
		}		
	}
	
	public void proativoInsereControleExecucao(int idExecucao, int qtdRejeitados,  String lote) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection dc = null;
		String sql = null;	
		
		try {
			sql = QueryWarehouse.getQuery("proativoInsereControleExecucao");
			dc = Connections.getConn(Connections.CONN_PROATIVO);
			pst = dc.prepareStatement(sql);
			pst.setInt(1,idExecucao);
			pst.setString(2, lote);
			pst.setInt(3, qtdRejeitados);
			pst.executeUpdate();
		} catch (SQLException e) {
			Log.error("Falha ao Inserir Controle Execucao. ", e);
		} catch (Exception e) {
			Log.error("Falha ao Inserir Controle Execucao. ", e);
		} finally{
			close(pst,dc,rs);
		}		
	}

	

}
