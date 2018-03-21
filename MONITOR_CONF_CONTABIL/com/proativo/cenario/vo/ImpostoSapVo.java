package com.proativo.cenario.vo;

import java.sql.Date;

public class ImpostoSapVo{
	
	private int jnlsCodeId;
	private int jnlsIdType;
	private int jnlsIdValue;
	private int jnlsIdType2;
	private int jnlsIdValue2;
	private int jnlsUseCode;
	private int jnlsAccountCategory;
	private int jnlsOpenItemId;
	private int jnlsTaxTypeCode;
	private int jnlsCContabilDebito;
	private int jnlsCContabilCredito;
	private Date jnlsActiveDate;
	private Date jnlsInactiveDate;
	private int jnlsAccountNo;
	private int jnlsSubscribNo;
	private int jnlsBillRefNo;
	
	
	public int getJnlsAccountNo() {
		return jnlsAccountNo;
	}
	public void setJnlsAccountNo(int jnlsAccountNo) {
		this.jnlsAccountNo = jnlsAccountNo;
	}
	public int getJnlsSubscribNo() {
		return jnlsSubscribNo;
	}
	public void setJnlsSubscribNo(int jnlsSubscribNo) {
		this.jnlsSubscribNo = jnlsSubscribNo;
	}
	public int getJnlsBillRefNo() {
		return jnlsBillRefNo;
	}
	public void setJnlsBillRefNo(int jnlsBillRefNo) {
		this.jnlsBillRefNo = jnlsBillRefNo;
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
