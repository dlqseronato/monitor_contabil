package com.proativo.cenario.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proativo.cenario.vo.ErroVo;
import com.proativo.cenario.vo.ProdutoVerdadeVo;
import com.proativo.util.QueryWarehouse;
import com.proativo.util.connection.Connections;
import com.proativo.util.connection.DynamicConnection;
import com.proativo.util.dao.OraUtilProativo;
import com.proativo.util.log.Log;

public class OraProativo extends OraUtilProativo {

	public String buscarComponents() {
		return this.getListaElementos("proativoBuscarComponents", Connections.CONN_PROATIVO);
	}

	public void proativoInsereTabelaVerdade(ProdutoVerdadeVo ob, DynamicConnection dc) {
		PreparedStatement pst = null;
		String sql = null;	
		try {
			sql = QueryWarehouse.getQuery("proativoInsereTabelaVerdade");
			pst = dc.prepareStatement(sql);
			pst.setInt(1, ob.getId());
			pst.setInt(2, ob.getIdProdutoKenan());
			pst.setString(3, ob.getDescricaoProduto());
			pst.setString(4, ob.getProdutoVv());
			pst.setInt(5, ob.getOpenItemIdFaturada());
			pst.setString(6, ob.getEmpresa());
			pst.setString(7, ob.getDescricaoResumo());
			pst.setInt(8, ob.getcContabilDebitoFaturada());
			pst.setInt(9, ob.getcContabilCreditoFaturada());
			pst.setInt(10, ob.getcContabilDebitoVencimento());
			pst.setInt(11, ob.getcContabilCreditoVencimento());
			pst.setInt(12, ob.getAccountCategory());
			pst.setString(13, ob.getDescricaoAccountCategory());
			pst.setString(14, ob.getDivisaoFaturada());
			pst.setInt(15, ob.getMktCode());
			pst.setString(16, ob.getUf());
			pst.setString(17, ob.getEstado());
			pst.setString(18, ob.getOrdemInternaFaturada());
			pst.setString(19, ob.getCentroCustoFaturada());
			pst.setInt(20, ob.getcContabilDebitoAFaturar());
			pst.setInt(21, ob.getcContabilCreditoAFaturar());
			pst.setString(22, ob.getDivisaoAFaturar());
			pst.setString(23, ob.getOrdemInternaAFaturar());
			pst.setString(24, ob.getCentroCustoAFaturar());
			pst.setInt(25, ob.getOpenItemIdAFaturar());

			pst.setInt(26, ob.getcContabilAdjDebitoFaturada());
			pst.setInt(27, ob.getcContabilAdjCreditoFaturada());
			pst.setString(28, ob.getDivisaoAdjFaturada());
			pst.setString(29, ob.getOrdemInternaAdjFaturada());
			pst.setString(30, ob.getCentroCustoAdjFaturada());
			pst.setInt(31, ob.getOpenItemIdAdjFaturada());

			pst.setInt(32, ob.getcContabilAdjDebitoAFaturar());
			pst.setInt(33, ob.getcContabilAdjCreditoAFaturar());
			pst.setString(34, ob.getDivisaoAdjAFaturar());
			pst.setString(35, ob.getOrdemInternaAdjAFaturar());
			pst.setString(36, ob.getCentroCustoAdjAFaturar());
			pst.setInt(37, ob.getOpenItemIdAdjAFaturar());

			pst.executeUpdate();
		} catch (SQLException e) {
			Log.error("Falha ao Inserir Tabela Verdade. ", e);
		} catch (Exception e) {
			Log.error("Falha ao Inserir Tabela Verdade. ", e);
		} finally{
			close(pst);
			dc.setUsed(false);
		}		
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
			pst.setDate(4, new Date(System.currentTimeMillis()));
			pst.executeUpdate();
		} catch (SQLException e) {
			Log.error("Falha ao Inserir Controle Execucao. ", e);
		} catch (Exception e) {
			Log.error("Falha ao Inserir Controle Execucao. ", e);
		} finally{
			close(pst,dc,rs);
		}		
	}

	public void proativoAtualizaTabelaVerdade(ProdutoVerdadeVo ob, DynamicConnection dc) {
		PreparedStatement pst = null;
		String sql = null;	
		try {
			sql = QueryWarehouse.getQuery("proativoAtualizaTabelaVerdade");
			pst = dc.prepareStatement(sql);
			pst.setInt(1, ob.getcContabilDebitoFaturada());
			pst.setInt(2, ob.getcContabilCreditoFaturada());
			pst.setInt(3, ob.getcContabilDebitoAFaturar());
			pst.setInt(4, ob.getcContabilCreditoAFaturar());
			pst.setInt(5, ob.getcContabilAdjDebitoFaturada());
			pst.setInt(6, ob.getcContabilAdjCreditoFaturada());
			pst.setInt(7, ob.getcContabilAdjDebitoAFaturar());
			pst.setInt(8, ob.getcContabilAdjCreditoAFaturar());
			pst.setInt(9, ob.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			Log.error("Falha ao atualizar Tabela Verdade. ", e);
		} catch (Exception e) {
			Log.error("Falha ao atualizar Tabela Verdade. ", e);
		} finally{
			close(pst);
			dc.setUsed(false);
		}


	}


	public List<ProdutoVerdadeVo> proativoBuscarTabelaVerdade() {
		ProdutoVerdadeVo ob ;
		List<ProdutoVerdadeVo> lob = new ArrayList<ProdutoVerdadeVo>();
		Connection dc = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;	
		try {
			sql = QueryWarehouse.getQuery("proativoBuscarTabelaVerdade");
			dc = Connections.getConn(Connections.CONN_PROATIVO);
			pst = dc.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				ob = new ProdutoVerdadeVo();
				ob.setId(rs.getInt("ID"));
				ob.setIdProdutoKenan(rs.getInt("ID_PRODUTO_KENAN"));
				ob.setDescricaoProduto(rs.getString("DESCR_PRODUTO"));
				ob.setProdutoVv(rs.getString("PRODUTO_VV"));
				ob.setOpenItemIdFaturada(rs.getInt("OPEN_ITEM_ID"));
				ob.setEmpresa(rs.getString("EMPRESA"));
				ob.setDescricaoResumo(rs.getString("DESCR_RESUMO"));
				ob.setcContabilDebitoFaturada(rs.getInt("FAT_CC_DEBITO"));
				ob.setcContabilCreditoFaturada(rs.getInt("FAT_CC_CREDITO"));
				ob.setcContabilDebitoVencimento(rs.getInt("VV_CC_DEBITO"));
				ob.setcContabilCreditoVencimento(rs.getInt("VV_CC_CREDITO"));
				ob.setAccountCategory(rs.getInt("ACCOUNT_CATEGORY"));
				ob.setDescricaoAccountCategory(rs.getString("DESCR_ACCOUNT_CATEGORY"));
				ob.setDivisaoFaturada(rs.getString("DIVISAO"));
				ob.setMktCode(rs.getInt("MKT_CODE"));
				ob.setUf(rs.getString("UF"));
				ob.setEstado(rs.getString("ESTADO"));
				ob.setOrdemInternaFaturada(rs.getString("ORDEM_INTERNA"));
				ob.setCentroCustoFaturada(rs.getString("CENTRO_CUSTO"));
				ob.setcContabilDebitoAFaturar(rs.getInt("A_FAT_CC_DEBITO"));
				ob.setcContabilCreditoAFaturar(rs.getInt("A_FAT_CC_CREDITO"));
				ob.setDivisaoAFaturar(rs.getString("A_FAT_DIVISAO"));
				ob.setOrdemInternaAFaturar(rs.getString("AFAT_ORDEM_INTERNA"));
				ob.setCentroCustoAFaturar(rs.getString("AFAT_CENTRO_CUSTO"));
				ob.setOpenItemIdAFaturar(rs.getInt("AFAT_OPEN_ITEM_ID"));

				ob.setcContabilAdjDebitoFaturada(rs.getInt("FAT_ADJ_CC_DEBITO"));
				ob.setcContabilAdjCreditoFaturada(rs.getInt("FAT_ADJ_CC_CREDITO"));
				ob.setDivisaoAdjFaturada(rs.getString("FAT_ADJ_DIVISAO"));
				ob.setOrdemInternaAdjFaturada(rs.getString("FAT_ADJ_ORDEM_INTERNA"));
				ob.setCentroCustoAdjFaturada(rs.getString("FAT_ADJ_CENTRO_CUSTO"));
				ob.setOpenItemIdAdjFaturada(rs.getInt("FAT_ADJ_OPEN_ITEM_ID"));

				ob.setcContabilAdjDebitoAFaturar(rs.getInt("A_FAT_ADJ_CC_DEBITO"));
				ob.setcContabilAdjCreditoAFaturar(rs.getInt("A_FAT_ADJ_CC_CREDITO"));
				ob.setDivisaoAdjAFaturar(rs.getString("A_FAT_ADJ_DIVISAO"));
				ob.setOrdemInternaAdjAFaturar(rs.getString("A_FAT_ADJ_ORDEM_INTERNA"));
				ob.setCentroCustoAdjAFaturar(rs.getString("A_FAT_ADJ_CENTRO_CUSTO"));
				ob.setOpenItemIdAdjAFaturar(rs.getInt("A_FAT_ADJ_OPEN_ITEM_ID"));	

				lob.add(ob);
			}
			return lob;

		} catch (SQLException e) {
			Log.error("Falha ao buscar Tabela Verdade. ", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar Tabela Verdade. ", e);
		} finally{
			close(pst,dc);
		}
		return null;
	}

	public void proativoLimparTabelaVerdade() {
		Connection dc = null;
		PreparedStatement pst = null;
		String sql = null;	
		try {
			sql = QueryWarehouse.getQuery("proativoLimparTabelaVerdade");
			dc = Connections.getConn(Connections.CONN_PROATIVO);
			pst = dc.prepareStatement(sql);
			pst.executeUpdate();
		} catch (SQLException e) {
			Log.error("Falha ao limpar Tabela Verdade. ", e);
		} catch (Exception e) {
			Log.error("Falha ao limpar Tabela Verdade. ", e);
		} finally{
			close(pst,dc);
		}
	}

}
