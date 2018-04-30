SELECT  JK.JNL_CODE_ID,D.DESCRIPTION_TEXT, JK.ID_TYPE, JK.ID_VALUE, JK.ID_TYPE2, JK.ID_VALUE2,
        JK.USE_CODE, JK.ACCOUNT_CATEGORY, ADJ_CATEGORY, ACTIVE_DATE, INACTIVE_DATE, 
        OPEN_ITEM_ID,NO_JNL_IND, TAX_TYPE_CODE, JC.FML_ACCT_DB, JC.FML_ACCT_CR
FROM    ARBOR.JNL_KEYS JK 
        INNER JOIN ARBOR.JNL_CUSTOM JC
        ON JK.JNL_CODE_ID = JC.JNL_CODE_ID
  LEFT OUTER JOIN USAGE_TYPES UT
   ON UT.TYPE_ID_USG = CASE WHEN JK.ID_TYPE = 1 THEN JK.ID_VALUE2
                            ELSE JK.ID_VALUE 
                       END
  AND JK.ID_TYPE IN (1,4)
  LEFT OUTER JOIN DESCRIPTIONS D
    ON D.LANGUAGE_CODE = 2
   AND D.Description_Code = CASE
           WHEN JK.ID_TYPE = 4 THEN
            UT.Description_Code
       
           WHEN JK.ID_TYPE = 7 AND
                JK.Id_Type2 = 7 THEN
            UT.Description_Code

           WHEN LENGTH(JK.ID_VALUE) IN (3, 4) THEN
            UT.Description_Code
           WHEN LENGTH(JK.ID_VALUE2) IN (3, 4) AND JK.ID_TYPE = 1 THEN
            UT.Description_Code
       
           ELSE
              CASE WHEN JK.ID_TYPE = 1 THEN JK.ID_VALUE2
                            ELSE JK.ID_VALUE 
              END
       END  
WHERE   INACTIVE_DATE IS NULL OR INACTIVE_DATE >= SYSDATE -90
;