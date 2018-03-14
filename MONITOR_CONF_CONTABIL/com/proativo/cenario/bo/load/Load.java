package com.proativo.cenario.bo.load;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.proativo.cenario.bo.load.threads.ThreadBatimento;
import com.proativo.cenario.bo.load.threads.ThreadLoadRejeitados;
import com.proativo.cenario.bo.load.threads.ThreadLoadTabelaVerdade;
import com.proativo.cenario.dao.OraKenan;
import com.proativo.cenario.dao.OraProativo;
import com.proativo.cenario.dao.OraUtil;
import com.proativo.cenario.util.Email;
import com.proativo.cenario.vo.ErroVo;
import com.proativo.cenario.vo.ProdutoSAPVo;
import com.proativo.cenario.vo.ProdutoVerdadeVo;
import com.proativo.util.Processo;
import com.proativo.util.connection.Connections;
import com.proativo.util.log.Log;
import com.proativo.util.thread.ThreadManagerDynamicConnection;
import com.proativo.util.vo.CenarioVo;
import com.proativo.util.vo.ProcessamentoCicloVo;

import teste.Planilha;

public class Load extends Processo {

	private ThreadManagerDynamicConnection tmdc;
	private static List<ProdutoVerdadeVo> listaVerdade;
	private static List<ProdutoSAPVo> listaSAP;
	//private static List<ProdutoVerdadeVo> listaRejeitados;
	private static List<ErroVo> listaRejeitados;
	private static List<ProdutoVerdadeVo> listaCorrigidos;
	public static int qtdErros;
	public String processamentoCiclo;
	public java.sql.Timestamp dataCiclo;
	private OraUtil util;
	private OraProativo proativo;
	private OraKenan kenan;

	public Load(){
		this.tmdc = new ThreadManagerDynamicConnection();
		util = new OraUtil();
		kenan = new OraKenan();
		proativo = new OraProativo();
		listaVerdade = new ArrayList<ProdutoVerdadeVo>();
		//listaRejeitados = new ArrayList<ProdutoVerdadeVo>();
		listaRejeitados = new ArrayList<ErroVo>();
		listaCorrigidos = new ArrayList<ProdutoVerdadeVo>();
		}


	public static synchronized void addAllLoadRejectList(List<ErroVo> list){
		listaRejeitados.addAll(list);
	}
	public static synchronized void addLoadRejectList(ErroVo erro){
		listaRejeitados.add(erro);
	}
	public static synchronized void addLoadCorrectList(ProdutoVerdadeVo ob){
		listaCorrigidos.add(ob);
	}
	public static synchronized void addLoadList(List<ProdutoVerdadeVo> list){
		listaVerdade.addAll(list);
	}
	
	

	public void executar(CenarioVo cenario, ProcessamentoCicloVo ciclo) {	
		
		Log.info("Kenan - Buscando configurações atuais da Journals:");
		listaSAP = kenan.kenanBuscaConfiguracoesJnls();
		
		Log.info("Util - Buscar dados planilha MONITOR_CONTABIL_CONF.");
		Planilha p;
		boolean ArquivoExiste = false;
		try {
			p = util.buscardadosPlanilha();
			ArquivoExiste = true;
			ProdutoVerdadeVo ob;
			for (int i = 0; i < p.getPlanilha().size(); i++) {
				ob = new ProdutoVerdadeVo();
				ob.setId(100000+i);
				ob.setValues(p, i);
				listaVerdade.add(ob);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.error("Util - Arquivo de configuração MONITOR_CONTABIL_CONF não existe no caminho.", e.getCause());
			ArquivoExiste = false;
		}
		if(ArquivoExiste) {
			Log.info("Proativo - Limpando tabela verdade atual.");
			kenan.kenanLimparTabelaVerdade();
			Log.info("Proativo - Inserindo novas configurações na tabela MONITOR_CONTABIL_CONF");
			tmdc.executar(listaVerdade, new ThreadLoadTabelaVerdade( cenario, tmdc,listaVerdade.size()), cenario.getQuantidadeThreads(), Connections.CONN_PROATIVO);
			
		}else{
			Log.info("Proativo - Buscando tabela verdade na tabela MONITOR_CONTABIL_CONF.");
			listaVerdade = kenan.kenanBuscarTabelaVerdade();
		}
		

		
		//tmdc.executar(listaVerdade, new ThreadLoadSAP( cenario, tmdc,listaVerdade.size()), cenario.getQuantidadeThreads(), Connections.CONN_KENAN_CT+1);
				
		Log.info("Kenan - Executando batimento de informações:");
		tmdc.executar(listaVerdade, new ThreadBatimento( cenario,tmdc,listaVerdade.size(),listaSAP), cenario.getQuantidadeThreads(), Connections.CONN_KENAN_CT+1, Connections.CONN_KENAN_CT+2 ,Connections.CONN_PROATIVO);
		//tmdc.executar(listaVerdade, new ThreadBatimento( cenario,tmdc,listaVerdade.size()), cenario.getQuantidadeThreads(), Connections.CONN_KENAN_CT+1, Connections.CONN_KENAN_CT+2 );
		
		listaSAP.clear();
		listaSAP = new ArrayList<ProdutoSAPVo>();
		
		Log.info("Kenan - Inserindo lista de rejeitados.");
		//tmdc.executar(listaRejeitados, new ThreadLoadRejeitados( cenario, tmdc,listaRejeitados.size()), cenario.getQuantidadeThreads(), Connections.CONN_KENAN_CT+1);
		tmdc.executar(listaRejeitados, new ThreadLoadRejeitados( cenario, tmdc,listaRejeitados.size()), cenario.getQuantidadeThreads(), Connections.CONN_PROATIVO);
	
		Log.info("Quantidade de objetos carregados: "+listaVerdade.size());
		Log.info("Quantidade de contas contábeis zeradas corrigidas: "+listaCorrigidos.size());
		Log.info("Quantidade de erros detectados: "+listaRejeitados.size());
		
		Log.info("Enviando e-mail de report.");
		Email.enviaEmail(cenario, listaVerdade.size(), listaVerdade.size() - listaRejeitados.size(), listaRejeitados.size(), listaRejeitados);
		
		super.setChanged();
		super.notifyObservers();
	}
}
