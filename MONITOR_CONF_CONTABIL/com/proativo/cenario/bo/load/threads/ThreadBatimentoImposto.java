package com.proativo.cenario.bo.load.threads;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.proativo.cenario.bo.load.Load;
import com.proativo.cenario.dao.OraKenan;
import com.proativo.cenario.dao.OraProativo;
import com.proativo.cenario.vo.ErroVo;
import com.proativo.cenario.vo.ImpostoVo;
import com.proativo.cenario.vo.ProdutoSAPVo;
import com.proativo.cenario.vo.ProdutoVerdadeVo;
import com.proativo.util.connection.Connections;
import com.proativo.util.thread.ActionAbstract;
import com.proativo.util.thread.ThreadManagerDynamicConnection;
import com.proativo.util.vo.CenarioVo;

public class ThreadBatimentoImposto extends ActionAbstract<ProdutoVerdadeVo> {
	private OraKenan kenan;
	private OraProativo proativo;
	List<ProdutoSAPVo> listaSAP;
	ErroVo erros;
	List<ErroVo> list;

	public ThreadBatimentoImposto(CenarioVo cenario, ThreadManagerDynamicConnection tmdc,Integer qtdeCasos,List<ProdutoSAPVo> listaSAP) {
		super();
		this.list = new ArrayList<ErroVo>();
		this.tmdc = tmdc;
		this.cenario = cenario;
		this.kenan = new OraKenan();
		this.proativo = new OraProativo();
		this.totalLista = qtdeCasos.floatValue();
		this.listaSAP = listaSAP;
	}

	@Override
	public void exec(ProdutoVerdadeVo ob) {	
		ProdutoVerdadeVo newOb = ob;
		List<Integer> lTaxType;
		String vPrefix = null;
		ErroVo errorOb;
		erros = new ErroVo();
		ImpostoVo iOb;
		boolean rowFixed = false;
		int validarImposto;

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -3);
		newOb.setErros( new ErroVo());

		//Cargas auxiliares à decisão de montagem da conta contábil de imposto
		lTaxType = kenan.kenanBuscaTaxTypeCode(ob);
		//validarImposto.kenan.


		//Itera o objeto da tabela verdade dentro da tabela full da journals para bater as informações
		for (ProdutoSAPVo sapOb : listaSAP) {

				//IMPOSTOS ID TYPE = 7
				if(sapOb.getJnlsIdType() == 1000) {
					if(sapOb.getJnlsTaxTypeCode() != 0 && sapOb.getJnlsTaxTypeCode() != -1 && lTaxType.contains(sapOb.getJnlsTaxTypeCode())) {
						for (Integer taxTypeCode : lTaxType) {
							iOb = new ImpostoVo();

							if(taxTypeCode == sapOb.getJnlsTaxTypeCode()) {
								newOb.setIdType(sapOb.getJnlsIdType());
								//IMPOSTO FATURADO USE CODE = 1
								if(sapOb.getJnlsUseCode() == 1 ) {
									newOb.setUseCode(sapOb.getJnlsUseCode());
									if(sapOb.getJnlsTaxTypeCode() >= 1000000 && sapOb.getJnlsTaxTypeCode() <= 1999999) {
										//PREPARANDO PREFIXO DA CONTA CONTABIL DE IMPOSTO
										if(		newOb.getOpenItemIdImpFaturada() == 90 || newOb.getOpenItemIdImpFaturada() == 91
												||	newOb.getOpenItemIdImpFaturada() == 92 || newOb.getOpenItemIdImpFaturada() == 99
												||	newOb.getOpenItemIdImpFaturada() == 100 || newOb.getOpenItemIdImpFaturada() == 101) 
											vPrefix = "31986174";
										else
											vPrefix = "817";
									}else if(sapOb.getJnlsTaxTypeCode() >= 2000000 && sapOb.getJnlsTaxTypeCode() <= 2999999) {
										//PREPARANDO PREFIXO DA CONTA CONTABIL DE IMPOSTO
										if(		newOb.getOpenItemIdImpFaturada() == 90 || newOb.getOpenItemIdImpFaturada() == 91
												||	newOb.getOpenItemIdImpFaturada() == 92 || newOb.getOpenItemIdImpFaturada() == 99
												||	newOb.getOpenItemIdImpFaturada() == 100 || newOb.getOpenItemIdImpFaturada() == 101) 
											vPrefix = "31986171";
										else
											vPrefix = "814";
									}
									
									if(kenan.kenanBuscaQtClassificacaoTv(newOb)) {
										if(vPrefix == null) {
											if(vPrefix.length() == 3) {
												vPrefix = vPrefix.substring(0, 3) + String.valueOf(sapOb.getJnlsCContabilCredito()).substring(3);
											}
										}else {
											vPrefix =  String.valueOf(newOb.getcContabilDebitoFaturada());
										}
									}else {
										vPrefix =  String.valueOf(newOb.getcContabilDebitoFaturada());
									}
									
									
								}
								//IMPOSTO A FATURAR USE CODE = 2
								if(sapOb.getJnlsUseCode() == 2  ) {
									//	BATIMENTO IMPOSTO CONTA CONTÁBIL DÉBITO A FATURAR
									if (sapOb.getJnlsCContabilDebito() != newOb.getcContabilAdjDebitoAFaturar()) {
										//ERRO 8: CONTA CONTÁBIL DÉBITO DIFERENTE DA JOURNALS
										if(newOb.getcContabilAdjDebitoAFaturar() == 0 || newOb.getcContabilAdjDebitoAFaturar() == -1) {
											newOb.setcContabilAdjDebitoAFaturar(sapOb.getJnlsCContabilDebito());
											rowFixed = true;
										}else {
											errorOb = new ErroVo(newOb.getId(),newOb.getIdProdutoKenan(),newOb.getDescricaoProduto(),sapOb.getJnlsCodeId(),sapOb.getJnlsUseCode(),sapOb.getJnlsIdType(),sapOb.getJnlsAccountCategory(),sapOb.getJnlsOpenItemId(),8);
											addToRejectList(errorOb,newOb, sapOb, 8);
										}
									}
									//  BATIMENTO AJUSTE CONTA CONTÁBIL DÉBITO A FATURAR
									if (sapOb.getJnlsCContabilCredito() != newOb.getcContabilAdjCreditoAFaturar()) {
										//ERRO 9: CONTA CONTÁBIL DÉBITO DIFERENTE DA JOURNALS
										if(newOb.getcContabilAdjCreditoAFaturar() == 0 || newOb.getcContabilAdjCreditoAFaturar() == -1) {
											newOb.setcContabilAdjCreditoAFaturar(sapOb.getJnlsCContabilCredito());
											rowFixed = true;
										}else {
											errorOb = new ErroVo(newOb.getId(),newOb.getIdProdutoKenan(),newOb.getDescricaoProduto(),sapOb.getJnlsCodeId(),sapOb.getJnlsUseCode(),sapOb.getJnlsIdType(),sapOb.getJnlsAccountCategory(),sapOb.getJnlsOpenItemId(),9);
											addToRejectList(errorOb,newOb, sapOb, 9);
										}
									}
								}

							}
						}
					}
				}
			}
		
		//Itera o objeto da tabela verdade dentro da tabela full da journals para bater as informações

		if (rowFixed) {
			proativo.proativoAtualizaTabelaVerdade(newOb, tmdc.getAvailableConnection(Connections.CONN_PROATIVO));
			Load.addLoadCorrectList(newOb);
		}
		atualizarProgresso(10);
	}

	public void addToRejectList(ErroVo erro,ProdutoVerdadeVo ob,ProdutoSAPVo sapOb,int codigoErro) {
		String tipoConta = "";

		switch (codigoErro) {
		case 2:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil débito faturada "+ob.getcContabilDebitoFaturada()+" está diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 3:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil crédito faturada "+ob.getcContabilCreditoFaturada()+" está diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		case 4:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil débito a faturar "+ob.getcContabilDebitoAFaturar()+" está diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 5:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil crédito a faturar "+ob.getcContabilCreditoAFaturar()+" está diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		case 6:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil ajuste débito faturada "+ob.getcContabilAdjDebitoFaturada()+" está diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 7:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil ajuste crédito faturada "+ob.getcContabilAdjCreditoFaturada()+" está diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		case 8:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil ajuste débito a faturar "+ob.getcContabilAdjDebitoAFaturar()+" está diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 9:
			erro.setMsgError("ID: "+ob.getId()+" Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil ajuste crédito a faturar "+ob.getcContabilAdjCreditoAFaturar()+" está diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		default:
			break;
		}
		Load.addLoadRejectList(erro);
	}

}
