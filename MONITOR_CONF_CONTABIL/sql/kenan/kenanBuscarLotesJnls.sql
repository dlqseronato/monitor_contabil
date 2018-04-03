
SELECT SC.LOTE,
       SUBSTR(SC.LOTE, 1, 3) PROCESSAMENTO,
       TO_CHAR(SC.DATA_INSERT_LOTE, 'YYYYMM') COMPETENCIA,
       JNL_REF_NO,
       JNL_REF_NO_SERV
  FROM (SELECT *
          FROM ARBORGVT_JOURNALS.Gvt_Kenan_Sap_Sped_Lotes
         WHERE 1 = 1
           AND H_INTERFACE_SAP = 'FII001'
           AND TO_CHAR(DATA_INSERT_LOTE, 'RRRRMM') = TO_CHAR(SYSDATE, 'RRRRMM')
         ORDER BY DATA_INSERT_LOTE DESC) SC
 WHERE ROWNUM = 1;