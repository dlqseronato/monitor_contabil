package com.proativo.cenario.vo;

public class ErroVo{
	private int id;
	private int idProdutoKenan;
	private int jnlsCodeId;
	private int useCode;
	private int idType;
	private int accountCategory;
	private int openItemId;
	private int errorType;
	private String msgError = "";
	
	public ErroVo() {
		super();
	}
	public ErroVo(int id, int idProdutoKenan, int jnlsCodeId, int useCode, int idType, int accountCategory, int openItemId, int errorType) {
		super();
		this.id = id;
		this.idProdutoKenan = idProdutoKenan;
		this.jnlsCodeId = jnlsCodeId;
		this.useCode = useCode;
		this.idType = idType;
		this.accountCategory = accountCategory;
		this.openItemId = openItemId;
		this.errorType = errorType;
	}
	
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
	public int getJnlsCodeId() {
		return jnlsCodeId;
	}
	public void setJnlsCodeId(int jnlsCodeId) {
		this.jnlsCodeId = jnlsCodeId;
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
	public void setIdType(int idType) {
		this.idType = idType;
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
	
	
	
}
