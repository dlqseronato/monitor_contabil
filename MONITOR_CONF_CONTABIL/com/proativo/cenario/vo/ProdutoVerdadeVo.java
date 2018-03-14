package com.proativo.cenario.vo;

import teste.Celula;
import teste.Planilha;

public class ProdutoVerdadeVo{
	private int id;
	private int idProdutoKenan;
	private String descricaoProduto;
	private String produtoVv;
	private String empresa;
	private String descricaoResumo;
	private int openItemIdFaturada;
	private int openItemIdAFaturar;
	private int openItemIdAdjFaturada;
	private int openItemIdAdjAFaturar;
	private int openItemIdImpFaturada;
	private int openItemIdImpAFaturar;
	private int cContabilDebitoFaturada;
	private int cContabilCreditoFaturada;
	private int cContabilAdjDebitoFaturada;
	private int cContabilAdjCreditoFaturada;
	private int cContabilImpDebitoFaturada;
	private int cContabilImpCreditoFaturada;
	private int cContabilDebitoVencimento;
	private int cContabilCreditoVencimento;
	private int cContabilDebitoAFaturar;
	private int cContabilCreditoAFaturar;
	private int cContabilAdjDebitoAFaturar;
	private int cContabilAdjCreditoAFaturar;
	private int cContabilImpDebitoAFaturar;
	private int cContabilImpCreditoAFaturar;
	
	private String rowIdFaturada;
	private String rowIdAFaturar;
	private String divisaoFaturada;
	private String divisaoAFaturar;
	private String divisaoAdjFaturada;
	private String divisaoAdjAFaturar;
	private String divisaoImpFaturada;
	private String divisaoImpAFaturar;
	private String ordemInternaFaturada;
	private String ordemInternaAFaturar;
	private String ordemInternaAdjFaturada;
	private String ordemInternaAdjAFaturar;
	private String ordemInternaImpFaturada;
	private String ordemInternaImpAFaturar;
	private String centroCustoFaturada;
	private String centroCustoAFaturar;
	private String centroCustoAdjFaturada;
	private String centroCustoAdjAFaturar;
	private String centroCustoImpFaturada;
	private String centroCustoImpAFaturar;
	private int accountCategory;
	private String descricaoAccountCategory;
	private int mktCode;
	private String uf;
	private String estado;
	
	private int useCode;
	private int idType;
	private int idType2;
	private int errorType;
	private String msgError;
	private int jnlsCodeId;
	private int jnlsCContabilDebitoFaturada;
	private int jnlsCContabilDebitoAFaturar;
	private int jnlsCContabilCreditoFaturada;
	private int jnlsCContabilCreditoAFaturar;
	private int jnlsCContabilAdjDebitoFaturada;
	private int jnlsCContabilAdjDebitoAFaturar;
	private int jnlsCContabilAdjCreditoFaturada;
	private int jnlsCContabilAdjCreditoAFaturar;
	
	private int jnlsCContabilImpDebitoFaturada;
	private int jnlsCContabilImpjDebitoAFaturar;
	private int jnlsCContabilImpCreditoFaturada;
	private int jnlsCContabilImpCreditoAFaturar;
	
	
	
	public int getIdType2() {
		return idType2;
	}
	public void setIdType2(int idType2) {
		this.idType2 = idType2;
	}

	private ErroVo erros = new ErroVo();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdProdutoKenan() {
		return idProdutoKenan;
	}
	public void setIdProdutoKenan(int idProdutoKenan) {
		this.idProdutoKenan = idProdutoKenan;
	}
	public String getDescricaoProduto() {
		return descricaoProduto;
	}
	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}
	public String getProdutoVv() {
		return produtoVv;
	}
	public void setProdutoVv(String produtoVv) {
		this.produtoVv = produtoVv;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getDescricaoResumo() {
		return descricaoResumo;
	}
	public void setDescricaoResumo(String descricaoResumo) {
		this.descricaoResumo = descricaoResumo;
	}
	public int getOpenItemIdFaturada() {
		return openItemIdFaturada;
	}
	public void setOpenItemIdFaturada(int openItemIdFaturada) {
		this.openItemIdFaturada = openItemIdFaturada;
	}
	public int getcContabilDebitoFaturada() {
		return cContabilDebitoFaturada;
	}
	public void setcContabilDebitoFaturada(int cContabilDebitoFaturada) {
		this.cContabilDebitoFaturada = cContabilDebitoFaturada;
	}
	public int getcContabilCreditoFaturada() {
		return cContabilCreditoFaturada;
	}
	public void setcContabilCreditoFaturada(int cContabilCreditoFaturada) {
		this.cContabilCreditoFaturada = cContabilCreditoFaturada;
	}
	public int getcContabilDebitoVencimento() {
		return cContabilDebitoVencimento;
	}
	public void setcContabilDebitoVencimento(int cContabilDebitoVencimento) {
		this.cContabilDebitoVencimento = cContabilDebitoVencimento;
	}
	public int getcContabilCreditoVencimento() {
		return cContabilCreditoVencimento;
	}
	public void setcContabilCreditoVencimento(int cContabilCreditoVencimento) {
		this.cContabilCreditoVencimento = cContabilCreditoVencimento;
	}
	public int getcContabilDebitoAFaturar() {
		return cContabilDebitoAFaturar;
	}
	public void setcContabilDebitoAFaturar(int cContabilDebitoAFaturar) {
		this.cContabilDebitoAFaturar = cContabilDebitoAFaturar;
	}
	public int getcContabilCreditoAFaturar() {
		return cContabilCreditoAFaturar;
	}
	public void setcContabilCreditoAFaturar(int cContabilCreditoAFaturar) {
		this.cContabilCreditoAFaturar = cContabilCreditoAFaturar;
	}
	public String getDivisaoFaturada() {
		return divisaoFaturada;
	}
	public void setDivisaoFaturada(String divisaoFaturada) {
		this.divisaoFaturada = divisaoFaturada;
	}
	public String getDivisaoAFaturar() {
		return divisaoAFaturar;
	}
	public void setDivisaoAFaturar(String divisaoAFaturar) {
		this.divisaoAFaturar = divisaoAFaturar;
	}
	public String getOrdemInternaFaturada() {
		return ordemInternaFaturada;
	}
	public void setOrdemInternaFaturada(String ordemInternaFaturada) {
		this.ordemInternaFaturada = ordemInternaFaturada;
	}
	public String getOrdemInternaAFaturar() {
		return ordemInternaAFaturar;
	}
	public void setOrdemInternaAFaturar(String ordemInternaAFaturar) {
		this.ordemInternaAFaturar = ordemInternaAFaturar;
	}
	public String getCentroCustoFaturada() {
		return centroCustoFaturada;
	}
	public void setCentroCustoFaturada(String centroCustoFaturada) {
		this.centroCustoFaturada = centroCustoFaturada;
	}
	public String getCentroCustoAFaturar() {
		return centroCustoAFaturar;
	}
	public void setCentroCustoAFaturar(String centroCustoAFaturar) {
		this.centroCustoAFaturar = centroCustoAFaturar;
	}
	public int getAccountCategory() {
		return accountCategory;
	}
	public void setAccountCategory(int accountCategory) {
		this.accountCategory = accountCategory;
	}
	public String getDescricaoAccountCategory() {
		return descricaoAccountCategory;
	}
	public void setDescricaoAccountCategory(String descricaoAccountCategory) {
		this.descricaoAccountCategory = descricaoAccountCategory;
	}
	public int getMktCode() {
		return mktCode;
	}
	public void setMktCode(int mktCode) {
		this.mktCode = mktCode;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getOpenItemIdAFaturar() {
		return openItemIdAFaturar;
	}
	public void setOpenItemIdAFaturar(int openItemIdAFaturar) {
		this.openItemIdAFaturar = openItemIdAFaturar;
	}
	
	public void setValues(Planilha p, int index) {
		for (Celula cel : p.getPlanilha().get(index).getLinhaCelulas()) {
			if(cel.getCelula().getContents() != null && !cel.getCelula().getContents().isEmpty() && cel.getCelula().getContents() != "") {
				switch (p.getHeader().getLinhaCelulas().get(cel.getCelula().getColumn()).getCelula().getContents()) {
				case "ID_PRODUTO_KENAN":
					this.idProdutoKenan = cel.getIntValue();
					break;
				case "DESCR_PRODUTO":
					this.descricaoProduto = cel.getStringValue();
					break;
				case "PRODUTO_VV":
					this.produtoVv = cel.getStringValue();
					break;
				case "OPEN_ITEM_ID":
					this.openItemIdFaturada = cel.getIntValue();
					break;
				case "EMPRESA":
					this.empresa = cel.getStringValue();
					break;
				case "DESCR_RESUMO":
					this.descricaoResumo = cel.getStringValue();
					if(descricaoResumo.equals("USO"))
						idType = 4;
					if(descricaoResumo.equals("NRC"))
						idType = 6;
					if(descricaoResumo.equals("RC"))
						idType = 3;
					break;
				case "FAT_CC_DEBITO":
					this.cContabilDebitoFaturada = cel.getIntValue();
					break;
				case "PARA_FAT_CC_DEBITO":
					this.cContabilDebitoFaturada = cel.getIntValue();
					break;
				case "FAT_CC_CREDITO":
					this.cContabilCreditoFaturada = cel.getIntValue();
					break;
				case "PARA_FAT_CC_CREDITO":
					this.cContabilCreditoFaturada = cel.getIntValue();
					break;
				case "ACCOUNT_CATEGORY":
					this.accountCategory = cel.getIntValue();
					break;
				case "DESCR_ACCOUNT_CATEGORY":
					this.descricaoAccountCategory = cel.getStringValue();
					break;
				case "DIVISAO":
					this.divisaoFaturada = cel.getStringValue();
					break;
				case "MKT_CODE":
					this.mktCode = cel.getIntValue();
					break;
				case "UF":
					this.uf = cel.getStringValue();
					break;
				case "ESTADO":
					this.estado = cel.getStringValue();
					break;
				case "ORDEM_INTERNA":
					this.ordemInternaFaturada = cel.getStringValue();
					break;
				case "PARA_ORDEM_INTERNA":
					this.ordemInternaFaturada = cel.getStringValue();
					break;
				case "CENTRO_CUSTO":
					this.centroCustoFaturada = cel.getStringValue();
					break;
				case "PARA_CENTRO_CUSTO":
					this.centroCustoFaturada = cel.getStringValue();
					break;
				case "A_FAT_CC_DEBITO":
					this.cContabilDebitoAFaturar = cel.getIntValue();
					break;
				case "PARA_A_FAT_CC_DEBITO":
					this.cContabilDebitoAFaturar = cel.getIntValue();
					break;
				case "A_FAT_CC_CREDITO":
					this.cContabilCreditoAFaturar = cel.getIntValue();
					break;
				case "PARA_A_FAT_CC_CREDITO":
					this.cContabilCreditoAFaturar = cel.getIntValue();
					break;
				case "A_FAT_DIVISAO":
					this.divisaoAFaturar = cel.getStringValue();
					break;
				case "AFAT_ORDEM_INTERNA":
					this.ordemInternaAFaturar = cel.getStringValue();
					break;
				case "AFAT_PARA_ORDEM_INTERNA":
					this.ordemInternaAFaturar = cel.getStringValue();
					break;
				case "AFAT_CENTRO_CUSTO":
					this.centroCustoAFaturar = cel.getStringValue();
					break;
				case "AFAT_OPEN_ITEM_ID":
					this.openItemIdAFaturar = cel.getIntValue();
					break;
				case "A_FAT_ADJ_CONTA_DEBITO":
					this.cContabilAdjDebitoAFaturar = cel.getIntValue();
					break;
				case "PARA_A_FAT_ADJ_CONTA_DEBITO":
					this.cContabilAdjDebitoAFaturar = cel.getIntValue();
					break;
				case "A_FAT_ADJ_CONTA_CREDITO":
					this.cContabilAdjCreditoAFaturar = cel.getIntValue();
					break;
				case "PARA_A_FAT_ADJ_CONTA_CREDITO":
					this.cContabilAdjCreditoAFaturar = cel.getIntValue();
					break;
				case "A_FAT_ADJ_DIVISAO":
					this.divisaoAFaturar = cel.getStringValue();
					break;
				case "A_FAT_ADJ_ORDEM_INTERNA":
					this.ordemInternaAdjAFaturar = cel.getStringValue();
					break;
				case "A_FAT_ADJ_CENTRO_CUSTO":
					this.centroCustoAdjAFaturar = cel.getStringValue();
					break;
				case "A_FAT_ADJ_OPEN_ITEM_ID":
					this.openItemIdAdjAFaturar = cel.getIntValue();
					break;	
				case "FAT_ADJ_CONTA_DEBITO":
					this.cContabilAdjDebitoFaturada = cel.getIntValue();
					break;
				case "PARA_FAT_ADJ_CONTA_DEBITO":
					this.cContabilAdjDebitoFaturada = cel.getIntValue();
					break;
				case "FAT_ADJ_CONTA_CREDITO":
					this.cContabilAdjCreditoFaturada = cel.getIntValue();
					break;
				case "PARA_FAT_ADJ_CONTA_CREDITO":
					this.cContabilAdjCreditoFaturada = cel.getIntValue();
					break;
				case "FAT_ADJ_DIVISAO":
					this.divisaoAdjFaturada = cel.getStringValue();
					break;
				case "FAT_ADJ_CENTRO_CUSTO":
					this.ordemInternaAdjFaturada = cel.getStringValue();
					break;
				case "FAT_ADJ_ORDEM_INTERNA":
					this.centroCustoAdjFaturada = cel.getStringValue();
					break;
				case "FAT_ADJ_OPEN_ITEM_ID":
					this.openItemIdAdjFaturada = cel.getIntValue();
					break;						
					

				case "FAT_IMP_CONTA_DEBITO":
					this.cContabilImpDebitoFaturada = cel.getIntValue();
					break;
				case "PARA_FAT_IMP_CONTA_DEBITO":
					this.cContabilImpDebitoFaturada = cel.getIntValue();
					break;
				case "FAT_IMP_CONTA_CREDITO":
					this.cContabilImpCreditoFaturada = cel.getIntValue();
					break;
				case "PARA_FAT_IMP_CONTA_CREDITO":
					this.cContabilImpCreditoFaturada = cel.getIntValue();
					break;
				case "FAT_IMP_DIVISAO":
					this.divisaoImpFaturada = cel.getStringValue();
					break;
				case "FAT_IMP_ORDEM_INTERNA":
					this.ordemInternaImpFaturada = cel.getStringValue();
					break;
				case "FAT_IMP_CENTRO_CUSTO":
					this.centroCustoImpFaturada = cel.getStringValue();
					break;
				case "FAT_IMP_OPEN_ITEM_ID":
					this.openItemIdImpFaturada = cel.getIntValue();
					break;	
					
				case "A_FAT_IMP_CONTA_DEBITO":
					this.cContabilImpDebitoAFaturar = cel.getIntValue();
					break;
				case "PARA_A_FAT_IMP_CONTA_DEBITO":
					this.cContabilImpDebitoAFaturar = cel.getIntValue();
					break;
				case "A_FAT_IMP_CONTA_CREDITO":
					this.cContabilImpDebitoAFaturar = cel.getIntValue();
					break;
				case "PARA_A_FAT_IMP_CONTA_CREDITO":
					this.cContabilImpCreditoAFaturar = cel.getIntValue();
					break;
				case "A_FAT_IMP_DIVISAO":
					this.divisaoAFaturar = cel.getStringValue();
					break;
				case "A_FAT_IMP_ORDEM_INTERNA":
					this.ordemInternaImpAFaturar = cel.getStringValue();
					break;
				case "A_FAT_IMP_CENTRO_CUSTO":
					this.centroCustoImpAFaturar = cel.getStringValue();
					break;
				case "A_FAT_IMP_OPEN_ITEM_ID":
					this.openItemIdImpAFaturar = cel.getIntValue();
					break;				
				default:
					break;
				}
			}
		}
	}
	@Override
	public String toString() {
		return ("Id Produto: "+
				this.idProdutoKenan+
				"  Descrição Produto: "+
				this.descricaoProduto+
				"  Produto Vencimento a Vencimento: "+
				this.produtoVv+
				"  Empresa: "+
				this.empresa+
				"  Resumo Descrição: "+
				this.descricaoResumo+
				"  Open Item ID Faturada: "+
				this.openItemIdFaturada+
				"  Conta contábil Débito Faturada: "+
				this.cContabilDebitoFaturada+
				"  Conta contábil Crédito Faturada: "+
				this.cContabilCreditoFaturada+
				"  Conta contábil Débito VV: "+
				this.cContabilDebitoVencimento+
				"  Conta contábil Crédito VV: "+
				this.cContabilCreditoVencimento+
				"  Account category: "+
				this.accountCategory+
				"  Descrição account category: "+
				this.descricaoAccountCategory+
				"  Divisao faturada: "+
				this.divisaoFaturada+
				"  MKT Code: "+
				this.mktCode+
				"  UF: "+
				this.uf+
				"  Estado: "+
				this.estado+
				"  Ordem Interna Faturada: "+
				this.ordemInternaFaturada+
				"  Centro de Custo Faturada: "+
				this.centroCustoFaturada + 
				"  Conta contábil Débito A Faturar: "+
				this.cContabilDebitoAFaturar+
				"  Conta contábil Crédito A Faturar: "+
				this.cContabilCreditoAFaturar+
				"  Divisao A Faturar: "+
				this.divisaoAFaturar+
				"  Ordem Interna A Faturar: "+
				this.ordemInternaAFaturar+
				"  Centro de Custo A Faturar: "+
				this.centroCustoAFaturar
				);

	}
	public String getRowIdFaturada() {
		return rowIdFaturada;
	}
	public void setRowIdFaturada(String rowIdFaturada) {
		this.rowIdFaturada = rowIdFaturada;
	}
	public String getRowIdAFaturar() {
		return rowIdAFaturar;
	}
	public void setRowIdAFaturar(String rowIdAFaturar) {
		this.rowIdAFaturar = rowIdAFaturar;
	}

	
	public int getUseCode() {
		return useCode;
	}
	public void setUseCode(int useCode) {
		this.useCode = useCode;
	}
	public int getIdType() {
		return idType;
	}
	public void setIdType(int useType) {
		this.idType = useType;
	}
	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	public String getMsgError() {
		return msgError;
	}
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}


	
	public int getJnlsCodeId() {
		return jnlsCodeId;
	}
	public void setJnlsCodeId(int jnlsCodeId) {
		this.jnlsCodeId = jnlsCodeId;
	}
	public int getJnlsCContabilDebitoFaturada() {
		return jnlsCContabilDebitoFaturada;
	}
	public void setJnlsCContabilDebitoFaturada(int jnlsCContabilDebitoFaturada) {
		this.jnlsCContabilDebitoFaturada = jnlsCContabilDebitoFaturada;
	}
	public int getJnlsCContabilDebitoAFaturar() {
		return jnlsCContabilDebitoAFaturar;
	}
	public void setJnlsCContabilDebitoAFaturar(int jnlsCContabilDebitoAFaturar) {
		this.jnlsCContabilDebitoAFaturar = jnlsCContabilDebitoAFaturar;
	}
	public int getJnlsCContabilCreditoFaturada() {
		return jnlsCContabilCreditoFaturada;
	}
	public void setJnlsCContabilCreditoFaturada(int jnlsCContabilCreditoFaturada) {
		this.jnlsCContabilCreditoFaturada = jnlsCContabilCreditoFaturada;
	}
	public int getJnlsCContabilCreditoAFaturar() {
		return jnlsCContabilCreditoAFaturar;
	}
	public void setJnlsCContabilCreditoAFaturar(int jnlsCContabilCreditoAFaturar) {
		this.jnlsCContabilCreditoAFaturar = jnlsCContabilCreditoAFaturar;
	}
	
	
	public int getOpenItemIdAdjFaturada() {
		return openItemIdAdjFaturada;
	}
	public void setOpenItemIdAdjFaturada(int openItemIdAdjFaturada) {
		this.openItemIdAdjFaturada = openItemIdAdjFaturada;
	}
	public int getOpenItemIdAdjAFaturar() {
		return openItemIdAdjAFaturar;
	}
	public void setOpenItemIdAdjAFaturar(int openItemIdAdjAFaturar) {
		this.openItemIdAdjAFaturar = openItemIdAdjAFaturar;
	}
	public int getcContabilAdjDebitoFaturada() {
		return cContabilAdjDebitoFaturada;
	}
	public void setcContabilAdjDebitoFaturada(int cContabilAdjDebitoFaturada) {
		this.cContabilAdjDebitoFaturada = cContabilAdjDebitoFaturada;
	}
	public int getcContabilAdjCreditoFaturada() {
		return cContabilAdjCreditoFaturada;
	}
	public void setcContabilAdjCreditoFaturada(int cContabilAdjCreditoFaturada) {
		this.cContabilAdjCreditoFaturada = cContabilAdjCreditoFaturada;
	}
	public int getcContabilAdjDebitoAFaturar() {
		return cContabilAdjDebitoAFaturar;
	}
	public void setcContabilAdjDebitoAFaturar(int cContabilAdjDebitoAFaturar) {
		this.cContabilAdjDebitoAFaturar = cContabilAdjDebitoAFaturar;
	}
	public int getcContabilAdjCreditoAFaturar() {
		return cContabilAdjCreditoAFaturar;
	}
	public void setcContabilAdjCreditoAFaturar(int cContabilAdjCreditoAFaturar) {
		this.cContabilAdjCreditoAFaturar = cContabilAdjCreditoAFaturar;
	}
	public String getDivisaoAdjFaturada() {
		return divisaoAdjFaturada;
	}
	public void setDivisaoAdjFaturada(String divisaoAdjFaturada) {
		this.divisaoAdjFaturada = divisaoAdjFaturada;
	}
	public String getDivisaoAdjAFaturar() {
		return divisaoAdjAFaturar;
	}
	public void setDivisaoAdjAFaturar(String divisaoAdjAFaturar) {
		this.divisaoAdjAFaturar = divisaoAdjAFaturar;
	}
	public String getOrdemInternaAdjFaturada() {
		return ordemInternaAdjFaturada;
	}
	public void setOrdemInternaAdjFaturada(String ordemInternaAdjFaturada) {
		this.ordemInternaAdjFaturada = ordemInternaAdjFaturada;
	}
	public String getOrdemInternaAdjAFaturar() {
		return ordemInternaAdjAFaturar;
	}
	public void setOrdemInternaAdjAFaturar(String ordemInternaAdjAFaturar) {
		this.ordemInternaAdjAFaturar = ordemInternaAdjAFaturar;
	}
	public String getCentroCustoAdjFaturada() {
		return centroCustoAdjFaturada;
	}
	public void setCentroCustoAdjFaturada(String centroCustoAdjFaturada) {
		this.centroCustoAdjFaturada = centroCustoAdjFaturada;
	}
	public String getCentroCustoAdjAFaturar() {
		return centroCustoAdjAFaturar;
	}
	public void setCentroCustoAdjAFaturar(String centroCustoAdjAFaturar) {
		this.centroCustoAdjAFaturar = centroCustoAdjAFaturar;
	}
	
	
	public int getJnlsCContabilAdjDebitoFaturada() {
		return jnlsCContabilAdjDebitoFaturada;
	}
	public void setJnlsCContabilAdjDebitoFaturada(int jnlsCContabilAdjDebitoFaturada) {
		this.jnlsCContabilAdjDebitoFaturada = jnlsCContabilAdjDebitoFaturada;
	}
	public int getJnlsCContabilAdjDebitoAFaturar() {
		return jnlsCContabilAdjDebitoAFaturar;
	}
	public void setJnlsCContabilAdjDebitoAFaturar(int jnlsCContabilAdjDebitoAFaturar) {
		this.jnlsCContabilAdjDebitoAFaturar = jnlsCContabilAdjDebitoAFaturar;
	}
	public int getJnlsCContabilAdjCreditoFaturada() {
		return jnlsCContabilAdjCreditoFaturada;
	}
	public void setJnlsCContabilAdjCreditoFaturada(int jnlsCContabilAdjCreditoFaturada) {
		this.jnlsCContabilAdjCreditoFaturada = jnlsCContabilAdjCreditoFaturada;
	}
	public int getJnlsCContabilAdjCreditoAFaturar() {
		return jnlsCContabilAdjCreditoAFaturar;
	}
	public void setJnlsCContabilAdjCreditoAFaturar(int jnlsCContabilAdjCreditoAFaturar) {
		this.jnlsCContabilAdjCreditoAFaturar = jnlsCContabilAdjCreditoAFaturar;
	}
	
	
	
	public int getcContabilImpDebitoFaturada() {
		return cContabilImpDebitoFaturada;
	}
	public void setcContabilImpDebitoFaturada(int cContabilImpDebitoFaturada) {
		this.cContabilImpDebitoFaturada = cContabilImpDebitoFaturada;
	}
	public int getcContabilImpCreditoFaturada() {
		return cContabilImpCreditoFaturada;
	}
	public void setcContabilImpCreditoFaturada(int cContabilImpCreditoFaturada) {
		this.cContabilImpCreditoFaturada = cContabilImpCreditoFaturada;
	}
	public int getcContabilImpDebitoAFaturar() {
		return cContabilImpDebitoAFaturar;
	}
	public void setcContabilImpDebitoAFaturar(int cContabilImpDebitoAFaturar) {
		this.cContabilImpDebitoAFaturar = cContabilImpDebitoAFaturar;
	}
	public int getcContabilImpCreditoAFaturar() {
		return cContabilImpCreditoAFaturar;
	}
	public void setcContabilImpCreditoAFaturar(int cContabilImpCreditoAFaturar) {
		this.cContabilImpCreditoAFaturar = cContabilImpCreditoAFaturar;
	}
	public String getDivisaoImpFaturada() {
		return divisaoImpFaturada;
	}
	public void setDivisaoImpFaturada(String divisaoImpFaturada) {
		this.divisaoImpFaturada = divisaoImpFaturada;
	}
	public String getDivisaoImpAFaturar() {
		return divisaoImpAFaturar;
	}
	public void setDivisaoImpAFaturar(String divisaoImpAFaturar) {
		this.divisaoImpAFaturar = divisaoImpAFaturar;
	}
	public String getOrdemInternaImpFaturada() {
		return ordemInternaImpFaturada;
	}
	public void setOrdemInternaImpFaturada(String ordemInternaImpFaturada) {
		this.ordemInternaImpFaturada = ordemInternaImpFaturada;
	}
	public String getOrdemInternaImpAFaturar() {
		return ordemInternaImpAFaturar;
	}
	public void setOrdemInternaImpAFaturar(String ordemInternaImpAFaturar) {
		this.ordemInternaImpAFaturar = ordemInternaImpAFaturar;
	}
	public String getCentroCustoImpFaturada() {
		return centroCustoImpFaturada;
	}
	public void setCentroCustoImpFaturada(String centroCustoImpFaturada) {
		this.centroCustoImpFaturada = centroCustoImpFaturada;
	}
	public String getCentroCustoImpAFaturar() {
		return centroCustoImpAFaturar;
	}
	public void setCentroCustoImpAFaturar(String centroCustoImpAFaturar) {
		this.centroCustoImpAFaturar = centroCustoImpAFaturar;
	}
	public int getJnlsCContabilImpDebitoFaturada() {
		return jnlsCContabilImpDebitoFaturada;
	}
	public void setJnlsCContabilImpDebitoFaturada(int jnlsCContabilImpDebitoFaturada) {
		this.jnlsCContabilImpDebitoFaturada = jnlsCContabilImpDebitoFaturada;
	}
	public int getJnlsCContabilImpjDebitoAFaturar() {
		return jnlsCContabilImpjDebitoAFaturar;
	}
	public void setJnlsCContabilImpjDebitoAFaturar(int jnlsCContabilImpjDebitoAFaturar) {
		this.jnlsCContabilImpjDebitoAFaturar = jnlsCContabilImpjDebitoAFaturar;
	}
	public int getJnlsCContabilImpCreditoFaturada() {
		return jnlsCContabilImpCreditoFaturada;
	}
	public void setJnlsCContabilImpCreditoFaturada(int jnlsCContabilImpCreditoFaturada) {
		this.jnlsCContabilImpCreditoFaturada = jnlsCContabilImpCreditoFaturada;
	}
	public int getJnlsCContabilImpCreditoAFaturar() {
		return jnlsCContabilImpCreditoAFaturar;
	}
	public void setJnlsCContabilImpCreditoAFaturar(int jnlsCContabilImpCreditoAFaturar) {
		this.jnlsCContabilImpCreditoAFaturar = jnlsCContabilImpCreditoAFaturar;
	}
	public int getOpenItemIdImpFaturada() {
		return openItemIdImpFaturada;
	}
	public void setOpenItemIdImpFaturada(int openItemIdImpFaturada) {
		this.openItemIdImpFaturada = openItemIdImpFaturada;
	}
	public int getOpenItemIdImpAFaturar() {
		return openItemIdImpAFaturar;
	}
	public void setOpenItemIdImpAFaturar(int openItemIdImpAFaturar) {
		this.openItemIdImpAFaturar = openItemIdImpAFaturar;
	}
	public ErroVo getErros() {
		return erros;
	}
	public void setErros(ErroVo erros) {
		this.erros = erros;
	}
	
	public ProdutoVerdadeVo() {
		super();
	}
	
	public ProdutoVerdadeVo(int id,int idProdutoKenan,int accountCategory, int cContabilDebitoFaturada, int cContabilCreditoFaturada, int cContabilDebitoAFaturar, int cContabilCreditoAFaturar, int cContabilAdjDebitoFaturada, int cContabilAdjCreditoFaturada, int cContabilAdjDebitoAFaturar,int cContabilAdjCreditoAFaturar  ){
		super();
		this.id = id;
		this.idProdutoKenan = idProdutoKenan;
		this.accountCategory = accountCategory;
		this.cContabilDebitoFaturada = cContabilDebitoFaturada;
		this.cContabilCreditoFaturada = cContabilCreditoFaturada;
		this.cContabilDebitoAFaturar = cContabilDebitoAFaturar;
		this.cContabilCreditoAFaturar = cContabilCreditoAFaturar;
		this.cContabilAdjDebitoFaturada = cContabilAdjDebitoFaturada;
		this.cContabilAdjCreditoFaturada = cContabilAdjCreditoFaturada;
		this.cContabilAdjDebitoAFaturar = cContabilAdjDebitoAFaturar;
		this.cContabilAdjCreditoAFaturar = cContabilAdjCreditoAFaturar;
	}

}
