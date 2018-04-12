package com.proativo.cenario.vo;

public class CentroDeCustoVo {
	String centroDeCusto;
	int accountCategory;
	public String getCentroDeCusto() {
		return centroDeCusto;
	}
	public void setCentroDeCusto(String centroDeCusto) {
		this.centroDeCusto = centroDeCusto;
	}
	public int getAccountCategory() {
		return accountCategory;
	}
	public void setAccountCategory(int accountCategory) {
		this.accountCategory = accountCategory;
	}
	public CentroDeCustoVo(String centroDeCusto, int accountCategory) {
		super();
		this.centroDeCusto = centroDeCusto;
		this.accountCategory = accountCategory;
	}
	
	
}
