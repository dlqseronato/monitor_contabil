package com.proativo.cenario.vo;

public class OrdemInternaVo {
	//ELEMENT
	//JNL_ID_TYPE
	//ORDEM_INTERNA
	
	private int element;
	private int jnlIdType;
	private String ordemInterna;
	
	
	public OrdemInternaVo(int element, int jnlIdType, String ordemInterna) {
		super();
		this.element = element;
		this.jnlIdType = jnlIdType;
		this.ordemInterna = ordemInterna;
	}
	public int getElement() {
		return element;
	}
	public void setElement(int element) {
		this.element = element;
	}
	public int getJnlIdType() {
		return jnlIdType;
	}
	public void setJnlIdType(int jnlIdType) {
		this.jnlIdType = jnlIdType;
	}
	public String getOrdemInterna() {
		return ordemInterna;
	}
	public void setOrdemInterna(String ordemInterna) {
		this.ordemInterna = ordemInterna;
	}
	
	
	
}
