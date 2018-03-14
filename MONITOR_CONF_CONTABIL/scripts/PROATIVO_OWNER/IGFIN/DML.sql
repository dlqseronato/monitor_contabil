Insert into PROATIVO_OWNER.PROATIVO_GRUPOS_EMAIL  (ID,NOME_GRUPO,EMAIL_LOG,EMAIL_PARA,EMAIL_CC) values ((select max(id) + 1 from PROATIVO_OWNER.PROATIVO_GRUPOS_EMAIL),'Email Monitor Contabil','cenario.de.ciclo@gvt.com.br','cenario.de.ciclo@gvt.com.br;Jhony.Cardoso@gvt.com.br;Evelize.Santos@gvt.com.br;Ranieri.Mocelin@gvt.com.br','diego.seronato@gvt.com.br');
Insert into PROATIVO_OWNER.PROATIVO_CENARIOS (ID_CENARIO,CENARIO,SEGMENTO,DESCRICAO,DATA_CRIACAO,DATA_INATIVACAO,CENARIO_OFICIAL,ID_GRUPO_EMAIL,COMPLEMENTO,CATEGORIA,DATA_RESPOSTA,DONO,NUMERO,RESPONSAVEL,RESPOSTA,STATUS) values ((select max(id_cenario) + 1 from PROATIVO_OWNER.PROATIVO_CENARIOS),'MONITOR_CONF_CONTABIL','R','Verifica diverg�ncias entre tabela verdade de contabilidade e journals',SYSDATE,null,'0',(select id from PROATIVO_OWNER.PROATIVO_GRUPOS_EMAIL where nome_grupo = 'Email Monitor Contabil'),null,null,null,null,null,null,null,null);
Insert into PROATIVO_OWNER.PROATIVO_CONTROLE_EXECUCAO (ID,ID_CENARIO,PERIODICIDADE,TIPO_EXECUCAO,EXECUTAVEL,QUANTIDADE_THREADS,QUANTIDADE_ERROS,RELATORIO,GERAR_SCRIPT) values ((select max(id) + 1 from PROATIVO_OWNER.PROATIVO_CONTROLE_EXECUCAO),(SELECT ID_CENARIO FROM PROATIVO_OWNER.PROATIVO_CENARIOS where cenario = 'MONITOR_CONF_CONTABIL'),'1','2','1','50','2','0','0');