package com.proativo.cenario.bo.load.threads;

import com.proativo.cenario.dao.OraKenan;
import com.proativo.cenario.dao.OraProativo;
import com.proativo.cenario.vo.ProdutoVerdadeVo;
import com.proativo.util.connection.Connections;
import com.proativo.util.thread.ActionAbstract;
import com.proativo.util.thread.ThreadManagerDynamicConnection;
import com.proativo.util.vo.CenarioVo;

public class ThreadLoadTabelaVerdade extends ActionAbstract<ProdutoVerdadeVo> {
	private OraProativo proativo;

	public ThreadLoadTabelaVerdade( CenarioVo cenario,ThreadManagerDynamicConnection tmdc, Integer qtdeCasos) {
		super();
		this.tmdc = tmdc;
		this.proativo = new OraProativo();
		this.cenario = cenario;
		this.totalLista = qtdeCasos.floatValue();
	}

	@Override
	public void exec(ProdutoVerdadeVo ob) {
		proativo.proativoInsereTabelaVerdade(ob, tmdc.getAvailableConnection(Connections.CONN_PROATIVO));
		atualizarProgresso(10);
	}
}
