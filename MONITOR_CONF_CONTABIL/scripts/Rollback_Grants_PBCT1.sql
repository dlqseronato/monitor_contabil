revoke select on OBT_REP.OBTDM_ROWCOUNTS from R_ITBILLING_RO;
revoke select,update,delete,insert on OBT_REP.OBTDM_ROWCOUNTS from SAN_AUTOM;

revoke select,insert,update,delete on arbor.jnl_custom from PROATIVO_PROD;
revoke select,insert,update,delete on arbor.jnl_custom from SAN_AUTOM;
revoke select on arbor.jnl_custom  from R_ITBILLING_RO;

revoke select,insert,update,delete on arbor.jnl_keys from PROATIVO_PROD;
revoke select,insert,update,delete on arbor.jnl_keys from SAN_AUTOM;
revoke select on arbor.jnl_keys  from R_ITBILLING_RO;

revoke select,insert,update,delete on arbor.jnl_detail from PROATIVO_PROD;
revoke select,insert,update,delete on arbor.jnl_detail from SAN_AUTOM;
revoke select on arbor.jnl_detail from R_ITBILLING_RO;

revoke select,insert,update,delete on ARBORGVT_JOURNALS.GVT_TAXAS_IMPOSTOS from PROATIVO_PROD;
revoke select,insert,update,delete on ARBORGVT_JOURNALS.GVT_TAXAS_IMPOSTOS from SAN_AUTOM;
revoke select on ARBORGVT_JOURNALS.GVT_TAXAS_IMPOSTOS  from R_ITBILLING_RO;


revoke select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO from PROATIVO_PROD;
revoke select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO from SAN_AUTOM;
revoke select on ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO from R_ITBILLING_RO;

revoke select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_ORD_INT from PROATIVO_PROD;
revoke select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_ORD_INT from SAN_AUTOM;
revoke select on ARBORGVT_JOURNALS.GVT_KENAN_SAP_ORD_INT  from R_ITBILLING_RO;

revoke select,insert,update,delete on arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET from PROATIVO_PROD;
revoke select,insert,update,delete on arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET from SAN_AUTOM;
revoke select on arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET from R_ITBILLING_RO;

revoke select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_CENTRO_CUSTO from PROATIVO_PROD;
revoke select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_CENTRO_CUSTO from SAN_AUTOM;
revoke select on ARBORGVT_JOURNALS.GVT_KENAN_SAP_CENTRO_CUSTO from R_ITBILLING_RO;

revoke select,insert,update,delete on arborgvt_billing.Gvt_Classificacao_Tv from PROATIVO_PROD;
revoke select,insert,update,delete on arborgvt_billing.Gvt_Classificacao_Tv from SAN_AUTOM;
revoke select on arborgvt_billing.Gvt_Classificacao_Tv from R_ITBILLING_RO;

revoke select,insert,update,delete on ARBORGVT_JOURNALS.Gvt_Kenan_Sap_Sped_Lotes from PROATIVO_PROD;
revoke select,insert,update,delete on ARBORGVT_JOURNALS.Gvt_Kenan_Sap_Sped_Lotes from SAN_AUTOM;
revoke select on ARBORGVT_JOURNALS.Gvt_Kenan_Sap_Sped_Lotes from R_ITBILLING_RO;