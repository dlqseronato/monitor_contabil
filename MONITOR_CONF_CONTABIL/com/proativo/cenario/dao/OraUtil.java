package com.proativo.cenario.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.proativo.util.log.Log;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import teste.Celula;
import teste.LinhaCelulas;
import teste.Planilha;

public class OraUtil {
	public Planilha buscardadosPlanilha() throws FileNotFoundException{
		// TODO Auto-generated method stub
		try {
			Files.createDirectories(Paths.get("/app/gvt/scripts/proativo/cenarios/monitor_conf_contabil/input"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Log.info("Não foi possível criar o diretório input.");
		}catch (Exception e2) {
			// TODO Auto-generated catch block
			Log.info("Não foi possível criar o diretório input.");
		}
		File f = new File("/app/gvt/scripts/proativo/cenarios/monitor_conf_contabil/input/GARANTIA_CONTABIL_CONF.xls");
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(f);
			Sheet sheet = workbook.getSheet(0);
			int linhas = sheet.getRows();
			int colunas = sheet.getColumns();
			Log.info("Iniciando a leitura da planilha XLS:");
			Celula celula = new Celula();
			List<Celula> celulas = new ArrayList<Celula>();
			LinhaCelulas linha = new LinhaCelulas();
			linha.setColunas(sheet.getColumns());
			List<LinhaCelulas> planilha = new ArrayList<LinhaCelulas>();
			Planilha p = new Planilha();
			for(int i = 0; i < linhas; i++){
				celulas = new ArrayList<Celula>();
				linha = new LinhaCelulas();
				for(int j = 0; j< colunas; j++) {
					celula = new Celula();
					celula.setCelula(sheet.getCell(j, i));
					celulas.add(celula);
				}

				linha.setLinhaCelulas(celulas);
				if(i == 0)
					p.setHeader(linha);
				planilha.add(linha);	
			}
			planilha.remove(0);
			p.setPlanilha(planilha);
			Log.info("Colunas encontradas na planilha: "+p.getPlanilha().get(0).getLinhaCelulas().size());
			Log.info("Linhas encontradas na planilha: "+p.getPlanilha().size());
			f.delete();
			return p;

		} catch (BiffException e) {
			Log.info("WARNING Util - Erro Biff ao buscardadosPlanilha.");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}catch (IOException e) {
			Log.info("WARNING Util - Erro IO ao buscardadosPlanilha.");
		}catch (Exception e) {
			Log.info("WARNING Util - Erro genérico ao buscardadosPlanilha.");
		}

		return null;
	}
}
