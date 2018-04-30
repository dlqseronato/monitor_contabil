package com.proativo.cenario.vo;

import java.sql.Date;

public class ProdutoSAPVo{
	
	private int jnlsCodeId;
	private int jnlsIdType;
	private int jnlsIdValue;
	private int jnlsIdType2;
	private int jnlsIdValue2;
	private int jnlsUseCode;
	private int jnlsAccountCategory;
	private int jnlsAdjCategory;
	private int jnlsOpenItemId;
	private int jnlsIndicator;
	private int jnlsTaxTypeCode;
	private int jnlsCContabilDebito;
	private int jnlsCContabilCredito;
	private String sapDescription;
	private Date jnlsActiveDate;
	private Date jnlsInactiveDate;
	
	

	public String getSapDescription() {
		return sapDescription;
	}
	public void setSapDescription(String sapDescription) {
		this.sapDescription = sapDescription;
	}
	public int getJnlsCodeId() {
		return jnlsCodeId;
	}
	public void setJnlsCodeId(int jnlsCodeId) {
		this.jnlsCodeId = jnlsCodeId;
	}
	public int getJnlsIdType() {
		return jnlsIdType;
	}
	public void setJnlsIdType(int jnlsIdType) {
		this.jnlsIdType = jnlsIdType;
	}
	public int getJnlsIdValue() {
		return jnlsIdValue;
	}
	public void setJnlsIdValue(int jnlsIdValue) {
		this.jnlsIdValue = jnlsIdValue;
	}
	public int getJnlsIdType2() {
		return jnlsIdType2;
	}
	public void setJnlsIdType2(int jnlsIdType2) {
		this.jnlsIdType2 = jnlsIdType2;
	}
	public int getJnlsIdValue2() {
		return jnlsIdValue2;
	}
	public void setJnlsIdValue2(int jnlsIdValue2) {
		this.jnlsIdValue2 = jnlsIdValue2;
	}
	public int getJnlsUseCode() {
		return jnlsUseCode;
	}
	public void setJnlsUseCode(int jnlsUseCode) {
		this.jnlsUseCode = jnlsUseCode;
	}
	public int getJnlsAccountCategory() {
		return jnlsAccountCategory;
	}
	public void setJnlsAccountCategory(int jnlsAccountCategory) {
		this.jnlsAccountCategory = jnlsAccountCategory;
	}
	public int getJnlsAdjCategory() {
		return jnlsAdjCategory;
	}
	public void setJnlsAdjCategory(int jnlsAdjCategory) {
		this.jnlsAdjCategory = jnlsAdjCategory;
	}
	public int getJnlsOpenItemId() {
		return jnlsOpenItemId;
	}
	public void setJnlsOpenItemId(int jnlsOpenItemId) {
		this.jnlsOpenItemId = jnlsOpenItemId;
	}
	public int getJnlsCContabilDebito() {
		return jnlsCContabilDebito;
	}
	public void setJnlsCContabilDebito(int jnlsCContabilDebito) {
		this.jnlsCContabilDebito = jnlsCContabilDebito;
	}
	public int getJnlsCContabilCredito() {
		return jnlsCContabilCredito;
	}
	public void setJnlsCContabilCredito(int jnlsCContabilCredito) {
		this.jnlsCContabilCredito = jnlsCContabilCredito;
	}
	public Date getJnlsActiveDate() {
		return jnlsActiveDate;
	}
	public void setJnlsActiveDate(Date jnlsActiveDate) {
		this.jnlsActiveDate = jnlsActiveDate;
	}
	public Date getJnlsInactiveDate() {
		return jnlsInactiveDate;
	}
	public void setJnlsInactiveDate(Date jnlsInactiveDate) {
		this.jnlsInactiveDate = jnlsInactiveDate;
	}

	
	public int getJnlsIndicator() {
		return jnlsIndicator;
	}
	public void setJnlsIndicator(int jnlsIndicator) {
		this.jnlsIndicator = jnlsIndicator;
	}
	public int getJnlsTaxTypeCode() {
		return jnlsTaxTypeCode;
	}
	public void setJnlsTaxTypeCode(int jnlsTaxTypeCode) {
		this.jnlsTaxTypeCode = jnlsTaxTypeCode;
	}
	@Override
	public String toString() {
		return "Id: "+jnlsCodeId + " Account Category: "+ jnlsAccountCategory + " Open Item Id: " + jnlsOpenItemId + " Id Value: " + jnlsIdValue + " Id Type: " + jnlsIdType + " Use Code: " + jnlsUseCode +  " Id Value 2: " + jnlsIdValue2 + " Id Type 2: " + jnlsIdType2 + " Conta Contábil Débito: " + jnlsCContabilDebito + " Conta Contábil Crédito: " + jnlsCContabilCredito;
	}

}
