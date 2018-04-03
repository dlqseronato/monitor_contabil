package com.proativo.cenario.vo;

public class LoteVo {

	private String lote;
	private String processamento;
	private String competencia;
	private int jnlRefNo;
	private int jnlRefNoServ;
		
	public LoteVo(String lote, String processamento, String competencia, int jnlRefNo, int jnlRefNoServ) {
		super();
		this.lote = lote;
		this.processamento = processamento;
		this.competencia = competencia;
		this.jnlRefNo = jnlRefNo;
		this.jnlRefNoServ = jnlRefNoServ;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getProcessamento() {
		return processamento;
	}
	public void setProcessamento(String processamento) {
		this.processamento = processamento;
	}
	public String getCompetencia() {
		return competencia;
	}
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}
	public int getJnlRefNo() {
		return jnlRefNo;
	}
	public void setJnlRefNo(int jnlRefNo) {
		this.jnlRefNo = jnlRefNo;
	}
	public int getJnlRefNoServ() {
		return jnlRefNoServ;
	}
	public void setJnlRefNoServ(int jnlRefNoServ) {
		this.jnlRefNoServ = jnlRefNoServ;
	}
	
	
	
	
}
