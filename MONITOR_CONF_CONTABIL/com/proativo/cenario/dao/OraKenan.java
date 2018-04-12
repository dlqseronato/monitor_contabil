package com.proativo.cenario.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proativo.cenario.vo.CentroDeCustoVo;
import com.proativo.cenario.vo.ContDetVo;
import com.proativo.cenario.vo.DivisaoVo;
import com.proativo.cenario.vo.LoteVo;
import com.proativo.cenario.vo.OrdemInternaVo;
import com.proativo.cenario.vo.ProdutoSAPVo;
import com.proativo.cenario.vo.ProdutoVerdadeVo;
import com.proativo.util.QueryWarehouse;
import com.proativo.util.connection.Connections;
import com.proativo.util.connection.DynamicConnection;
import com.proativo.util.dao.OraUtilKenan;
import com.proativo.util.log.Log;

public class OraKenan extends OraUtilKenan{

	//Atualiza contas contabeis nulas na config
	public ProdutoVerdadeVo kenanbuscaCContabil(ProdutoVerdadeVo ob,DynamicConnection dc) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		ProdutoVerdadeVo newOb = ob;
		try {
			sql = QueryWarehouse.getQuery("kenanBuscaCContabil");
			pst = dc.prepareStatement(sql);
			pst.setInt(1, newOb.getIdProdutoKenan());
			pst.setInt(2, newOb.getAccountCategory());
			pst.setInt(3, newOb.getAccountCategory());
			pst.setInt(4, newOb.getIdProdutoKenan());
			pst.setInt(5, newOb.getAccountCategory());
			pst.setInt(6, newOb.getAccountCategory());
			rs = pst.executeQuery();

			//System.out.println("ID: "+ob.getId()+" ANTES : Alterando CC "+newOb.getIdProdutoKenan() +" " + newOb.getcContabilDebitoFaturada() + " "+ newOb.getcContabilCreditoFaturada()  + " " +newOb.getcContabilDebitoAFaturar()+" "+newOb.getcContabilCreditoAFaturar());

			if(rs.next()){
				newOb.setJnlsCodeId(rs.getInt("JNL_CODE_ID"));
				if(newOb.getcContabilDebitoFaturada() == 0 || newOb.getcContabilDebitoFaturada() == -1) {
					newOb.setcContabilDebitoFaturada(rs.getInt("FML_ACCT_DB_FATURADA"));
				}
				if(newOb.getcContabilCreditoFaturada() == 0 || newOb.getcContabilCreditoFaturada() == -1) {
					newOb.setcContabilCreditoFaturada(rs.getInt("FML_ACCT_CR_FATURADA"));
				}
				if(newOb.getcContabilDebitoAFaturar() == 0 || newOb.getcContabilDebitoAFaturar() == -1) {
					newOb.setcContabilDebitoAFaturar(rs.getInt("FML_ACCT_DB_AFATURAR"));
				}
				if(newOb.getcContabilCreditoAFaturar() == 0 || newOb.getcContabilCreditoAFaturar() == -1) {
					newOb.setcContabilCreditoAFaturar(rs.getInt("FML_ACCT_CR_AFATURAR"));
				}

				//System.out.println("ID: "+ob.getId()+" DEPOIS : Alterando CC "+newOb.getIdProdutoKenan() +" " + newOb.getcContabilDebitoFaturada() + " "+ newOb.getcContabilCreditoFaturada()  + " " +newOb.getcContabilDebitoAFaturar()+" "+newOb.getcContabilCreditoAFaturar());
			}
		} catch (SQLException e) {
			Log.error("Falha ao buscar conta contábil.", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar conta contábil.", e);
		} finally{
			close(rs, pst);
			dc.setUsed(false);
		}
		return newOb;
	}



	public LoteVo kenanBuscarLotesJnls() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection dc = null;
		String sql = null;
		LoteVo newOb = null;
		try {
			sql = QueryWarehouse.getQuery("kenanBuscarLotesJnls");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);			
			rs = pst.executeQuery();
			while(rs.next()){
				newOb = new LoteVo(	rs.getString("LOTE"), 
						rs.getString("PROCESSAMENTO"), 
						rs.getString("COMPETENCIA"), 
						rs.getInt("JNL_REF_NO"), 
						rs.getInt("JNL_REF_NO_SERV"));
			}
		} catch (SQLException e) {
			Log.error("Falha ao buscar LOTE NA JOURNALS.", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar LOTE NA JOURNALS.", e);
		} finally{
			close(rs, pst, dc);
		}
		return newOb;
	}




	public List<DivisaoVo> kenanBuscarDivisoes() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection dc = null;
		String sql = null;
		DivisaoVo newOb = null;
		List<DivisaoVo> list = new ArrayList<DivisaoVo>();
		try {
			sql = QueryWarehouse.getQuery("kenanBuscaDivisoes");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);			
			rs = pst.executeQuery();
			while(rs.next()){
				newOb = new DivisaoVo(	
						rs.getInt("MKT_CODE"),
						rs.getString("DESCRICAO"), 
						rs.getString("DIVISAO")
						);
				list.add(newOb);
			}
		} catch (SQLException e) {
			Log.error("Falha ao buscar DIVISAO.", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar DIVISAO.", e);
		} finally{
			close(rs, pst, dc);
		}
		return list;
	}
	
	

	public int kenanBuscaContDetQtdPaginas(String lote) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection dc = null;
		String sql = null;
		try {
			sql = QueryWarehouse.getQuery("kenanBuscaContDetQtdPaginas");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);
			pst.setString(1, lote);
			rs = pst.executeQuery();
			if(rs.next()){
				return rs.getInt("QTD_PAGINAS");
			}
		} catch (SQLException e) {
			Log.error("Falha ao buscar DIVISAO.", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar DIVISAO.", e);
		} finally{
			close(rs, pst, dc);
		}
		return 0;
	}
	public List<CentroDeCustoVo> kenanBuscaCentroDeCusto() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection dc = null;
		String sql = null;
		List<CentroDeCustoVo> list = new ArrayList<CentroDeCustoVo>();
		CentroDeCustoVo ob;
		try {
			sql = QueryWarehouse.getQuery("kenanBuscaCentroDeCusto");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				ob = new CentroDeCustoVo(rs.getString(1),
										 rs.getInt(2));
				list.add(ob);
			}
		} catch (SQLException e) {
			Log.error("Falha ao buscar DIVISAO.", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar DIVISAO.", e);
		} finally{
			close(rs, pst, dc);
		}
		return list;
	}
	
	public List<OrdemInternaVo> kenanBuscaOrdemInterna() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection dc = null;
		String sql = null;
		List<OrdemInternaVo> list = new ArrayList<OrdemInternaVo>();
		OrdemInternaVo ob;
		try {
			sql = QueryWarehouse.getQuery("kenanBuscaOrdemInterna");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				ob = new OrdemInternaVo(rs.getInt("ELEMENT"),
										 rs.getInt("JNL_ID_TYPE"),
										 rs.getString("ORDEM_INTERNA"));
				list.add(ob);
			}
		} catch (SQLException e) {
			Log.error("Falha ao buscar ORDEM INTERNA.", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar ORDEM INTERNA.", e);
		} finally{
			close(rs, pst, dc);
		}
		return list;
	}


	public List<ContDetVo> kenanBuscarContDet(String lote, int paginacao) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection dc = null;
		int cont = 0;
		String sql = null;
		ContDetVo ob  = null;
		List<ContDetVo> ObList = new ArrayList<ContDetVo>();

		try {
			sql = QueryWarehouse.getQuery("kenanBuscaContDet");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);
			pst.setString(1, lote);
			pst.setInt(2, paginacao);
			pst.setInt(3, paginacao);
			rs = pst.executeQuery();
			while(rs.next()){
				cont++;	
				ob = new ContDetVo(		cont,
						rs.getInt("EMPRESA_MKT_CODE"),
						rs.getDate("DATA_DOCUMENTO"), 
						rs.getInt("TIPO_LANCAMENTO"), 
						rs.getInt("ID_TYPE2"), 
						rs.getInt("CONTA_CONTABIL_CR"), 
						rs.getInt("CONTA_CONTABIL_DB"), 
						rs.getInt("ACCOUNT_NO"), 
						rs.getString("EXTERNAL_ID"), 
						rs.getInt("ACCOUNT_CATEGORY"), 
						rs.getInt("OPEN_ITEM_ID"),
						rs.getString("COD_ATRIBUICAO"), 
						rs.getString("DIVISAO"), 
						rs.getString("CENTRO_CUSTO"), 
						rs.getInt("ELEMENT"), 
						rs.getString("ORDEM_INTERNA"),
						rs.getString("TECNOLOGIA_OFICIAL")
						);
				ObList.add(ob);
			}
		} catch (SQLException e) {
			Log.error("Falha ao buscar cont det.", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar conta contábil.", e);
		} finally{
			close(rs, pst, dc);
		}
		return ObList;
	}


	//Atualiza contas contabeis nulas na config
	public ProdutoVerdadeVo kenanbuscaCContabilAdj(ProdutoVerdadeVo ob,DynamicConnection dc) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		ProdutoVerdadeVo newOb = ob;
		try {

			sql = QueryWarehouse.getQuery("kenanBuscaCContabilAdj");
			pst = dc.prepareStatement(sql);
			pst.setInt(1, newOb.getIdProdutoKenan());
			pst.setInt(2, newOb.getAccountCategory());
			pst.setInt(3, newOb.getAccountCategory());
			pst.setInt(4, newOb.getIdProdutoKenan());
			pst.setInt(5, newOb.getAccountCategory());
			pst.setInt(6, newOb.getAccountCategory());
			rs = pst.executeQuery();
			//System.out.println("ID: "+ob.getId()+" ANTES AJUSTE : Alterando CC "+newOb.getIdProdutoKenan() +" " + newOb.getcContabilAdjDebitoFaturada() + " "+ newOb.getcContabilAdjCreditoFaturada()  + " " +newOb.getcContabilAdjDebitoAFaturar()+" "+newOb.getcContabilAdjCreditoAFaturar());
			if(rs.next()){
				newOb.setJnlsCodeId(rs.getInt("JNL_CODE_ID"));

				if(newOb.getcContabilAdjDebitoFaturada() == 0 || newOb.getcContabilAdjDebitoFaturada() == -1) {
					newOb.setcContabilAdjDebitoFaturada(rs.getInt("FML_ACCT_DB_FATURADA"));
				}
				if(newOb.getcContabilAdjCreditoFaturada() == 0 || newOb.getcContabilAdjCreditoFaturada() == -1) {
					newOb.setcContabilAdjCreditoFaturada(rs.getInt("FML_ACCT_CR_FATURADA"));
				}
				if(newOb.getcContabilAdjDebitoAFaturar() == 0 || newOb.getcContabilAdjDebitoAFaturar() == -1) {
					newOb.setcContabilAdjDebitoAFaturar(rs.getInt("FML_ACCT_DB_AFATURAR"));
				}
				if(newOb.getcContabilAdjCreditoAFaturar() == 0 || newOb.getcContabilAdjCreditoAFaturar() == -1) {
					newOb.setcContabilAdjCreditoAFaturar(rs.getInt("FML_ACCT_CR_AFATURAR"));
				}

				//System.out.println("ID: "+ob.getId()+" DEPOIS AJUSTE : Alterando CC "+newOb.getIdProdutoKenan() +" " + newOb.getcContabilAdjDebitoFaturada() + " "+ newOb.getcContabilAdjCreditoFaturada()  + " " +newOb.getcContabilAdjDebitoAFaturar()+" "+newOb.getcContabilAdjCreditoAFaturar());
			}
		} catch (SQLException e) {
			Log.error("Falha ao buscar conta contábil.", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar conta contábil.", e);
		} finally{
			close(rs, pst);
			dc.setUsed(false);
		}
		return newOb;
	}


	public void kenanInsereTabelaVerdade(ProdutoVerdadeVo ob, DynamicConnection dc) {
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

	public void kenanInsereTabelaSAP(ProdutoVerdadeVo ob, DynamicConnection dc) {
		PreparedStatement pst = null;
		String sql = null;	
		try {
			sql = QueryWarehouse.getQuery("proativoInsereTabelaSAP");
			pst = dc.prepareStatement(sql);
			pst.setInt(1, ob.getIdProdutoKenan());
			pst.setString(2, ob.getDescricaoProduto());


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


	public void kenanAtualizaTabelaVerdade(ProdutoVerdadeVo ob, DynamicConnection dc) {
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


	public List<ProdutoVerdadeVo> kenanBuscarTabelaVerdade() {
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

	public List<ProdutoSAPVo> kenanBuscaConfiguracoesJnls() {
		ProdutoSAPVo ob ;
		List<ProdutoSAPVo> lob = new ArrayList<ProdutoSAPVo>();
		Connection dc = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;	
		try {
			sql = QueryWarehouse.getQuery("kenanBuscaConfiguracoesJnls");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				ob = new ProdutoSAPVo();
				ob.setJnlsCodeId(rs.getInt("JNL_CODE_ID"));
				ob.setJnlsAccountCategory(rs.getInt("ACCOUNT_CATEGORY"));
				ob.setJnlsIdType(rs.getInt("ID_TYPE"));
				ob.setJnlsIdType2(rs.getInt("ID_TYPE2"));
				ob.setJnlsIdValue(rs.getInt("ID_VALUE"));
				ob.setJnlsIdValue2(rs.getInt("ID_VALUE2"));
				ob.setJnlsUseCode(rs.getInt("USE_CODE"));
				ob.setJnlsOpenItemId(rs.getInt("OPEN_ITEM_ID"));
				ob.setJnlsTaxTypeCode(rs.getInt("TAX_TYPE_CODE"));
				ob.setJnlsIndicator(rs.getInt("NO_JNL_IND"));
				ob.setJnlsOpenItemId(rs.getInt("OPEN_ITEM_ID"));
				try {
					ob.setJnlsCContabilDebito(rs.getInt("FML_ACCT_DB"));
					ob.setJnlsCContabilCredito(rs.getInt("FML_ACCT_CR"));
				}catch (Exception e) {
					Log.info("Falha ao converter conta contábil em inteiro. Jnl Code Id: "+ob.getJnlsCodeId() +" Id Value: "+ob.getJnlsIdValue()+ " Erro: " + e.getCause());
				}
				ob.setJnlsAdjCategory(rs.getInt("ADJ_CATEGORY"));
				ob.setJnlsActiveDate(rs.getDate("ACTIVE_DATE"));
				ob.setJnlsInactiveDate(rs.getDate("INACTIVE_DATE"));
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

	public List<Integer> kenanBuscaTaxTypeCode(ProdutoVerdadeVo ob) {
		//ProdutoSAPVo ob ;
		List<Integer> lob = new ArrayList<Integer>();
		Connection dc = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;	
		try {
			sql = QueryWarehouse.getQuery("kenanBuscaTaxTypeCode");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);
			pst.setInt(1, ob.getIdProdutoKenan());
			pst.setString(1, ob.getUf());
			rs = pst.executeQuery();
			while(rs.next()) {
				lob.add(rs.getInt("TAX_TYPE_CODE"));
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

	public boolean kenanBuscaQtClassificacaoTv(ProdutoVerdadeVo ob) {
		//ProdutoSAPVo ob ;
		Connection dc = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;	
		try {
			sql = QueryWarehouse.getQuery("kenanBuscaQtClassificacaoTv");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);
			pst.setInt(1, ob.getIdType());
			pst.setInt(2, ob.getIdType());
			pst.setInt(3, ob.getIdProdutoKenan());
			rs = pst.executeQuery();
			while(rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			Log.error("Falha ao buscar Tabela Verdade. ", e);
		} catch (Exception e) {
			Log.error("Falha ao buscar Tabela Verdade. ", e);
		} finally{
			close(pst,dc);
		}
		return false;
	}

	public List<Integer> kenanBuscaCassificacaoTV(ProdutoVerdadeVo ob) {
		//ProdutoSAPVo ob ;
		List<Integer> lob = new ArrayList<Integer>();
		Connection dc = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;	
		try {
			sql = QueryWarehouse.getQuery("kenanBuscaCassificacaoTV");
			dc = Connections.getConn(Connections.CONN_KENAN_CT+1);
			pst = dc.prepareStatement(sql);
			pst.setInt(1, ob.getIdProdutoKenan());
			pst.setString(1, ob.getUf());
			rs = pst.executeQuery();
			while(rs.next()) {
				lob.add(rs.getInt("TAX_TYPE_CODE"));
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


	public void kenanLimparTabelaVerdade() {
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