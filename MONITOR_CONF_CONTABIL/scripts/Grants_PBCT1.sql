grant select on OBT_REP.OBTDM_ROWCOUNTS to R_ITBILLING_RO;
grant select,update,delete,insert on OBT_REP.OBTDM_ROWCOUNTS to SAN_AUTOM;

grant select,insert,update,delete on arbor.jnl_custom to PROATIVO_PROD;
grant select,insert,update,delete on arbor.jnl_custom to SAN_AUTOM;
grant select on arbor.jnl_custom  to R_ITBILLING_RO;

grant select,insert,update,delete on arbor.jnl_keys to PROATIVO_PROD;
grant select,insert,update,delete on arbor.jnl_keys to SAN_AUTOM;
grant select on arbor.jnl_keys  to R_ITBILLING_RO;

grant select,insert,update,delete on arbor.jnl_detail to PROATIVO_PROD;
grant select,insert,update,delete on arbor.jnl_detail to SAN_AUTOM;
grant select on arbor.jnl_detail to R_ITBILLING_RO;

grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_TAXAS_IMPOSTOS to PROATIVO_PROD;
grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_TAXAS_IMPOSTOS to SAN_AUTOM;
grant select on ARBORGVT_JOURNALS.GVT_TAXAS_IMPOSTOS  to R_ITBILLING_RO;


grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO to PROATIVO_PROD;
grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO to SAN_AUTOM;
grant select on ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO to R_ITBILLING_RO;

grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_ORD_INT to PROATIVO_PROD;
grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_ORD_INT to SAN_AUTOM;
grant select on ARBORGVT_JOURNALS.GVT_KENAN_SAP_ORD_INT  to R_ITBILLING_RO;

grant select,insert,update,delete on arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET to PROATIVO_PROD;
grant select,insert,update,delete on arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET to SAN_AUTOM;
grant select on arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET to R_ITBILLING_RO;

grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_CENTRO_CUSTO to PROATIVO_PROD;
grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_KENAN_SAP_CENTRO_CUSTO to SAN_AUTOM;
grant select on ARBORGVT_JOURNALS.GVT_KENAN_SAP_CENTRO_CUSTO to R_ITBILLING_RO;

grant select,insert,update,delete on arborgvt_billing.Gvt_Classificacao_Tv to PROATIVO_PROD;
grant select,insert,update,delete on arborgvt_billing.Gvt_Classificacao_Tv to SAN_AUTOM;
grant select on arborgvt_billing.Gvt_Classificacao_Tv to R_ITBILLING_RO;

grant select,insert,update,delete on ARBORGVT_JOURNALS.Gvt_Kenan_Sap_Sped_Lotes to PROATIVO_PROD;
grant select,insert,update,delete on ARBORGVT_JOURNALS.Gvt_Kenan_Sap_Sped_Lotes to SAN_AUTOM;
grant select on ARBORGVT_JOURNALS.Gvt_Kenan_Sap_Sped_Lotes to R_ITBILLING_RO;