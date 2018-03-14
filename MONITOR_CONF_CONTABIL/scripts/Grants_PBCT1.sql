grant select on OBT_REP.OBTDM_ROWCOUNTS to R_ITBILLING_RO;
grant select,update,delete,insert on OBT_REP.OBTDM_ROWCOUNTS to SAN_AUTOM;

grant select,insert,update,delete on arbor.jnl_custom to PROATIVO_PROD;
grant select,insert,update,delete on arbor.jnl_custom to SAN_AUTOM;
grant select on arbor.jnl_custom  to R_ITBILLING_RO;

grant select,insert,update,delete on arbor.jnl_keys to PROATIVO_PROD;
grant select,insert,update,delete on arbor.jnl_keys to SAN_AUTOM;
grant select on arbor.jnl_keys  to R_ITBILLING_RO;

grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_TAXAS_IMPOSTOS to PROATIVO_PROD;
grant select,insert,update,delete on ARBORGVT_JOURNALS.GVT_TAXAS_IMPOSTOS to SAN_AUTOM;
grant select on ARBORGVT_JOURNALS.GVT_TAXAS_IMPOSTOS  to R_ITBILLING_RO;
