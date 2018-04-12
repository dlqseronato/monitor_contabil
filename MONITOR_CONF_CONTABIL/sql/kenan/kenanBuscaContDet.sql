SELECT * FROM (SELECT a.*, rownum r__
FROM(
SELECT  *
FROM arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET 
WHERE LOTE = ?
) a
    WHERE rownum < ((? * 1000000) + 1 ))
WHERE r__ >= (((?-1) * 1000000) + 1);