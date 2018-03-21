package com.proativo.cenario.bo.load.threads;

import com.proativo.cenario.dao.OraProativo;
import com.proativo.cenario.vo.ErroVo;
import com.proativo.util.connection.Connections;
import com.proativo.util.thread.ActionAbstract;
import com.proativo.util.thread.ThreadManagerDynamicConnection;
import com.proativo.util.vo.CenarioVo;

public class ThreadLoadRejeitados extends ActionAbstract<ErroVo> {
	private OraProativo proativo;

	public ThreadLoadRejeitados( CenarioVo cenario,ThreadManagerDynamicConnection tmdc, Integer qtdeCasos) {
		super();
		this.tmdc = tmdc;
		this.cenario = cenario;
		this.proativo = new OraProativo();
		this.totalLista = qtdeCasos.floatValue();
	}

	@Override
	public void exec(ErroVo ob) {
		proativo.proativoInsereRejeitados(ob, tmdc.getAvailableConnection(Connections.CONN_PROATIVO),cenario.getIdExecucao());
		atualizarProgresso(10);
	}
}
