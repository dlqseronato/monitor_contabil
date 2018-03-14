package com.proativo.cenario.bo.load.threads;


import com.proativo.cenario.dao.OraKenan;
import com.proativo.cenario.dao.OraProativo;
import com.proativo.cenario.vo.ProdutoVerdadeVo;
import com.proativo.util.thread.ActionAbstract;
import com.proativo.util.thread.ThreadManagerDynamicConnection;
import com.proativo.util.vo.CenarioVo;

public class ThreadLoadKenan extends ActionAbstract<ProdutoVerdadeVo> {
	private OraKenan kenan;
	private OraProativo proativo;

	public ThreadLoadKenan(CenarioVo cenario, ThreadManagerDynamicConnection tmdc,Integer qtdeCasos) {
		super();
		this.tmdc = tmdc;
		this.cenario = cenario;
		this.kenan = new OraKenan();
		this.proativo = new OraProativo();
		this.totalLista = qtdeCasos.floatValue();
	}

	@Override
	public void exec(ProdutoVerdadeVo ob) {	
		ProdutoVerdadeVo newOb = ob;
		


		atualizarProgresso();
	}
}
