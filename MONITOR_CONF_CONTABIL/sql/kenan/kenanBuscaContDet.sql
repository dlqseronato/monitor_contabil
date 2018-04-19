SELECT * FROM (SELECT a.*, rownum r__
FROM(
SELECT 
SC.EMPRESA_MKT_CODE, 
SC.DATA_DOCUMENTO,
SC.TIPO_LANCAMENTO, 
SC.ID_TYPE2, 
SC.CONTA_CONTABIL_CR, 
SC.CONTA_CONTABIL_DB, 
SC.EXTERNAL_ID, 
SC.ACCOUNT_CATEGORY, 
SC.OPEN_ITEM_ID, 
SC.USE_CODE, 
SC.COD_ATRIBUICAO, 
SC.DIVISAO, 
SC.CENTRO_CUSTO, 
SC.ELEMENT, 
SC.ORDEM_INTERNA, 
SC.TECNOLOGIA_OFICIAL ||' - '|| D.DESCRIPTION_TEXT as TECNOLOGIA_OFICIAL

  FROM arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET SC


  LEFT OUTER JOIN USAGE_TYPES UT
    ON SC.TIPO_LANCAMENTO = 4
   AND UT.TYPE_ID_USG = SC.ELEMENT

  LEFT OUTER JOIN DESCRIPTIONS D
    ON D.LANGUAGE_CODE = 2
   AND D.Description_Code = CASE
           WHEN SC.Tipo_Lancamento = 4 THEN
            UT.Description_Code
       
           WHEN SC.Tipo_Lancamento = 7 AND
                SC.Id_Type2 = 7 THEN
            UT.Description_Code

           WHEN LENGTH(SC.Element) IN (3, 4) THEN
            UT.Description_Code
       
           ELSE
            SC.Element
       END
WHERE LOTE = ?
) a
    WHERE rownum < ((? * 1000000) + 1 ))
WHERE r__ >= (((?-1) * 1000000) + 1);