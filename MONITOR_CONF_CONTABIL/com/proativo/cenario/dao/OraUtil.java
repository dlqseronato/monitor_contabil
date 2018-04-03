package com.proativo.cenario.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		JFileChooser fc; 
		try {
			Files.createDirectories(Paths.get("/app/gvt/scripts/proativo/cenarios/MONITOR_CONF_CONTABIL/input"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//File f = new File("/app/gvt/scripts/proativo/cenarios/MONITOR_CONF_CONTABIL/input/GARANTIA_CONTABIL_CONF.xls");
		File f = new File("D:/GARANTIA_CONTABIL_CONF.xls");
		JButton open = new JButton();
		fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("/app/gvt/scripts/proativo/cenarios/MONITOR_CONF_CONTABIL/input/"));
		fc.setDialogTitle("Buscar tabela verdade");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Excel", "xls");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(filter);

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
