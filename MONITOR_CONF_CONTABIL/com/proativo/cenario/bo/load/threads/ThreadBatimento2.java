package com.proativo.cenario.bo.load.threads;



import java.util.List;
import com.proativo.cenario.bo.load.Load;
import com.proativo.cenario.dao.OraKenan;
import com.proativo.cenario.dao.OraProativo;
import com.proativo.cenario.vo.CentroDeCustoVo;
import com.proativo.cenario.vo.ContDetVo;
import com.proativo.cenario.vo.DivisaoVo;
import com.proativo.cenario.vo.ErroVo;
import com.proativo.cenario.vo.OrdemInternaVo;
import com.proativo.util.log.Log;
import com.proativo.util.thread.ActionAbstract;
import com.proativo.util.thread.ThreadManagerDynamicConnection;
import com.proativo.util.vo.CenarioVo;

public class ThreadBatimento2 extends ActionAbstract<ContDetVo> {
	private OraKenan kenan;
	private OraProativo proativo;

	List<DivisaoVo> divisoes;
	List<OrdemInternaVo> ordensInternas;

	public ThreadBatimento2(CenarioVo cenario, ThreadManagerDynamicConnection tmdc,Integer qtdeCasos, List<DivisaoVo> divisoes,List<OrdemInternaVo> ordensInternas) {
		super();
		this.tmdc = tmdc;
		this.cenario = cenario;
		this.kenan = new OraKenan();
		this.proativo = new OraProativo();
		this.totalLista = qtdeCasos.floatValue();
		this.ordensInternas = ordensInternas;
		this.divisoes = divisoes;
	}

	@Override
	public void exec(ContDetVo ob) {	
		String divisao;	
		ErroVo erro;
		try {
			

		if(	ob.getCentroCusto() != null && ob.getOrdemInterna() != null && ob.getDivisao() != null
				&& !ob.getCentroCusto().isEmpty() && !ob.getOrdemInterna().isEmpty() && !ob.getDivisao().isEmpty()
				) {
			for (DivisaoVo div : divisoes) {
				if(ob.getEmpresaMktCode() == div.getMktCode()) {
					int codUf = Integer.parseInt(div.getDivisao().substring(0, 2));
					String uf = div.getDivisao().substring(2);
					String ordemInterna;
					String centroCusto;

					switch (ob.getOpenItemId() ) {
					case 20:	//20 - TDATA
						if (!ob.getDivisao().equals("29SP")) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),10);
							addToRejectList(erro, ob, erro.getErrorType(),"29SP","","");
						}
						ordemInterna = "D29"+div.getDivisao();
						if(!ob.getOrdemInterna().equals(ordemInterna)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),11);
							addToRejectList(erro, ob, erro.getErrorType(),"29SP",ordemInterna,"");
						}
						centroCusto = "29D"+buscaCentroDeCusto(ob.getAccountCategory());
						if(!ob.getCentroCusto().equals(centroCusto)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),12);
							addToRejectList(erro, ob, erro.getErrorType(),"29SP",ordemInterna,centroCusto);
						}
						break;
					case 21:	//21 - TDATA
						if (!ob.getDivisao().equals("29SP")) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),10);
							addToRejectList(erro, ob, erro.getErrorType(),"29SP","","");
						}
						ordemInterna = "D29"+div.getDivisao();
						if(!ob.getOrdemInterna().equals(ordemInterna)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),11);
							addToRejectList(erro, ob, erro.getErrorType(),"29SP",ordemInterna,"");
						}
						centroCusto = "29D"+buscaCentroDeCusto(ob.getAccountCategory());
						if(!ob.getCentroCusto().equals(centroCusto)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),12);
							addToRejectList(erro, ob, erro.getErrorType(),"29SP",ordemInterna,centroCusto);
						}
						break;
					case 90:	//90 - TV DTH
						if (!ob.getDivisao().equals("30PR")) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),10);
							addToRejectList(erro, ob, erro.getErrorType(),"30PR","","");			
						}
						ordemInterna = "T30T"+div.getDivisao();
						if(!ob.getOrdemInterna().equals(ordemInterna)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),11);
							addToRejectList(erro, ob, erro.getErrorType(),"30PR",ordemInterna,"");
						}
						centroCusto = "30T"+buscaCentroDeCusto(ob.getAccountCategory());
						if(!ob.getCentroCusto().equals(centroCusto)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),12);
							addToRejectList(erro, ob, erro.getErrorType(),"30PR",ordemInterna,centroCusto);
						}
						break;
					case 93:	//93 - POP
						if (!ob.getDivisao().equals("30PR")) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),10);
							addToRejectList(erro, ob, erro.getErrorType(),"30PR","","");	
						}
						ordemInterna = "P30"+div.getDivisao();
						if(!ob.getOrdemInterna().equals(ordemInterna)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),11);
							addToRejectList(erro, ob, erro.getErrorType(),"30PR",ordemInterna,"");
						}
						centroCusto = "30P"+buscaCentroDeCusto(ob.getAccountCategory());
						if(!ob.getCentroCusto().equals(centroCusto)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),12);
							addToRejectList(erro, ob, erro.getErrorType(),"30PR",ordemInterna,centroCusto);
						}
						break;
					case 99:	//99 - IPTV
						if (!ob.getDivisao().equals(div.getDivisao())) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),10);
							addToRejectList(erro, ob, erro.getErrorType(),div.getDivisao(),"","");
						}
						ordemInterna = "T"+codUf+"T"+div.getDivisao();
						if(!ob.getOrdemInterna().equals(ordemInterna)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),11);
							addToRejectList(erro, ob, erro.getErrorType(),div.getDivisao(),ordemInterna,"");
						}
						centroCusto = codUf+"T"+buscaCentroDeCusto(ob.getAccountCategory());
						//centroCusto = "30T"+buscaCentroDeCusto(ob.getAccountCategory());
						if(!ob.getCentroCusto().equals(centroCusto)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),12);
							addToRejectList(erro, ob, erro.getErrorType(),div.getDivisao(),ordemInterna,centroCusto);
						}
						break;
					default:
						if (!ob.getDivisao().equals(div.getDivisao())) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),10);
							addToRejectList(erro, ob, erro.getErrorType(),div.getDivisao(),"","");
						}

						ordemInterna = "T"+codUf+buscaOrdemInterna(ob.getElement());
						if(!ob.getOrdemInterna().equals(ordemInterna)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),11);
							addToRejectList(erro, ob, erro.getErrorType(),div.getDivisao(),ordemInterna,"");
						}


						centroCusto = codUf+"T"+buscaCentroDeCusto(ob.getAccountCategory());
						if(!ob.getCentroCusto().equals(centroCusto)) {
							erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),12);
							addToRejectList(erro, ob, erro.getErrorType(),div.getDivisao(),ordemInterna,centroCusto);

						}
						break;
					}
				}

			}	
		}else {
			if( ob.getCentroCusto() == null || ob.getCentroCusto().isEmpty() ) {
				erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),13);
				addToRejectList(erro, ob, erro.getErrorType(),"","","");
			}		
			if( ob.getOrdemInterna() == null || ob.getOrdemInterna().isEmpty() ) {
				erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),14);
				addToRejectList(erro, ob, erro.getErrorType(),"","","");
			}	
			if(	 ob.getDivisao() == null || ob.getDivisao().isEmpty()) {	
				erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),15);
				addToRejectList(erro, ob, erro.getErrorType(),"","","");
			}
			/*
			if(	ob.getOpenItemId() == 0) {
				erro = new ErroVo(ob.getId(),ob.getElement(),0,0,ob.getTipoLancamento(),ob.getAccountCategory(),ob.getOpenItemId(),16);
				addToRejectList(erro, ob, erro.getErrorType(),"","","");
			}*/


		}
		} catch (NullPointerException e) {
			System.out.println("Produto: "+ob.getId()+" do tipo "+ob.getTecnologia()+" Erro: Possui algum objeto nulo. Divisão "+ob.getDivisao() +" Ordem interna "+ob.getOrdemInterna() +" Centro de custo "+ob.getCentroCusto() );
		}


		atualizarProgresso(10);
	}

	public void addToRejectList(ErroVo erro,ContDetVo ob,int codigoErro,String divisaoCorreta,String ordemInternaCorreta, String centroCustoCorreto) {
		switch (codigoErro) {
		case 10:
			erro.setMsgError("Produto: "+ob.getId()+" do tipo "+ob.getTecnologia()+" Erro: Divisão "+ob.getDivisao() +" está incorreta. A correta é "+divisaoCorreta);
			break;
		case 11:
			erro.setMsgError("Produto: "+ob.getId()+" Tipo: "+ob.getTecnologia()+" Erro: Ordem interna "+ob.getOrdemInterna() +" está incorreta. A correta é "+ordemInternaCorreta);
			break;
		case 12:
			erro.setMsgError("Produto: "+ob.getId()+" Tipo: "+ob.getTecnologia()+" Erro: Centro de custo "+ob.getCentroCusto() +" está incorreto. O correto é "+centroCustoCorreto);
			break;
		case 13:
			erro.setMsgError("Produto: "+ob.getId()+" Tipo: "+ob.getTecnologia()+" Erro: Centro de custo nulo na ContDet.");
			break;
		case 14:
			erro.setMsgError("Produto: "+ob.getId()+" Tipo: "+ob.getTecnologia()+" Erro: Ordem interna nulo na ContDet.");
			break;
		case 15:
			erro.setMsgError("Produto: "+ob.getId()+" Tipo: "+ob.getTecnologia()+" Erro: Divisao nula na ContDet.");
			break;
			/*
		case 16:
			erro.setMsgError("Produto: "+ob.getId()+" do tipo "+ob.getTecnologia()+" Erro: Open item = 0 na ContDet.");
			break;*/
		default:
			break;
		}
		Load.addLoadRejectList(erro);
	}

	public String buscaCentroDeCusto(int accountCategory) {
		for (CentroDeCustoVo centroCusto : Load.getCentrosDeCusto()) {
			if(centroCusto.getAccountCategory() == accountCategory)
				return centroCusto.getCentroDeCusto();
		}
		return null;
	}

	public String buscaOrdemInterna(int element) {
		for (OrdemInternaVo ordens : ordensInternas) {
			if(ordens.getElement() == element) {
				return ordens.getOrdemInterna();
			}
		}
		return null;
	}
}
