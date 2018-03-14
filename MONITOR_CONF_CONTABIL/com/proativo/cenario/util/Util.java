package com.proativo.cenario.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.proativo.cenario.vo.ErroVo;
import com.proativo.cenario.vo.ErroVo;
import com.proativo.util.log.Log;
import com.proativo.util.vo.CenarioVo;

public class Util {

	public static File geraArquivoReport(List<ErroVo> lista,CenarioVo cenario,int total, int ok, int nok) {
		FileWriter fw = null;
		PrintWriter pw = null;
		Date d = null;
		d = new Date (System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sdate= sdf.format(d);
		List<String> ls = new ArrayList<String>();
		try {
			fw = new FileWriter("d:\\MONITOR_CONTABIL_"+sdate+".txt");

			pw = new PrintWriter(fw);
			pw.printf("%s %s %s%n","RELATÓRIO DE CONFIGURAÇÕES CONTÁBEIS","Data da execução: ",sdate);
			pw.printf("%s %s %n","Id Execução:",cenario.getIdExecucao());
			pw.printf("%s %s%n%s %s%n%s %s%n", "Quantidade verificada:",total,"Quantidade Ok:",ok,"Quantidade de erros:",nok);
			pw.printf("%n%s%n", "ERRO: ID_VALUE NÃO ENCONTRADO NA JOURNALS");
			
			for (ErroVo ob : lista) {
				if(ob.getErrorType() == 1) {
					pw.printf("%s %d %s %d %s %d %s %s%n", "JNL_CODE_ID:",ob.getJnlsCodeId(),"ID_VALUE :",ob.getIdProdutoKenan(),"Error Type: ",ob.getErrorType(),"Description: ",ob.getMsgError());
				}
			}
			pw.printf("%n%s%n", "ERRO: RC - CONTA CONTABIL FATURADA DÉBITO INCORRETA");
			for (ErroVo ob : lista) {
				if(ob.getErrorType() == 2) {
					pw.printf("%s %d %s %d %s %d %s %s%n", "JNL_CODE_ID:",ob.getJnlsCodeId(),"ID_VALUE :",ob.getIdProdutoKenan(),"Error Type: ",ob.getErrorType(),"Description: ",ob.getMsgError());
				}
			}
			pw.printf("%n%s%n", "ERRO: RC - CONTA CONTABIL FATURADA CRÉDITO INCORRETA");
			for (ErroVo ob : lista) {
				if(ob.getErrorType() == 3) {
					pw.printf("%s %d %s %d %s %d %s %s%n", "JNL_CODE_ID:",ob.getJnlsCodeId(),"ID_VALUE :",ob.getIdProdutoKenan(),"Error Type: ",ob.getErrorType(),"Description: ",ob.getMsgError());
				}
			}
			pw.printf("%n%s%n", "ERRO: RC - CONTA CONTABIL A FATURAR DÉBITO INCORRETA");
			for (ErroVo ob : lista) {
				if(ob.getErrorType() == 4) {
					pw.printf("%s %d %s %d %s %d %s %s%n", "JNL_CODE_ID:",ob.getJnlsCodeId(),"ID_VALUE :",ob.getIdProdutoKenan(),"Error Type: ",ob.getErrorType(),"Description: ",ob.getMsgError());
				}
			}
			pw.printf("%n%s%n", "ERRO: RC - CONTA CONTABIL A FATURAR CRÉDITO INCORRETA");
			for (ErroVo ob : lista) {
				if(ob.getErrorType() == 5) {
					pw.printf("%s %d %s %d %s %d %s %s%n", "JNL_CODE_ID:",ob.getJnlsCodeId(),"ID_VALUE :",ob.getIdProdutoKenan(),"Error Type: ",ob.getErrorType(),"Description: ",ob.getMsgError());
				}
			}
			pw.printf("%n%s%n", "ERRO: ADJ - CONTA CONTABIL FATURADA DÉBITO INCORRETA");
			for (ErroVo ob : lista) {
				if(ob.getErrorType() == 6) {
					pw.printf("%s %d %s %d %s %d %s %s%n", "JNL_CODE_ID:",ob.getJnlsCodeId(),"ID_VALUE :",ob.getIdProdutoKenan(),"Error Type: ",ob.getErrorType(),"Description: ",ob.getMsgError());
				}
			}
			pw.printf("%n%s%n", "ERRO: ADJ - CONTA CONTABIL FATURADA CRÉDITO INCORRETA");
			for (ErroVo ob : lista) {
				if(ob.getErrorType() == 7) {
					pw.printf("%s %d %s %d %s %d %s %s%n", "JNL_CODE_ID:",ob.getJnlsCodeId(),"ID_VALUE :",ob.getIdProdutoKenan(),"Error Type: ",ob.getErrorType(),"Description: ",ob.getMsgError());
				}
			}
			pw.printf("%n%s%n", "ERRO: ADJ - CONTA CONTABIL A FATURAR DÉBITO INCORRETA");
			for (ErroVo ob : lista) {
				if(ob.getErrorType() == 8) {
					pw.printf("%s %d %s %d %s %d %s %s%n", "JNL_CODE_ID:",ob.getJnlsCodeId(),"ID_VALUE :",ob.getIdProdutoKenan(),"Error Type: ",ob.getErrorType(),"Description: ",ob.getMsgError());
				}
			}
			pw.printf("%n%s%n", "ERRO: ADJ - CONTA CONTABIL A FATURAR CRÉDITO INCORRETA");
			for (ErroVo ob : lista) {
				if(ob.getErrorType() == 9) {
					pw.printf("%s %d %s %d %s %d %s %s%n", "JNL_CODE_ID:",ob.getJnlsCodeId(),"ID_VALUE :",ob.getIdProdutoKenan(),"Error Type: ",ob.getErrorType(),"Description: ",ob.getMsgError());
				}
			}
		} catch (IOException e) {
			//Log.error("Não foi possível escrever arquivo.", e);
		}finally {
			try {
				pw.close();
				fw.close();
				return new File("d:\\MONITOR_CONTABIL_"+sdate+".txt");
			} catch (IOException e) {
				Log.error("Não foi possível fechar arquivo.", e);
			}
		}
		
		
		
		return null;
	}
}
