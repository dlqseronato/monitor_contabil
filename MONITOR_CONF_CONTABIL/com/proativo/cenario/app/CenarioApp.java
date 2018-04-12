 package com.proativo.cenario.app;

import com.proativo.util.app.ProativoAppUtil;

public class CenarioApp extends ProativoAppUtil{
	
	//TODO: INFORMAR NUMERO DO CENARIO
	private static String CENARIO = "MONITOR_CONF_CONTABIL";
	private static String SEGMENTO = "R";
	
	
	public static void main(String[] args){
		executaAplicacao(CENARIO, SEGMENTO, args, CenarioApp.class);
	}	
}