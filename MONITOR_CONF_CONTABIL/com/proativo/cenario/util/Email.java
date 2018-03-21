package com.proativo.cenario.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;

import com.proativo.cenario.dao.OraProativo;
import com.proativo.cenario.vo.ErroVo;
import com.proativo.util.Mailer;
import com.proativo.util.log.Log;
import com.proativo.util.vo.CenarioVo;

public class Email {
	public static void enviaEmail(CenarioVo cenario,int total, int ok, int nok, List<ErroVo> rejectList) {
		try{

			OraProativo proativo = new OraProativo();
			
			String mailServer = null;
			String[] destinatarios = null;
			
			File f = Util.geraArquivoReport(rejectList,cenario,total,ok,nok);
			List<File> lsf = new ArrayList<File>();
			lsf.add(f);	
			String body = "";			
			body = 	"Execucao do Monitor de Configuração Contabil Finalizada!" +
					"<br><br>" +
					"ID de Execucao - " + cenario.getIdExecucao() +
					"<br><br>" +
					"Foram validadas " + total + " de produtos." +
					"<br><br>" +
					ok + " OK!" +
					"<br><br>" +
					nok + " erros encontrados!" +
					"<br><br>" +			
					"<br><br>";
				
						
			String subject = "Monitor de Configuracoes Contabeis";
			
			destinatarios = cenario.getEmailPara().toArray(new String[cenario.getEmailPara().size()]);
			

			mailServer = proativo.buscarParametro("emailServidor"); 
			for (String destinatario: destinatarios) {
				System.out.println(mailServer+"\n"+destinatario+"\n"+subject);	
			}
			Mailer.sendWithAttachments(mailServer, "monitor_conf_contabil@telefonica.com.br", destinatarios, subject, body, "text/html",lsf);
			
		} catch (AddressException e) {
			Log.error("Falha ao enviar e-mail de relatório", e);
		} catch (FileNotFoundException e) {
			Log.error("Falha ao enviar e-mail de relatório", e);
		} catch (IOException e) {
			Log.error("Falha ao enviar e-mail de relatório", e);
		} catch (Exception e) {
			Log.error("Falha ao enviar e-mail de relatório", e);
		}
	}
}
