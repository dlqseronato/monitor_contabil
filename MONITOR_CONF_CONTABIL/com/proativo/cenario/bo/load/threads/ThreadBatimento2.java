package com.proativo.cenario.bo.load.threads;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.proativo.cenario.bo.load.Load;
import com.proativo.cenario.dao.OraKenan;
import com.proativo.cenario.dao.OraProativo;
import com.proativo.cenario.vo.ContDetVo;
import com.proativo.cenario.vo.ErroVo;
import com.proativo.cenario.vo.ImpostoVo;
import com.proativo.cenario.vo.ProdutoSAPVo;
import com.proativo.cenario.vo.ProdutoVerdadeVo;
import com.proativo.util.connection.Connections;
import com.proativo.util.thread.ActionAbstract;
import com.proativo.util.thread.ThreadManagerDynamicConnection;
import com.proativo.util.vo.CenarioVo;

public class ThreadBatimento2 extends ActionAbstract<ProdutoVerdadeVo> {
	private OraKenan kenan;
	private OraProativo proativo;
	ErroVo erros;
	List<ErroVo> errorList;
	List<ContDetVo> countDetList;

	public ThreadBatimento2(CenarioVo cenario, ThreadManagerDynamicConnection tmdc,Integer qtdeCasos,List<ContDetVo> listaSAP) {
		super();
		this.errorList = new ArrayList<ErroVo>();
		this.tmdc = tmdc;
		this.cenario = cenario;
		this.kenan = new OraKenan();
		this.proativo = new OraProativo();
		this.totalLista = qtdeCasos.floatValue();
	}

	@Override
	public void exec(ProdutoVerdadeVo ob) {	
		
		
		
		
		
		
		
		
		
		
		atualizarProgresso(10);
	}

	public void addToRejectList(ErroVo erro,ProdutoVerdadeVo ob,ProdutoSAPVo sapOb,int codigoErro) {
		String tipoConta = "";

		switch (codigoErro) {
		case 2:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta cont�bil d�bito faturada "+ob.getcContabilDebitoFaturada()+" est� diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 3:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta cont�bil cr�dito faturada "+ob.getcContabilCreditoFaturada()+" est� diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		case 4:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta cont�bil d�bito a faturar "+ob.getcContabilDebitoAFaturar()+" est� diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 5:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta cont�bil cr�dito a faturar "+ob.getcContabilCreditoAFaturar()+" est� diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		case 6:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta cont�bil ajuste d�bito faturada "+ob.getcContabilAdjDebitoFaturada()+" est� diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 7:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta cont�bil ajuste cr�dito faturada "+ob.getcContabilAdjCreditoFaturada()+" est� diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		case 8:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta cont�bil ajuste d�bito a faturar "+ob.getcContabilAdjDebitoAFaturar()+" est� diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 9:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta cont�bil ajuste cr�dito a faturar "+ob.getcContabilAdjCreditoAFaturar()+" est� diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		default:
			break;
		}
		Load.addLoadRejectList(erro);
	}

}
