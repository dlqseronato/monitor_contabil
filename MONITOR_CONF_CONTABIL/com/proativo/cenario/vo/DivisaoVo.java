package com.proativo.cenario.vo;

import java.sql.Date;

public class DivisaoVo {

	private int mktCode;
	private String descricao;
	private String divisao;
	
	public int getMktCode() {
		return mktCode;
	}
	public void setMktCode(int mktCode) {
		this.mktCode = mktCode;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDivisao() {
		return divisao;
	}
	public void setDivisao(String divisao) {
		this.divisao = divisao;
	}
	public DivisaoVo(int mktCode, String descricao, String divisao) {
		super();
		this.mktCode = mktCode;
		this.descricao = descricao;
		this.divisao = divisao;
	}
	
	
	
}
