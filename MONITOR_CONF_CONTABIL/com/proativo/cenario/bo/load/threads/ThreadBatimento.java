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

public class ThreadBatimento extends ActionAbstract<ProdutoVerdadeVo> {
	private OraKenan kenan;
	private OraProativo proativo;
	List<ProdutoSAPVo> listaSAP;

	public ThreadBatimento(CenarioVo cenario, ThreadManagerDynamicConnection tmdc,Integer qtdeCasos,List<ProdutoSAPVo> listaSAP) {
		super();
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
		ImpostoVo iOb;
		boolean rowFixed = false;
		int validarImposto;

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -3);
		newOb.setErros( new ErroVo());

		//Cargas auxiliares à decisão de montagem da conta contábil de imposto
		//lTaxType = kenan.kenanBuscaTaxTypeCode(ob);
		lTaxType = new ArrayList<Integer>();
		//validarImposto.kenan.


		//Itera o objeto da tabela verdade dentro da tabela full da journals para bater as informações
		for (ProdutoSAPVo sapOb : listaSAP) {

			if (	(sapOb.getJnlsIdValue() == newOb.getIdProdutoKenan() || sapOb.getJnlsIdValue2() == newOb.getIdProdutoKenan() || sapOb.getJnlsIdType() == 7) 
					&& ((sapOb.getJnlsAccountCategory() == newOb.getAccountCategory()) || sapOb.getJnlsAccountCategory() == 0) 
					&& (sapOb.getJnlsIdType() == 1 || sapOb.getJnlsIdType() == 3 || sapOb.getJnlsIdType() == 4 || sapOb.getJnlsIdType() == 6 || sapOb.getJnlsIdType() == 7) 
					//&& (sapOb.getJnlsInactiveDate() == null || sapOb.getJnlsInactiveDate().after(cal.getTime())) 
					&& (sapOb.getJnlsInactiveDate() == null )
					) 
			{
				//RC ID TYPE = 3 , USO ID TYPE = 4 E NRC ID TYPE = 6
				if(sapOb.getJnlsIdType() == 3 || sapOb.getJnlsIdType() == 4 || sapOb.getJnlsIdType() == 6 ) {
					//PRODUTO FATURADO USE CODE = 1
					if(sapOb.getJnlsUseCode() == 1 	&& ((sapOb.getJnlsOpenItemId() == newOb.getOpenItemIdFaturada()) || sapOb.getJnlsOpenItemId() == 0 || newOb.getOpenItemIdFaturada() == 0) ) {
						newOb.setIdType(sapOb.getJnlsIdType());
						newOb.setIdType2(sapOb.getJnlsIdType2());
						if (sapOb.getJnlsCContabilDebito() != newOb.getcContabilDebitoFaturada()) {
							//ERRO 2: CONTA CONTÁBIL DÉBITO DIFERENTE DA JOURNALS
							if(newOb.getcContabilDebitoFaturada() == 0 || newOb.getcContabilDebitoFaturada() == -1) {
								newOb.setcContabilDebitoFaturada(sapOb.getJnlsCContabilDebito());
								rowFixed = true;
							}else {
								errorOb = new ErroVo(newOb.getId(),newOb.getIdProdutoKenan(),newOb.getDescricaoProduto(),sapOb.getJnlsCodeId(),sapOb.getJnlsUseCode(),sapOb.getJnlsIdType(),sapOb.getJnlsAccountCategory(),sapOb.getJnlsOpenItemId(),2);
								addToRejectList(errorOb,newOb, sapOb, 2);
							}

						}
						if (sapOb.getJnlsCContabilCredito() != newOb.getcContabilCreditoFaturada()) {
							//ERRO 3: CONTA CONTÁBIL CRÉDITO DIFERENTE DA JOURNALS
							if(newOb.getcContabilCreditoFaturada() == 0 || newOb.getcContabilCreditoFaturada() == -1) {
								newOb.setcContabilCreditoFaturada(sapOb.getJnlsCContabilCredito());
								rowFixed = true;
							}else {
								errorOb = new ErroVo(newOb.getId(),newOb.getIdProdutoKenan(),newOb.getDescricaoProduto(),sapOb.getJnlsCodeId(),sapOb.getJnlsUseCode(),sapOb.getJnlsIdType(),sapOb.getJnlsAccountCategory(),sapOb.getJnlsOpenItemId(),3);
								addToRejectList(errorOb,newOb, sapOb, 3);
							}
						}
					}
					//PRODUTO A FATURAR USE CODE = 2
					if(sapOb.getJnlsUseCode() == 2 	&& ((sapOb.getJnlsOpenItemId() == newOb.getOpenItemIdAFaturar()) || sapOb.getJnlsOpenItemId() == 0 || newOb.getOpenItemIdAFaturar() == 0) ) {
						newOb.setIdType(sapOb.getJnlsIdType());
						newOb.setIdType2(sapOb.getJnlsIdType2());
						if (sapOb.getJnlsCContabilDebito() != newOb.getcContabilDebitoAFaturar()) {
							//ERRO 4: CONTA CONTÁBIL DÉBITO DIFERENTE DA JOURNALS
							if(newOb.getcContabilDebitoAFaturar() == 0 || newOb.getcContabilDebitoAFaturar() == -1) {
								newOb.setcContabilDebitoAFaturar(sapOb.getJnlsCContabilDebito());
								rowFixed = true;
							}else {
								errorOb = new ErroVo(newOb.getId(),newOb.getIdProdutoKenan(),newOb.getDescricaoProduto(),sapOb.getJnlsCodeId(),sapOb.getJnlsUseCode(),sapOb.getJnlsIdType(),sapOb.getJnlsAccountCategory(),sapOb.getJnlsOpenItemId(),4);
								addToRejectList(errorOb,newOb, sapOb, 4);
							}
						}
						if (sapOb.getJnlsCContabilCredito() != newOb.getcContabilCreditoAFaturar()) {
							//ERRO 5: CONTA CONTÁBIL CRÉDITO DIFERENTE DA JOURNALS
							if(newOb.getcContabilCreditoAFaturar() == 0 || newOb.getcContabilCreditoAFaturar() == -1) {
								newOb.setcContabilCreditoAFaturar(sapOb.getJnlsCContabilCredito());
								rowFixed = true;
							}else {
								errorOb = new ErroVo(newOb.getId(),newOb.getIdProdutoKenan(),newOb.getDescricaoProduto(),sapOb.getJnlsCodeId(),sapOb.getJnlsUseCode(),sapOb.getJnlsIdType(),sapOb.getJnlsAccountCategory(),sapOb.getJnlsOpenItemId(),5);
								addToRejectList(errorOb,newOb, sapOb, 5);
							}
						}
					}
				}
				//AJUSTES ID TYPE = 1
				if(sapOb.getJnlsIdType() == 1) {
					newOb.setIdType(sapOb.getJnlsIdType());
					//AJUSTE FATURADO USE CODE = 1
					if(sapOb.getJnlsUseCode() == 1 ) {
						newOb.setIdType(sapOb.getJnlsIdType());
						newOb.setIdType2(sapOb.getJnlsIdType2());
						//ERRO 6: CONTA CONTÁBIL DÉBITO DIFERENTE DA JOURNALS
						if (sapOb.getJnlsCContabilDebito() != newOb.getcContabilAdjDebitoFaturada()) {
							if(newOb.getcContabilAdjDebitoFaturada() == 0 || newOb.getcContabilAdjDebitoFaturada() == -1) {
								newOb.setcContabilAdjDebitoFaturada(sapOb.getJnlsCContabilDebito());
								rowFixed = true;
							}else {
								errorOb = new ErroVo(newOb.getId(),newOb.getIdProdutoKenan(),newOb.getDescricaoProduto(),sapOb.getJnlsCodeId(),sapOb.getJnlsUseCode(),sapOb.getJnlsIdType(),sapOb.getJnlsAccountCategory(),sapOb.getJnlsOpenItemId(),6);
								addToRejectList(errorOb,newOb, sapOb, 6);
							}
						}
						//ERRO 7: CONTA CONTÁBIL CRÉDITO DIFERENTE DA JOURNALS
						if (sapOb.getJnlsCContabilCredito() != newOb.getcContabilAdjCreditoFaturada()) {						
							if(newOb.getcContabilAdjCreditoFaturada() == 0 || newOb.getcContabilAdjCreditoFaturada() == -1) {
								newOb.setcContabilAdjCreditoFaturada(sapOb.getJnlsCContabilCredito());
								rowFixed = true;
							}else {
								errorOb = new ErroVo(newOb.getId(),newOb.getIdProdutoKenan(),newOb.getDescricaoProduto(),sapOb.getJnlsCodeId(),sapOb.getJnlsUseCode(),sapOb.getJnlsIdType(),sapOb.getJnlsAccountCategory(),sapOb.getJnlsOpenItemId(),7);
								addToRejectList(errorOb,newOb, sapOb, 7);
							}
						}
					}
					//AJUSTE A FATURAR USE CODE = 2
					if(sapOb.getJnlsUseCode() == 2 	&& ((sapOb.getJnlsOpenItemId() == newOb.getOpenItemIdAdjAFaturar()) || sapOb.getJnlsOpenItemId() == 0 || newOb.getOpenItemIdAdjAFaturar() == 0 ) ) {
						newOb.setIdType(sapOb.getJnlsIdType());
						newOb.setIdType2(sapOb.getJnlsIdType2());
						//	BATIMENTO AJUSTE CONTA CONTÁBIL DÉBITO A FATURAR
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
			erro.setMsgError("Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil débito faturada "+ob.getcContabilDebitoFaturada()+" está diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 3:
			erro.setMsgError("Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil crédito faturada "+ob.getcContabilCreditoFaturada()+" está diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		case 4:
			erro.setMsgError("Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil débito a faturar "+ob.getcContabilDebitoAFaturar()+" está diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 5:
			erro.setMsgError("Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil crédito a faturar "+ob.getcContabilCreditoAFaturar()+" está diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		case 6:
			erro.setMsgError("Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil ajuste débito faturada "+ob.getcContabilAdjDebitoFaturada()+" está diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 7:
			erro.setMsgError("Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil ajuste crédito faturada "+ob.getcContabilAdjCreditoFaturada()+" está diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		case 8:
			erro.setMsgError("Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil ajuste débito a faturar "+ob.getcContabilAdjDebitoAFaturar()+" está diferente da Journals " + sapOb.getJnlsCContabilDebito());
			break;
		case 9:
			erro.setMsgError("Jnl Code ID: "+sapOb.getJnlsCodeId()+" A conta contábil ajuste crédito a faturar "+ob.getcContabilAdjCreditoAFaturar()+" está diferente da Journals " + sapOb.getJnlsCContabilCredito());
			break;
		default:
			break;
		}
		Load.addLoadRejectList(erro);
	}

}
