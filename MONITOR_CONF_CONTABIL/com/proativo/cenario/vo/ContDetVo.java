package com.proativo.cenario.vo;

import java.sql.Date;

public class ContDetVo {
	private int id;
	private int empresaMktCode;
	private Date dataDocumento; 
	private int tipoLancamento; 
	private int idType2;
	private int contaContabilDb; 
	private int contaConbabilCr; 
	private int accountNo; 
	private String externalId; 
	private int accountCategory; 
	private int openItemId; 
	private String codAtribuicao; 
	private String divisao; 
	private String centroCusto;
	private int element;
	private String ordemInterna;
	
	
	
	
	public ContDetVo(int id, int empresaMktCode, Date dataDocumento, int tipoLancamento, int idType2,
			int contaContabilDb, int contaConbabilCr, int accountNo, String externalId, int accountCategory,
			int openItemId, String codAtribuicao, String divisao, String centroCusto, int element,
			String ordemInterna) {
		super();
		this.id = id;
		this.empresaMktCode = empresaMktCode;
		this.dataDocumento = dataDocumento;
		this.tipoLancamento = tipoLancamento;
		this.idType2 = idType2;
		this.contaContabilDb = contaContabilDb;
		this.contaConbabilCr = contaConbabilCr;
		this.accountNo = accountNo;
		this.externalId = externalId;
		this.accountCategory = accountCategory;
		this.openItemId = openItemId;
		this.codAtribuicao = codAtribuicao;
		this.divisao = divisao;
		this.centroCusto = centroCusto;
		this.element = element;
		this.ordemInterna = ordemInterna;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmpresaMktCode() {
		return empresaMktCode;
	}
	public void setEmpresaMktCode(int empresaMktCode) {
		this.empresaMktCode = empresaMktCode;
	}
	public Date getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public int getTipoLancamento() {
		return tipoLancamento;
	}
	public void setTipoLancamento(int tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}
	public int getIdType2() {
		return idType2;
	}
	public void setIdType2(int idType2) {
		this.idType2 = idType2;
	}
	public int getContaContabilDb() {
		return contaContabilDb;
	}
	public void setContaContabilDb(int contaContabilDb) {
		this.contaContabilDb = contaContabilDb;
	}
	public int getContaConbabilCr() {
		return contaConbabilCr;
	}
	public void setContaConbabilCr(int contaConbabilCr) {
		this.contaConbabilCr = contaConbabilCr;
	}
	public int getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public int getAccountCategory() {
		return accountCategory;
	}
	public void setAccountCategory(int accountCategory) {
		this.accountCategory = accountCategory;
	}
	public int getOpenItemId() {
		return openItemId;
	}
	public void setOpenItemId(int openItemId) {
		this.openItemId = openItemId;
	}
	public String getCodAtribuicao() {
		return codAtribuicao;
	}
	public void setCodAtribuicao(String codAtribuicao) {
		this.codAtribuicao = codAtribuicao;
	}
	public String getDivisao() {
		return divisao;
	}
	public void setDivisao(String divisao) {
		this.divisao = divisao;
	}
	public String getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(String centroCusto) {
		this.centroCusto = centroCusto;
	}
	public int getElement() {
		return element;
	}
	public void setElement(int element) {
		this.element = element;
	}
	public String getOrdemInterna() {
		return ordemInterna;
	}
	public void setOrdemInterna(String ordemInterna) {
		this.ordemInterna = ordemInterna;
	}
	
	
}
