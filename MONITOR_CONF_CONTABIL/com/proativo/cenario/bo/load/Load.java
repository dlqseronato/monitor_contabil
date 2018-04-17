package com.proativo.cenario.bo.load;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.proativo.cenario.bo.load.threads.ThreadBatimento;
import com.proativo.cenario.bo.load.threads.ThreadBatimento2;
import com.proativo.cenario.bo.load.threads.ThreadLoadRejeitados;
import com.proativo.cenario.bo.load.threads.ThreadLoadTabelaVerdade;
import com.proativo.cenario.dao.OraKenan;
import com.proativo.cenario.dao.OraProativo;
import com.proativo.cenario.dao.OraUtil;
import com.proativo.cenario.util.Email;
import com.proativo.cenario.vo.CentroDeCustoVo;
import com.proativo.cenario.vo.ContDetVo;
import com.proativo.cenario.vo.DivisaoVo;
import com.proativo.cenario.vo.ErroVo;
import com.proativo.cenario.vo.LoteVo;
import com.proativo.cenario.vo.OrdemInternaVo;
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
	private static List<ErroVo> listaRejeitados;
	private static List<ProdutoVerdadeVo> listaCorrigidos;
	private static List<DivisaoVo> divisoes;
	private static List<CentroDeCustoVo> centrosCusto;
	private static List<OrdemInternaVo> ordensInternas;
	private static int qtdPaginas;
	private LoteVo lote;
	private List<ContDetVo> contDetList;
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


	public static List<ErroVo> getListaRejeitados() {
		return listaRejeitados;
	}


	public static void setListaRejeitados(List<ErroVo> listaRejeitados) {
		Load.listaRejeitados = listaRejeitados;
	}


	public static List<DivisaoVo> getDivisoes(){
		return divisoes;
	}

	public static List<OrdemInternaVo> getOrdensInternas(){
		return ordensInternas;
	}

	public static List<CentroDeCustoVo> getCentrosDeCusto(){
		return centrosCusto;
	}


	public void executar(CenarioVo cenario, ProcessamentoCicloVo ciclo) {	

		Log.info("Kenan - Buscando configurações atuais da Journals.");
		listaSAP = kenan.kenanBuscaConfiguracoesJnls();

		Log.info("Kenan - Buscando ultimo lote processado da Journals.");
		lote = kenan.kenanBuscarLotesJnls();

		Log.info("Kenan - Buscando divisões pré configuradas no Kenan.");
		divisoes = kenan.kenanBuscarDivisoes();

		Log.info("Kenan - Buscando ordens internas pré configuradas no Kenan.");
		ordensInternas = kenan.kenanBuscaOrdemInterna();

		Log.info("Kenan - Buscando centro de custos pré configuradas no Kenan.");
		centrosCusto = kenan.kenanBuscaCentroDeCusto();

		try {
			Log.info("Kenan - Buscando quantidade de paginas da query da cont det.");
			qtdPaginas = kenan.kenanBuscaContDetQtdPaginas(lote.getLote());

			Log.info("Kenan - Buscando lançamentos referente ao último lote "+lote.getLote()+" na GVT_KENAN_SAP_SPED_CONT_DET.");
			for (int i = 1; i <= qtdPaginas; i++) {
				contDetList = kenan.kenanBuscarContDet(lote.getLote(),i);
				Log.info("Kenan - Encontrada pagina "+i+" de "+qtdPaginas+" com "+contDetList.size()+" registros do lote "+lote.getLote());
				try {
					tmdc.executar(contDetList, new ThreadBatimento2( cenario,tmdc,contDetList.size(),divisoes,ordensInternas), cenario.getQuantidadeThreads(), Connections.CONN_KENAN_CT+1, Connections.CONN_KENAN_CT+2 ,Connections.CONN_PROATIVO);
				} catch (NullPointerException e) {
					Log.info("Erro NullPointerException");
				}

				Log.info("Nº total de rejeitados: "+listaRejeitados.size());		
			}
		} catch (NullPointerException e) {
			Log.info("WARNING: Não há lotes da Journals do mês atual para validar.");
		}		
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
			Log.info("Util - Arquivo de configuração MONITOR_CONTABIL_CONF não existe no caminho."+ " Erro: " + e.getCause());
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

		try {
			proativo.proativoInsereControleExecucao(cenario.getIdExecucao(), listaRejeitados.size(), lote.getLote());
		}catch (NullPointerException e) {
			Log.info("WARNING - Não há lotes na Journals para inserir na Controle.");
			proativo.proativoInsereControleExecucao(cenario.getIdExecucao(), listaRejeitados.size(), "LOTE INVALIDO");
		}
			
		super.setChanged();
		super.notifyObservers();
	}
}
