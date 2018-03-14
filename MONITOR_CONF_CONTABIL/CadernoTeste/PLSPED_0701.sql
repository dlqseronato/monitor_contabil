--------------------------------------------------------------------------------
--   Programa : 10.sql
--   Analista : Ranieri Mocelin
--   Data     : Março/2012
--   Descrição : Le o Tabela JNL_DETAIL 
--               Gera Tabela FROM ARBORGVT_JOURNALS.GVT_KENAN_SAP_SPED
--------------------------------------------------------------------------------
 
/*
SET VERIFY                     OFF;
SET SERVEROUT                  ON SIZE 1000000;
SET FEED                       OFF;
SET SPACE                      0;
SET PAGESIZE                   0;
SET LINE                       500;
SET WRAP                       ON;
SET HEADING                    OFF;
*/
--
--------------------------------------------------------------------------------
--  declaração de variáveis
--------------------------------------------------------------------------------
--
DECLARE
  v_total_amount		number;
  v_Temp_amount			number;
  index_curso			number;
  quantidade_linhas		number;
  v_Tracking_id            NUMBER;
  v_id_type				number;	
  v_count				   NUMBER := 0;
  v_valida_param		   NUMBER := 0;
  v_linha                  NUMBER := 0;
  v_commit                 NUMBER := 0;
  v_bill_ref_no_3          jnl_detail.jnl_ref_no%TYPE;
  v_open_item_id_3         jnl_detail.open_item_id%TYPE;
  v_bill_ref_no_4         jnl_detail.jnl_ref_no%TYPE;
  v_open_item_id_4         jnl_detail.open_item_id%TYPE;
  v_jnl_ref_no             jnl_detail.jnl_ref_no%TYPE;
  v_jnl_ref_no_serv        jnl_detail.jnl_ref_no_serv%TYPE;
  v_lote                   gvt_exec_arg.desc_parametro%TYPE;
  v_bill_ref_no            jnl_detail.bill_ref_no%TYPE;
  v_bill_ref_no_2          jnl_detail.bill_ref_no%TYPE;
  v_open_item_id           jnl_detail.open_item_id%TYPE;
  v_open_item_id_2         jnl_detail.open_item_id%TYPE;
  v_open_item_id_ssn       jnl_detail.open_item_id%TYPE;
  v_open_item_id_ssn_2     jnl_detail.open_item_id%TYPE;
    v_open_item_id_ssn_4     jnl_detail.open_item_id%TYPE;
  v_sin_seq_ref_no         sin_seq_no.sin_seq_ref_no%TYPE;
  v_sin_seq_ref_no_2       sin_seq_no.sin_seq_ref_no%TYPE;
  v_sin_seq_ref_no_4       sin_seq_no.sin_seq_ref_no%TYPE;
  v_sin_seq_ref_resets     sin_seq_no.sin_seq_ref_resets%TYPE;
  v_sin_seq_ref_resets_2   sin_seq_no.sin_seq_ref_resets%TYPE;
   v_sin_seq_ref_resets_4   sin_seq_no.sin_seq_ref_resets%TYPE;
  v_full_sin_seq           sin_seq_no.full_sin_seq%TYPE;
  v_full_sin_seq_2         sin_seq_no.full_sin_seq%TYPE;
   v_full_sin_seq_4         sin_seq_no.full_sin_seq%TYPE;
  v_group_id               sin_seq_no.group_id%TYPE;
  v_group_id_2             sin_seq_no.group_id%TYPE;
    v_group_id_4             sin_seq_no.group_id%TYPE;
  v_prep_status            sin_seq_no.prep_status%TYPE;
  v_tot_registros      NUMBER := 0;
  v_tot_valor_credito  NUMBER(16, 2) := 0;
  v_tot_valor_debito   NUMBER(16, 2) := 0;
  v_tot_reg_uf         NUMBER := 0;
  v_sqlerrm            VARCHAR2(1000);
  v_lotes_lidos        NUMBER := 0;
  v_db_cre             VARCHAR(01);
  v_db_deb             VARCHAR(01);
  v_vlr_guardar        NUMBER(16, 2) := 0;
  v_vlr_dif            NUMBER(16, 2) := 0;
  v_rev_ctr            jnl_detail.rev_rcv_cost_ctr%TYPE := 0;
  v_mkt_code           jnl_detail.mkt_code%TYPE := 0;
  v_ciclo              cmf.bill_period%TYPE := '&1';
  v_valida_tab_lotes   NUMBER;
  V_VALIDA_IMPOSTO   NUMBER := 0;
  v_cta_cred_new VARCHAR(30);  
  v_cta_db_new VARCHAR(30);  
  v_cta_cred VARCHAR(30);
  v_cta_db VARCHAR(30);
  v_prod_desconto NUMBER := 0;
  v_prefix VARCHAR(30);
  v_centro_custo  VARCHAR2(20);
  v_ordem_interna VARCHAR2(50);
  v_verifica_modalidade_cliente 	VARCHAR2(20);
  v_verifica_tipo_produto 	  		VARCHAR2(20);
  v_obter_centro_custo 	      		VARCHAR2(20);
  v_conta_contabil_credito 			VARCHAR2(20);
  v_conta_contabil_debito 			VARCHAR2(20);
  
  -- INICIO PROJETO VENCIMENTO / VENCIMENTO 
  vPeriodo               arborgvt_journals.gvt_cluster_rc_nrc_uso_mes.competencia%TYPE;
  -- FIM PROJETO VENCIMENTO / VENCIMENTO 
  --*******************************************************************************
  -- CURSOR SELECIONA LOTES
  --*******************************************************************************

  CURSOR c_lotes IS
   SELECT lt.*
   FROM   GVT_Kenan_sap_sped_lotes lt
   WHERE  lt.data_grava_lote IS NULL
   AND    lt.h_interface_sap = 'FII001'
   ORDER BY lt.jnl_ref_no,
            lt.jnl_ref_no_serv;

  --*******************************************************************************
  -- CURSOR SELECIONA JNL_DETAIL PARA GERAR DADOS LANCAMENTO CONTABEIS SAP
  --*******************************************************************************

   CURSOR c_jnl_detail(c_lote in VARCHAR2, c_jnl_ref_no IN NUMBER, c_jnl_ref_no_serv NUMBER) IS
     SELECT kss.pos_neg,
            kss.documento_jnl_ref_no jnl_ref_no,
            kss.documento_jnl_ref_no_serv jnl_ref_no_serv,
            kss.empresa_mkt_code mkt_code,
            kss.data_documento jnl_subcycle_end_dt,
            kss.tipo_lancamento id_type,
            kss.id_type2,
            kss.conta_contabil_cr fml_acct_cr,
      kss.conta_contabil_db fml_acct_db,
            kss.centro_de_custo_rev_ctr rev_rcv_cost_ctr,
            kss.bill_ref_no,
            kss.bill_ref_resets,
            kss.account_no,
            kss.external_id,
            kss.account_category,
            kss.open_item_id,
            SUM(kss.montante_valor) valor,
            kss.use_code,
            kss.cod_atribuicao ciclo,
      kss.full_sin_seq,
      kss.tipo_registro,
      kss.moeda,
        kss.divisao,
      kss.centro_custo
   FROM   arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET kss
     WHERE  kss.documento_jnl_ref_no = c_jnl_ref_no
     AND    kss.documento_jnl_ref_no_serv = c_jnl_ref_no_serv
   AND    kss.lote = c_lote
   GROUP BY kss.pos_neg,
              kss.documento_jnl_ref_no,
              kss.documento_jnl_ref_no_serv,
              kss.empresa_mkt_code,
              kss.data_documento,
              kss.tipo_lancamento,
              kss.id_type2,
              kss.conta_contabil_cr,
              kss.conta_contabil_db,
              kss.centro_de_custo_rev_ctr,
              kss.bill_ref_no,
              kss.bill_ref_resets,
              kss.account_no,
              kss.external_id,
              kss.account_category,
              kss.open_item_id,
              kss.use_code,
              kss.cod_atribuicao,
              kss.full_sin_seq,
              kss.tipo_registro,
              kss.moeda,
              kss.divisao,
              kss.centro_custo
     ORDER  BY 2,3,4,5,6,9,8,9,10,1,17;
        
  --**********************************************************************************************************
  -- CURSOR SELECIONA JNL_DETAIL PARA GERAR DADOS LANCAMENTO CONTABEIS SAP COM GERAÇÃO DO DADOS FATURADOS
  --**********************************************************************************************************

    CURSOR c_jnl_detail_2(c_jnl_ref_no IN NUMBER, c_jnl_ref_no_serv NUMBER) IS
		SELECT   /*+ PARALLEL (jd,20) */  case when jd.reported_amount >= 0 then '0 P' else '1 N' end pos_neg,
               jd.jnl_ref_no,
               jd.jnl_ref_no_serv,
               jd.mkt_code,
               jd.jnl_subcycle_end_dt,
               jd.id_type,
               jd.id_type2,
               jc.fml_acct_cr,
               jc.fml_acct_db,
               jd.rev_rcv_cost_ctr,
               jd.bill_ref_no,
               jd.bill_ref_resets,
               jd.account_no,
               ciam.external_id,
               jd.account_category,
               jd.open_item_id,
               SUM(jd.reported_amount) / 100 AS valor,
               jd.use_code,
               c.bill_period ciclo,
         CASE
          WHEN jd.open_item_id IN (90,91,92,93,94,95) --Pay TV e POP
            THEN  '30PR'
          ELSE ksd.divisao 
        END divisao,  
         decode(jd.id_type,1,jd.id_value2,jd.id_value) id_value,
         DECODE(nvl(ksoi.ordem_interna,ksoi1.ordem_interna),null,null,
         (CASE
          WHEN jd.open_item_id = 20 
           THEN 'D' || SUBSTR(ksd.divisao, 1, 2) || ksd.divisao
          WHEN jd.open_item_id IN (90,91,92,93,94,95,99,100,101) 
           THEN nvl(ksoi.ordem_interna,ksoi1.ordem_interna) || ksd.divisao 
        ELSE 'T' ||SUBSTR(ksd.divisao,1,2) || nvl(ksoi.ordem_interna,ksoi1.ordem_interna)
        END)) ordem_interna,
         jd.tracking_id,
         /* PJ1176 - ORIONII-885 */
        jd.id_value2,
           kscc.centro_custo
    FROM   jnl_detail                                   jd,
               jnl_custom                                   jc,
               customer_id_acct_map                         ciam,
               cmf                                          c,
               ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO      ksd,   
               ARBORGVT_JOURNALS.GVT_KENAN_SAP_ORD_INT      ksoi,
               ARBORGVT_JOURNALS.GVT_KENAN_SAP_ORD_INT      ksoi1,
               ARBORGVT_JOURNALS.GVT_KENAN_SAP_CENTRO_CUSTO kscc
    WHERE  jd.jnl_ref_no = c_jnl_ref_no
    AND    jd.jnl_ref_no_serv = c_jnl_ref_no_serv
    AND    jd.jnl_code_id = jc.jnl_code_id
    AND    jd.jnl_feed_status = 'W'
    AND    jd.account_no = c.account_no
    AND    jd.account_no = ciam.account_no(+)
    AND    ciam.external_id_type(+) = 1
    AND    jd.mkt_code = ksd.mkt_code
    AND    jd.id_type = ksoi.jnl_id_type(+)
    AND    jd.id_value = ksoi.element(+)
    AND    jd.id_value2 = ksoi1.element(+)
    AND    jd.id_type in (1,3,4,6) -- Retirando descontos e impostos deste cursor 
    and    kscc.account_category = c.account_category
    and    upper(kscc.tipo_produto) = 'FIXA'
    and    upper(kscc.tipo_contabilizacao) = 'RECEITA'
    and    upper(kscc.caracteristica_cliente) = '1P'
        GROUP BY case when jd.reported_amount >=0 then '0 P' else '1 N' end,
                 jd.jnl_ref_no,
                 jd.jnl_ref_no_serv,
                 jd.mkt_code,
                 jd.jnl_subcycle_end_dt,
                 jd.id_type,
                 jd.id_type2,
                 jc.fml_acct_cr,
                 jc.fml_acct_db,
                 jd.rev_rcv_cost_ctr,
                 jd.bill_ref_no,
                 jd.bill_ref_resets,
                 jd.account_no,
                 ciam.external_id,
                 jd.account_category,
                 jd.open_item_id,
                 jd.use_code,
                 c.bill_period,
                 CASE WHEN jd.open_item_id IN (90,91,92,93,94,95) THEN  '30PR'
                 ELSE ksd.divisao END,
                 decode(jd.id_type,1,jd.id_value2,jd.id_value),
                 DECODE(nvl(ksoi.ordem_interna,ksoi1.ordem_interna),null,null,(
                  CASE WHEN jd.open_item_id = 20 THEN 'D' || SUBSTR(ksd.divisao, 1, 2) || ksd.divisao
                       WHEN jd.open_item_id IN (90,91,92,93,94,95,99,100,101) THEN  nvl(ksoi.ordem_interna,ksoi1.ordem_interna) || ksd.divisao
                       ELSE 'T' ||SUBSTR(ksd.divisao,1,2) || nvl(ksoi.ordem_interna,ksoi1.ordem_interna) END)),
                 jd.tracking_id,
                 jd.id_value2,
                 kscc.centro_custo
                 ORDER BY 2,3,4,5,6,9,8,9,10,1,17;
        
    -- insoc-027.1     
     CURSOR c_jnl_detail_3(c_jnl_ref_no IN NUMBER, c_jnl_ref_no_serv NUMBER) IS
		SELECT  /*+ PARALLEL (jd,20) */ case when jd.reported_amount >= 0 then '0 P' else '1 N' end pos_neg,
        jd.jnl_ref_no,
        jd.jnl_ref_no_serv,
        jd.mkt_code,
        jd.jnl_subcycle_end_dt,
        jd.id_type,
        jd.id_type2,
        jc.fml_acct_cr,
        jc.fml_acct_db,
        jd.rev_rcv_cost_ctr,
        jd.bill_ref_no,
        jd.bill_ref_resets,
        jd.account_no,
        ciam.external_id,
        jd.account_category,
        jd.open_item_id,
        SUM(jd.reported_amount) / 100 AS valor,
        jd.use_code,
        c.bill_period ciclo,
        CASE
          WHEN jd.open_item_id IN (90,91,92,93,94,95) 
            THEN  '30PR'
          ELSE ksd.divisao 
        END divisao,
        decode(jd.id_type,1,jd.id_value2,jd.id_value) id_value,
        CASE
          WHEN jd.open_item_id = 20 
            THEN  NULL ----'D' || SUBSTR(ksd.divisao, 1, 2) ALTERACAO TASK FORCE REQ2323464 
          WHEN jd.open_item_id IN (90,91,92,93,94,95,99,100,101) 
            THEN  NULL 
          ELSE 'T' ||SUBSTR(ksd.divisao,1,2) 
        END ordem_interna ,
        jd.subscr_no,
        jd.subscr_no_resets,
        jd.tax_type_code,
        jd.id_value2,
        kscc.centro_custo
    FROM   jnl_detail                                   jd,
           jnl_custom                                   jc,
           customer_id_acct_map                         ciam,
           cmf                                          c,
           ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO      ksd,
           ARBORGVT_JOURNALS.GVT_KENAN_SAP_CENTRO_CUSTO kscc
    WHERE  jd.jnl_ref_no = c_jnl_ref_no
    AND    jd.jnl_ref_no_serv = c_jnl_ref_no_serv
    AND    jd.jnl_code_id = jc.jnl_code_id
    AND    jd.jnl_feed_status = 'W'
    AND    jd.account_no = c.account_no
    AND    jd.account_no = ciam.account_no(+)
    AND    ciam.external_id_type(+) = 1
    AND    jd.mkt_code = ksd.mkt_code 
    and    jd.id_type = 7
    and    jd.id_type2 <> 4   
    and    kscc.account_category = c.account_category
    and    upper(kscc.tipo_produto) = upper('FIXA')
    and    upper(kscc.caracteristica_cliente) = upper('1P')
    and    upper(kscc.tipo_contabilizacao) = (CASE WHEN jd.tax_type_code between 1000000 and 1999999 THEN  'ICMS' 
																                WHEN jd.tax_type_code between 2000000 and 2999999 THEN  'ISS' 
																                ELSE 'RECEITA'     END)   
    GROUP BY     
       case when jd.reported_amount >= 0 then '0 P' else '1 N' end,
                 jd.jnl_ref_no,
                 jd.jnl_ref_no_serv,
                 jd.mkt_code,
                 jd.jnl_subcycle_end_dt,
                 jd.id_type,
                 jd.id_type2,
                 jc.fml_acct_cr,
                 jc.fml_acct_db,
                 jd.rev_rcv_cost_ctr,
                 jd.bill_ref_no,
                 jd.bill_ref_resets,
                 jd.account_no,
                 ciam.external_id,
                 jd.account_category,
                 jd.open_item_id,
                 jd.use_code,
                 c.bill_period,
        CASE
          WHEN jd.open_item_id IN (90,91,92,93,94,95) 
            THEN  '30PR'
          ELSE ksd.divisao 
        END,
        decode(jd.id_type,1,jd.id_value2,jd.id_value),
        CASE
          WHEN jd.open_item_id = 20 
            THEN NULL --'D' || SUBSTR(ksd.divisao, 1, 2) 
          WHEN jd.open_item_id IN (90,91,92,93,94,95,99,100,101) 
            THEN  NULL 
          ELSE 'T' ||SUBSTR(ksd.divisao,1,2) 
        END,
        jd.subscr_no,
        jd.subscr_no_resets,
        jd.tax_type_code,
        jd.id_value2,
        kscc.centro_custo
    ORDER BY 2,3,4,5,6,9,8,9,10,1,17;

        
  CURSOR c_bill_detail_1 (
      v_bill_ref_no          IN NUMBER,
      v_bill_ref_resets      IN NUMBER,
      v_tax_type_code        IN NUMBER,
      v_subscr_no            IN NUMBER,
      v_subscr_no_resets     IN NUMBER,
      v_type_code            IN NUMBER,
      V_Open_Item_Id         IN NUMBER,
	    v_account_category     IN NUMBER, /*BUG-1277*/
	    v_use_code             IN NUMBER
	  ) /*BUG-1277*/
      -- ACIMA ADICIONADO PARAMETRO PARA BATIMENTO SAP X CONT_DET
   IS
SELECT BID.type_code, BID.subtype_code, BIT.federal_tax/100 valor, gkoi.ordem_interna, BID.tracking_id, c.fml_acct_cr, c.fml_acct_db
  FROM bill_invoice_detail bid, bill_invoice_tax bit, arborgvt_journals.gvt_kenan_sap_ord_int gkoi, jnl_keys k, jnl_custom c
WHERE BID.BILL_REF_NO = v_bill_ref_no
   AND BID.BILL_REF_RESETS = v_bill_ref_resets
   AND BID.type_code = v_type_code
   AND BID.SUBSCR_NO = nvl(v_subscr_no,0)
   AND BID.SUBSCR_NO_RESETS = nvl(v_subscr_no_resets,0)
   AND BIT.BILL_REF_NO = BID.BILL_REF_NO
   AND BIT.BILL_REF_RESETS = BID.BILL_REF_RESETS
   AND BID.Open_Item_Id = V_Open_Item_Id -- CORRIGIDO PARA BATIMENTO SAP X CONT_DET
   AND BIT.BILL_INVOICE_ROW = BID.BILL_INVOICE_ROW
   and BIT.Federal_Tax > 0
   AND BIT.Tax_Type_Code = v_tax_type_code
   and gkoi.element (+) = bid.subtype_code
   and gkoi.jnl_id_type (+) = decode(bid.type_code,2,3,3,6,7,4,bid.type_code)   
   -- Alteração para retornar a conta contábil do produto sobre o qual incidiu o imposto
   /*BUG-1277 - INICIO*/
   and k.jnl_code_id = c.jnl_code_id
   and k.id_type = decode(bid.type_code,2,3,3,6,7,4,bid.type_code)
   and k.id_value = bid.subtype_code
   and k.inactive_date is null
   and k.account_category = v_account_category -- retornado do cursor c_jnl_detail_3    
   and k.use_code = v_use_code;  -- retornado do cursor c_jnl_detail_3					
   /*BUG-1277 - FIM*/
   
   --INSOC-028.1--
  CURSOR c_jnl_detail_d(c_jnl_ref_no IN NUMBER, c_jnl_ref_no_serv NUMBER) IS
		SELECT  /*+ PARALLEL (jd,20) */ case when jd.reported_amount >= 0 then '0 P' else '1 N' end pos_neg,
        jd.jnl_ref_no,
        jd.jnl_ref_no_serv,
        jd.mkt_code,
        jd.jnl_subcycle_end_dt,
        jd.id_type,
        jd.id_type2,
        jc.fml_acct_cr,
        jc.fml_acct_db,
        jd.rev_rcv_cost_ctr,
        jd.bill_ref_no,
        jd.bill_ref_resets,
        jd.account_no,
        ciam.external_id,
        jd.account_category,
        jd.open_item_id,
        SUM(jd.reported_amount) / 100 AS valor,
        jd.use_code,
        c.bill_period ciclo,
        ksd.divisao,
        jd.id_value,
        null ordem_interna,            
        jd.Subscr_No,         -- ADICIONADO PARA BATIMENTO SAP X CONT_DET
        jd.Subscr_No_Resets,   -- ADICIONADO PARA BATIMENTO SAP X CONT_DET
        /* PJ1176 - ORIONII-885 */
        'RECEITA' tipo_contabilizacao,
        jd.id_value2,
        '1P' tipo_modalidade      
    FROM     jnl_detail                                  jd,
        jnl_custom                                  jc,
        customer_id_acct_map                        ciam,
        cmf                                         c,
        ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO     ksd 
    WHERE  jd.jnl_ref_no = c_jnl_ref_no
    AND    jd.jnl_ref_no_serv = c_jnl_ref_no_serv
    AND    jd.jnl_code_id = jc.jnl_code_id
    AND    jd.jnl_feed_status = 'W'
    AND    jd.account_no = c.account_no
    AND    jd.account_no = ciam.account_no(+)
    AND    ciam.external_id_type(+) = 1
    AND    jd.mkt_code = ksd.mkt_code
    AND    jd.id_type = 5
    GROUP BY 
         case when jd.reported_amount >= 0 then '0 P' else '1 N' end,
         jd.jnl_ref_no,
         jd.jnl_ref_no_serv,
         jd.mkt_code,
         jd.jnl_subcycle_end_dt,
         jd.id_type,
         jd.id_type2,
         jc.fml_acct_cr,
         jc.fml_acct_db,
         jd.rev_rcv_cost_ctr,
         jd.bill_ref_no,
         jd.bill_ref_resets,
         jd.account_no,
         ciam.external_id,
         jd.account_category,
         jd.open_item_id,
         jd.use_code,
         c.bill_period,
         ksd.divisao,
         jd.id_value,
         NULL,            
         jd.Subscr_No,        -- ADICIONADO PARA BATIMENTO SAP X CONT_DET
         jd.Subscr_No_Resets,  -- ADICIONADO PARA BATIMENTO SAP X CONT_DET
         /* PJ1176 - ORIONII-885 */
         'RECEITA',
         jd.id_value2,
         '1P'  
    ORDER BY 2,3,4,5,6,9,8,9,10,1,17;

--  CURSOR c_bill_detail_d(c_bill_ref_no in number, c_bill_ref_resets in number, c_id_value IN NUMBER) IS
  -- CORRECAO PARAMETROS CURSOR BATIMENTO SAP X CONT_DET
  CURSOR c_bill_detail_d(c_bill_ref_no      in number,
                         c_bill_ref_resets  in number, 
                         c_id_value         IN NUMBER, 
                         c_Subscr_No        in number, 
                         c_Subscr_No_Resets in number,
                         c_tipo_receita     in VARCHAR2,
                         c_tipo_modalidade  in VARCHAR2,
                         c_account_category in NUMBER,
                         c_tipo_produto     in VARCHAR2) IS
    SELECT bid.type_code,
         bid.subtype_code,
         bd.discount_amount/100 discount_amount,
         gkoi.ordem_interna,
         bid.open_item_id,
		     bid.tracking_id, 
		     kscc.centro_custo
    FROM bill_invoice_detail bid, bill_invoice_discount bd, arborgvt_journals.gvt_kenan_sap_ord_int gkoi,
    		 arborgvt_journals.gvt_kenan_sap_centro_custo kscc
    WHERE bid.bill_ref_no = c_bill_ref_no
    AND   bid.bill_ref_resets = c_bill_ref_resets
    AND   bid.type_code in (2,3,7)
    AND   bid.bill_ref_no = bd.bill_ref_no
    AND   bid.bill_ref_resets = bd.bill_ref_resets
    AND   bid.bill_invoice_row = bd.bill_invoice_row
    AND   bd.discount_id = c_id_value
    -- INICO ABAIXO CORRECAO BATIMENTO SAP X CONT_DET
    and   (bid.Subscr_No is null
      or   bid.Subscr_No = 0
      or   bid.Subscr_No = decode(c_Subscr_No,null,bid.Subscr_No,c_Subscr_No ))
    and   (bid.Subscr_No_Resets is null
      or   bid.Subscr_No_Resets = 0
      or   bid.Subscr_No_Resets = decode(c_Subscr_No_Resets,null,bid.Subscr_No_Resets,c_Subscr_No_Resets))
    -- FINAL ACIMA CORRECAO BATIMENTO SAP X CONT_DET
    AND   gkoi.element (+) = bid.subtype_code
    and   gkoi.jnl_id_type (+) = decode(bid.type_code,2,3,3,6,7,4,bid.type_code)
    and kscc.account_category = c_account_category
    and upper(kscc.tipo_produto) = upper(c_tipo_produto)
    and upper(kscc.caracteristica_cliente) = upper(c_tipo_modalidade)
    and upper(kscc.tipo_contabilizacao) = upper(c_tipo_receita)    
    order by bid.open_item_id;
	
cursor busca_param_percent is
select tipo_lancamento,
       element,
       component_id,
       jurisdiction,
       units_type,
       sum(percentual)
  from arborgvt_journals.gvt_kenan_sap_param
 where data_inativacao is null
 group by tipo_lancamento, element, component_id, jurisdiction, units_type
having sum(percentual) <> 100;
 
 busca_cursor busca_param_percent%ROWTYPE;
 
 CURSOR C_ABERTURA_FISCAL (v_tracking_id NUMBER, v_tipo_lancamento number) IS
 SELECT 
 gks.TIPO_LANCAMENTO,
 gks.ELEMENT,
 gks.CONTA_PARA,
 gks.PERCENTUAL,
 gks.COMPONENT_ID,
 gks.JURISDICTION,
 gks.UNITS_TYPE
from arborgvt_journals.gvt_kenan_sap_param gks, product p, product_rate_key pk
where p.TRACKING_ID = v_tracking_id
and pk.TRACKING_ID (+)= p.tracking_id
and gks.element = p.element_id
and (gks.component_id = p.COMPONENT_ID or gks.component_id = 0)
and ((gks.JURISDICTION = pk.JURISDICTION
and gks.units_type = pk.UNITS_TYPE
and pk.INACTIVE_DT is null) or
(gks.jurisdiction = 0
and gks.units_type = 0 ))
and gks.TIPO_LANCAMENTO = v_tipo_lancamento
and gks.DATA_INATIVACAO is null 
and p.PRODUCT_INACTIVE_DT is null
order by gks.PERCENTUAL;

cursor c_jnl_detail_4 (c_jnl_ref_no IN NUMBER, c_jnl_ref_no_serv NUMBER) IS
    SELECT '1 N' pos_neg,
            jd.jnl_ref_no,
            jd.jnl_ref_no_serv,
            jd.mkt_code,
            jd.jnl_subcycle_end_dt,
            4 id_type, -- simular que o registro eh de uso
            jd.id_type2,
            0 fml_acct_cr,
            0 fml_acct_db,
            NULL rev_rcv_cost_ctr,
            jd.bill_ref_no,
            jd.bill_ref_resets,
            jd.account_no,
            ciam.external_id,
            jd.account_category,
            1 open_item_id, -- os registros de unidade de credito na jnl é 0, simula 1 - Fixa/Celular
            SUM(jd.reported_amount) / 100 AS valor,
            jd.use_code,
            c.bill_period ciclo,
            ksd.divisao,
            NULL ordem_interna,            
            NULL Subscr_No,       
            NULL Subscr_No_Resets, 
			/* PJ1176 - ORIONII-885 */ 
            'RECEITA' tipo_contabilizacao,
			0 id_value,
			0 id_value2,
      '1P' tipo_modalidade			
     FROM   jnl_detail                                  jd,
            jnl_custom                                  jc,
            customer_id_acct_map                        ciam,
            cmf                                         c,
            ARBORGVT_JOURNALS.GVT_KENAN_SAP_DIVISAO       ksd
     WHERE  jd.jnl_ref_no = c_jnl_ref_no
     AND    jd.jnl_ref_no_serv = c_jnl_ref_no_serv
     AND    jd.jnl_code_id = jc.jnl_code_id
     AND    jd.account_no = c.account_no
     AND    jd.account_no = ciam.account_no(+)
     AND    ciam.external_id_type(+) = 1
     AND    jd.reported_amount < 0
     AND    jd.mkt_code = ksd.mkt_code
     AND    jd.id_type = 9
     GROUP BY jd.jnl_ref_no,
              jd.jnl_ref_no_serv,
              jd.mkt_code,
              jd.jnl_subcycle_end_dt,
              jd.id_type,
              jd.id_type2,
              0,
              0,
              NULL,
              jd.bill_ref_no,
              jd.bill_ref_resets,
              jd.account_no,
              ciam.external_id,
              jd.account_category,
              jd.open_item_id,
              jd.use_code,
              c.bill_period,
              ksd.divisao,        
              NULL,            
              NULL,        
              NULL,
              'RECEITA',
			  0,
			  0,
			  '1P'
     ORDER BY 2,3,4,5,6,9,8,9,10,1,17;

cursor c_usg_d (v_bill_ref_no NUMBER, 
								v_bill_ref_no_resets NUMBER, 
								v_subscr_no NUMBER, 
								v_subscr_no_resets NUMBER, 
								v_account_category NUMBER, 
								v_divisao_t VARCHAR2,
								v_tipo_modalidade VARCHAR2,
								v_tipo_contabilizacao VARCHAR2) is
select bid.subtype_code, sum(bid.secondary_amount)/100 as valor, jc.fml_acct_db, jc.fml_acct_cr, bid.type_code,
	     CASE WHEN bid.open_item_id = 20 THEN 'D' || v_divisao_t
            WHEN bid.open_item_id IN (90,91,92,93,94,95,99,100,101) THEN ksoi.ordem_interna || v_divisao_t 
            ELSE 'T' ||SUBSTR(v_divisao_t,1,2) || ksoi.ordem_interna
        END ordem_interna, kscc.centro_custo
	from bill_invoice_detail bid, jnl_keys jk, jnl_custom jc, arborgvt_journals.gvt_kenan_sap_ord_int ksoi,
       arborgvt_journals.gvt_kenan_sap_centro_custo kscc
		where bid.type_code = 7
			and bid.bill_ref_no = v_bill_ref_no
			and bid.bill_ref_resets = v_bill_ref_no_resets
			and bid.subscr_no = NVL(v_subscr_no, bid.subscr_no)
			and bid.subscr_no_resets = NVL(v_subscr_no_resets, bid.subscr_no_resets)
			and jk.account_category = v_account_category
			and jk.id_value = bid.subtype_code
			and jk.open_item_id = bid.open_item_id
			and jk.inactive_date is null
			and jk.use_code = 1
			and jk.id_type = 4
			and jc.jnl_code_id = jk.jnl_code_id
			and bid.secondary_amount <> 0
      and ksoi.element = bid.subtype_code
      and kscc.account_category = v_account_category
      and upper(kscc.tipo_produto) = upper('FIXA')
      and upper(kscc.caracteristica_cliente) = upper(v_tipo_modalidade)
      and upper(kscc.tipo_contabilizacao) = upper(v_tipo_contabilizacao) 
group by bid.subtype_code, jc.fml_acct_db, jc.fml_acct_cr, bid.type_code, 
			  CASE WHEN bid.open_item_id = 20 THEN 'D' || v_divisao_t
            WHEN bid.open_item_id IN (90,91,92,93,94,95,99,100,101) THEN ksoi.ordem_interna || v_divisao_t 
            ELSE 'T' ||SUBSTR(v_divisao_t,1,2) || ksoi.ordem_interna
        END, kscc.centro_custo; 

-------------------------------------------------------------
 /* PJ1176 - ORIONII-885 */ 
FUNCTION acres_prefixo_centro_custo (v_open_item_id IN NUMBER, v_divisao IN VARCHAR2, v_centro_custo IN VARCHAR2) RETURN VARCHAR2 IS

v_open_centro_custo VARCHAR2(20);
v_aux_centro_custo  VARCHAR2(20);

BEGIN
	BEGIN

		IF v_open_item_id IN (90,91,92) THEN
			v_aux_centro_custo := '30T' || v_centro_custo;
		ELSIF v_open_item_id IN (93,94,95) THEN
			v_aux_centro_custo := '30P' || v_centro_custo;
		ELSE		
			IF v_open_item_id = 20 THEN
				v_open_centro_custo := 'D';
			ELSE
				v_open_centro_custo := 'T';
			END IF;
			v_aux_centro_custo := SUBSTR(v_divisao,1,2) || v_open_centro_custo || v_centro_custo;
		END IF;

	EXCEPTION
	WHEN OTHERS THEN
		DBMS_OUTPUT.PUT_LINE('Erro ao acrescentar prefixo no centro custo.');
		DBMS_OUTPUT.PUT_LINE('open_item_id: ' || v_open_item_id);
		DBMS_OUTPUT.PUT_LINE('ERRO : '||SQLERRM(SQLCODE));
	END;

	RETURN v_aux_centro_custo;
	
END acres_prefixo_centro_custo;


--÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷
--÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷ I N I C I O ÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷
--÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷

BEGIN
  dbms_output.put_line('INICIO = ' || to_char(SYSDATE, 'DD/MM/YYYY HH24:MI:SS'));
  
  OPEN busca_param_percent;

	FETCH busca_param_percent into busca_cursor;
	
	if busca_param_percent%NOTFOUND then
	
	v_valida_param := 1;
	
	end if;
	
 CLOSE busca_param_percent;
	
if( v_valida_param = 1) 
 THEN
	

  BEGIN
  
    SELECT nvl(COUNT(1), 0)
    INTO   v_valida_tab_lotes
    FROM   bill_period_ref bpr
    WHERE  bpr.bill_period IN
           (SELECT X.BILL_PERIOD
            FROM   GVT_PROCESSAMENTO_CICLO X
            WHERE  X.PROCESSAMENTO = UPPER(v_ciclo));
  
    IF v_valida_tab_lotes = 0
    THEN
      dbms_output.put_line('CICLO NAO CADASTRADO TABELA bill_period_ref = ' || v_ciclo);
    
      SELECT v_ciclo / 2 INTO v_valida_tab_lotes FROM dual;
    
    ELSE
    
  SELECT UPPER(v_ciclo) || 'JNL' || lpad(substr(dbms_reputil.global_name(), 5), 2, 0) || '.' ||
                (SELECT to_char(p.j_cd, 'YYMMDD_HH24MISS') || '_' || lpad(p.jr, 8, 0) ||
                     lpad(p.jrs, 2, 0)
              FROM   (SELECT MAX(j.jnl_ref_no) jr,
                             MAX(j.jnl_ref_no_serv) jrs,
                             MAX(j.jnl_completion_dt) j_cd
                      FROM   jnl_runs_status j
                      WHERE  jnl_type = 4
                      AND    run_status = 1) p) lote
      INTO   v_lote
      FROM   dual;
    
      SELECT MAX(j.jnl_ref_no) jnl_ref_no,
             MAX(j.jnl_ref_no_serv) jnl_ref_no_serv
      INTO   v_jnl_ref_no,
             v_jnl_ref_no_serv
      FROM   jnl_runs_status j
      WHERE  jnl_type = 1
      AND    run_status = 1;
    
      SELECT nvl(COUNT(1), 0)
      INTO   v_valida_tab_lotes
      FROM   GVT_Kenan_sap_sped_lotes lt
      WHERE  lt.jnl_ref_no = v_jnl_ref_no;
    
      IF v_valida_tab_lotes > 0
      THEN
      
        SELECT lt.lote
        INTO   v_lote
        FROM   GVT_Kenan_sap_sped_lotes lt
        WHERE  lt.jnl_ref_no = v_jnl_ref_no;
      
        dbms_output.put_line('LOTE JA CADASTRADO TABELA gvt_kenan_sap_sped_lotes = ' ||
                             v_jnl_ref_no || '   LOTE = ' || v_lote);
      
        SELECT 'ERRO' / 2 INTO v_valida_tab_lotes FROM dual;
      
      ELSE
        INSERT INTO GVT_Kenan_sap_sped_lotes
        VALUES
          (v_lote,
           v_jnl_ref_no,
           v_jnl_ref_no_serv,
           SYSDATE,
           'FII001',
           NULL,
           NULL,
           NULL,
           0,
           0,
           0,
           NULL,
           NULL,
           v_lote,
           NULL);
      
        COMMIT;
      
      END IF;
    
    END IF;
  
  EXCEPTION
    WHEN OTHERS THEN
      dbms_output.put_line('ERRO BUSCA / GRAVA TABELA gvt_kenan_sap_sped_lotes');
      dbms_output.put_line('ERRO ORACLE = ' || SQLERRM);
  END;

  FOR r_lt IN c_lotes
  LOOP
    v_lotes_lidos     := v_lotes_lidos + 1;
    v_jnl_ref_no      := r_lt.jnl_ref_no;
    v_jnl_ref_no_serv := r_lt.jnl_ref_no_serv;
    dbms_output.put_line('LOTE = ' || r_lt.lote || '  JNL_REF_NO = ' || r_lt.jnl_ref_no ||
                         '  JNL_REF_NO_SERV = ' || r_lt.jnl_ref_no_serv);
  
    v_bill_ref_no       := 0;
  v_bill_ref_no_2     := 0;
    v_open_item_id      := 9;
  v_open_item_id_2    := 9;
  v_bill_ref_no_3    := 0;
  v_open_item_id_3   := 9;
   v_bill_ref_no_4    := 0;
  v_open_item_id_4   := 9;
    v_tot_registros     := 0;
    v_tot_valor_credito := 0;
    v_tot_valor_debito  := 0;
    v_mkt_code          := 0;
    v_vlr_guardar       := 0;
    v_vlr_dif           := 0;
    v_rev_ctr           := 0;
  
    BEGIN
      SELECT jd.jnl_ref_no,
             jd.jnl_ref_no_serv
      INTO   v_jnl_ref_no,
             v_jnl_ref_no_serv
      FROM   jnl_detail jd
      WHERE  jd.jnl_ref_no = r_lt.jnl_ref_no
      AND    jd.jnl_ref_no_serv = r_lt.jnl_ref_no_serv
      AND    rownum = 1;
    
    
     SELECT to_char(j.jnl_end_dt, 'yyyymm')
        INTO vPeriodo
        FROM jnl_runs_status j
       WHERE j.jnl_ref_no = v_jnl_ref_no
         AND j.jnl_ref_no_serv = v_jnl_ref_no_serv;
  
      -- CAPTURAR SEQUENCIAL GUARDAR NA V_TOT_REG_UF  
      SELECT s.sequencial
      INTO   v_tot_reg_uf
      FROM   arborgvt_journals.GVT_Kenan_sap_sped_sequencial s
      WHERE  s.interface = 'FII001';
    
      dbms_output.put_line('VALOR SEQUENCIAL INICIO = ' || v_tot_reg_uf);
	  
	  
 --ORIONII-840
--*******************************************************************************
          -------------ABRINDO CURSOR c_jnl_detail_4----------
  --*******************************************************************************
  
  FOR r_jd_4 IN c_jnl_detail_4(v_jnl_ref_no, v_jnl_ref_no_serv) LOOP 
   ------------------------------------------------------------------------------- 
      --------------ABRINDO CURSOR c_usg_d-----------------
   ------------------------------------------------------------------------------- 

  FOR r_usg_1 IN c_usg_d(r_jd_4.bill_ref_no,
                         r_jd_4.bill_ref_resets,
                         r_jd_4.subscr_no,
                         r_jd_4.subscr_no_resets, 
                         r_jd_4.account_category, 
                         r_jd_4.divisao,
                         r_jd_4.tipo_modalidade,
                         r_jd_4.tipo_contabilizacao ) LOOP 
	  
	   IF v_bill_ref_no_4 <> r_jd_4.bill_ref_no OR v_open_item_id_4 <> r_jd_4.open_item_id
        THEN
          v_bill_ref_no_4  := r_jd_4.bill_ref_no;
          v_open_item_id_4 := r_jd_4.open_item_id;
        
          BEGIN
            SELECT s.sin_seq_ref_no,
                   s.sin_seq_ref_resets,
                   s.full_sin_seq,
                   s.group_id v_prep_status,
                   s.open_item_id
            INTO   v_sin_seq_ref_no_4,
                   v_sin_seq_ref_resets_4,
                   v_full_sin_seq_4,
                   v_group_id_2,
                   v_open_item_id_ssn_4
            FROM   sin_seq_no s
            WHERE  s.bill_ref_no = r_jd_4.bill_ref_no
            AND    s.bill_ref_resets =  r_jd_4.bill_ref_resets
            AND    s.open_item_id =
                   decode(r_jd_4.open_item_id, 1, 0, 2, 0, 3, 0, 91, 90, 92, 90, 94, 93, 95,
                           93, 97, 96, 98, 96, 101, 99, 100, 99,  r_jd_4.open_item_id); --PJ IPTV
          
          EXCEPTION
            WHEN no_data_found THEN
              BEGIN
                SELECT s.sin_seq_ref_no,
                       s.sin_seq_ref_resets,
                       s.full_sin_seq,
                       s.group_id v_prep_status,
                       s.open_item_id
                INTO   v_sin_seq_ref_no_4,
                       v_sin_seq_ref_resets_4,
                       v_full_sin_seq_4,
                       v_group_id_4,
                       v_open_item_id_ssn_4
                FROM   sin_seq_no s
                WHERE  s.bill_ref_no = r_jd_4.bill_ref_no
                AND    s.bill_ref_resets = 0
                AND    s.open_item_id = 0;
              
              EXCEPTION
                WHEN OTHERS THEN
                  v_full_sin_seq_4     := NULL;
                  v_open_item_id_ssn_4 := NULL;
              END;
            
            WHEN OTHERS THEN
              v_full_sin_seq_4     := NULL;
              v_open_item_id_ssn_4 := NULL;
          END;
        END IF;

							IF SUBSTR(nvl(r_jd_4.fml_acct_cr, '99999999'),1,1) IN(1,2) THEN
								IF SUBSTR(nvl(r_jd_4.fml_acct_cr, '99999999'),1,1) = SUBSTR(nvl(r_jd_4.fml_acct_db, '77777777'),1,1) then
									v_centro_custo := null;
									v_ordem_interna := null;
								END IF;
							END IF;
							
							IF r_jd_4.use_code = 1  -- PROJETO VENCIMENTO / VENCIMENTO
							THEN
		
								BEGIN
									if r_jd_4.use_code = 4 then
									r_jd_4.valor:= r_jd_4.valor *-1;
									end if;
						
									v_centro_custo := acres_prefixo_centro_custo(r_jd_4.open_item_id, r_jd_4.divisao, r_usg_1.centro_custo);
									
									INSERT INTO arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET
										VALUES      
										(r_lt.lote,
										'D',
										'BRL',
										v_full_sin_seq_4,
										r_jd_4.pos_neg,
										v_jnl_ref_no,
										v_jnl_ref_no_serv,
										r_jd_4.mkt_code,
										r_jd_4.jnl_subcycle_end_dt,
										r_jd_4.id_type,
										r_jd_4.id_type2,
										nvl(r_usg_1.fml_acct_cr, '99999999'),
										nvl(r_usg_1.fml_acct_db, '77777777'),
										r_jd_4.rev_rcv_cost_ctr,
										r_jd_4.bill_ref_no,
										r_jd_4.bill_ref_resets,
										r_jd_4.account_no,
										r_jd_4.external_id,
										r_jd_4.account_category,
										r_jd_4.open_item_id,
										r_usg_1.valor,
										r_jd_4.use_code,
										r_jd_4.ciclo,
										r_jd_4.divisao,
										v_centro_custo, 
										r_usg_1.subtype_code,
										r_usg_1.ordem_interna,
										0,
										0,
										0);
								
								END;
							END IF;
							v_commit := v_commit + 1;
              IF v_commit > 10000 THEN
                COMMIT;
                v_commit := 0;
              END IF;
						END LOOP;
					END LOOP;
	
					commit;	
    
					-------------------------------------------------------------------------------
					--------------ABRINDO CURSOR c_jnl_detail_2------------------------------------
					-------------------------------------------------------------------------------
					FOR r_jd_2 IN c_jnl_detail_2(v_jnl_ref_no, v_jnl_ref_no_serv)
					LOOP
						
					IF v_bill_ref_no_2 <> r_jd_2.bill_ref_no OR v_open_item_id_2 <> r_jd_2.open_item_id
						THEN
						v_bill_ref_no_2  := r_jd_2.bill_ref_no;
						v_open_item_id_2 := r_jd_2.open_item_id;
						
						BEGIN
							SELECT s.sin_seq_ref_no,
								s.sin_seq_ref_resets,
								s.full_sin_seq,
								s.group_id v_prep_status,
								s.open_item_id
							INTO   v_sin_seq_ref_no_2,
								v_sin_seq_ref_resets_2,
								v_full_sin_seq_2,
								v_group_id_2,
								v_open_item_id_ssn_2
							FROM   sin_seq_no s
							WHERE  s.bill_ref_no = r_jd_2.bill_ref_no
							AND    s.bill_ref_resets = 0
							AND    s.open_item_id =
								decode(r_jd_2.open_item_id, 1, 0, 2, 0, 3, 0, 91, 90, 92, 90, 94, 93, 95,
										93, 97, 96, 98, 96, 101, 99, 100, 99,  r_jd_2.open_item_id); --PJ IPTV
						
						EXCEPTION
							WHEN no_data_found THEN
							BEGIN
								SELECT s.sin_seq_ref_no,
									s.sin_seq_ref_resets,
									s.full_sin_seq,
									s.group_id v_prep_status,
									s.open_item_id
								INTO   v_sin_seq_ref_no_2,
									v_sin_seq_ref_resets_2,
									v_full_sin_seq_2,
									v_group_id_2,
									v_open_item_id_ssn_2
								FROM   sin_seq_no s
								WHERE  s.bill_ref_no = r_jd_2.bill_ref_no
								AND    s.bill_ref_resets = 0
								AND    s.open_item_id = 0;
							
							EXCEPTION
								WHEN OTHERS THEN
								v_full_sin_seq_2     := NULL;
								v_open_item_id_ssn_2 := NULL;
								
								--dbms_output.put_line('r_jd_2.bill_ref_no = ' || r_jd_2.bill_ref_no || '  r_jd_2.opem_item_id = ' || r_jd_2.open_item_id);
								--dbms_output.put_line('NDF ORACLE = ' || SQLERRM);
							END;
							
							WHEN OTHERS THEN
							v_full_sin_seq_2     := NULL;
							v_open_item_id_ssn_2 := NULL;
							
							--dbms_output.put_line('r_jd_2.bill_ref_no = ' || r_jd_2.bill_ref_no || '  r_jd_2.opem_item_id = ' || r_jd_2.open_item_id);
							--dbms_output.put_line('ORACLE = ' || SQLERRM);
						END;
						END IF;
						
						/* PJ1176 - ORIONII-885 */
					--v_centro_custo := r_jd_2.centro_custo;
					
					v_ordem_interna := r_jd_2.ordem_interna;
					IF SUBSTR(nvl(r_jd_2.fml_acct_cr, '99999999'),1,1) IN(1,2) THEN
					IF SUBSTR(nvl(r_jd_2.fml_acct_cr, '99999999'),1,1) = SUBSTR(nvl(r_jd_2.fml_acct_db, '77777777'),1,1) then
						v_centro_custo := null;
					v_ordem_interna := null;
					END IF;
					END IF;
					
					IF r_jd_2.use_code = 1  -- PROJETO VENCIMENTO / VENCIMENTO
						THEN
					
							BEGIN
								if r_jd_2.use_code = 4 then
								r_jd_2.valor:= r_jd_2.valor *-1;
								end if;
							  v_count := 0;
							
                if (r_jd_2.id_type = 3)then 
								select  count(1) INTO v_count
								from arborgvt_journals.gvt_kenan_sap_param gks, product p, product_rate_key pk
									where  p.TRACKING_ID = r_jd_2.tracking_id
										and pk.TRACKING_ID (+)  = p.tracking_id 
										and gks.element = p.element_id  
										and (gks.component_id = p.COMPONENT_ID or gks.component_id = 0)
								and(( gks.JURISDICTION = pk.JURISDICTION 
								and gks.units_type = pk.UNITS_TYPE 
								and pk.INACTIVE_DT is null) or 
									( gks.JURISDICTION = 0
									and gks.units_type = 0
									))
								and gks.tipo_lancamento = r_jd_2.id_type
								and  gks.DATA_INATIVACAO is null
								and p.PRODUCT_INACTIVE_DT is null;
								
                end if;
						
								v_centro_custo := acres_prefixo_centro_custo(r_jd_2.open_item_id, r_jd_2.divisao, r_jd_2.centro_custo);
								--
								
								if (v_count > 0) THEN
								
									v_Tracking_id := r_jd_2.tracking_id;
									v_id_type := r_jd_2.id_type;
									v_total_amount := 0;
									v_Temp_amount := 0;
									index_curso  := 0;
									
								
									FOR r_abert IN C_ABERTURA_FISCAL(v_Tracking_id, v_id_type)
									LOOP
														
										index_curso := index_curso +1;
										v_Temp_amount := ROUND(r_jd_2.valor*(r_abert.percentual/100),2);
										v_total_amount := v_total_amount + v_Temp_amount;
										
										
										if index_curso = v_count 
										then
											if v_total_amount < r_jd_2.valor
											then
											v_temp_amount := v_temp_amount + (r_jd_2.valor - v_total_amount );
											
											else 
											v_temp_amount := v_temp_amount - (v_total_amount  - r_jd_2.valor);
											
											end if;
										end if;
										
										INSERT INTO arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET
										VALUES      
										(r_lt.lote,
										'D',
										'BRL',
										v_full_sin_seq_2,
										r_jd_2.pos_neg,
										v_jnl_ref_no,
										v_jnl_ref_no_serv,
										r_jd_2.mkt_code,
										r_jd_2.jnl_subcycle_end_dt,
										r_jd_2.id_type,
										r_jd_2.id_type2,
										nvl(r_abert.CONTA_PARA, '99999999'),
										nvl(r_jd_2.fml_acct_db, '77777777'),
										r_jd_2.rev_rcv_cost_ctr,
										r_jd_2.bill_ref_no,
										r_jd_2.bill_ref_resets,
										r_jd_2.account_no,
										r_jd_2.external_id,
										r_jd_2.account_category,
										r_jd_2.open_item_id,
										v_Temp_amount,
										r_jd_2.use_code,
										r_jd_2.ciclo,
										r_jd_2.divisao,
										v_centro_custo,
										r_jd_2.id_value,
										r_jd_2.ordem_interna,
										r_abert.component_id,
										r_abert.jurisdiction,
										r_abert.units_type
										);
										
									
									END LOOP;
								
								
								ELSE 
								
									INSERT INTO arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET
									VALUES      
									(r_lt.lote,
									'D',
									'BRL',
									v_full_sin_seq_2,
									r_jd_2.pos_neg,
									v_jnl_ref_no,
									v_jnl_ref_no_serv,
									r_jd_2.mkt_code,
									r_jd_2.jnl_subcycle_end_dt,
									r_jd_2.id_type,
									r_jd_2.id_type2,
									nvl(r_jd_2.fml_acct_cr, '99999999'),
									nvl(r_jd_2.fml_acct_db, '77777777'),
									r_jd_2.rev_rcv_cost_ctr,
									r_jd_2.bill_ref_no,
									r_jd_2.bill_ref_resets,
									r_jd_2.account_no,
									r_jd_2.external_id,
									r_jd_2.account_category,
									r_jd_2.open_item_id,
									r_jd_2.valor,
									r_jd_2.use_code,
									r_jd_2.ciclo,
									r_jd_2.divisao,
									v_centro_custo,
									r_jd_2.id_value,
									r_jd_2.ordem_interna,
									0,
									0,
									0);
								
								END IF;
							EXCEPTION
								WHEN OTHERS THEN
								dbms_output.put_line('r_jd_2.bill_ref_no = ' || r_jd_2.bill_ref_no ||
											'  r_jd_2.opem_item_id = ' || r_jd_2.open_item_id);
								dbms_output.put_line('INSERT ORACLE = ' || SQLERRM);          
							END;          
					
					-- receita antecipada
						ELSIF r_jd_2.use_code = 2 -- RECEITA ANTECIPADA  -- PROJETO VENCIMENTO / VENCIMENTO
							AND r_jd_2.id_type = 3 --SOMENTE DE RC  -- PROJETO VENCIMENTO / VENCIMENTO
						THEN
						
						BEGIN
						
							INSERT INTO arborgvt_journals.VIVO2_SPED_RECEITA_ANTECIPADA
							VALUES
							(vPeriodo, --AnoMesReferencia, 
							'PLSPED_0701', --
							'RC', --
							r_jd_2.ciclo, -- 
							r_jd_2.account_category, -- 
							r_jd_2.id_value, --
							2, --
							r_jd_2.id_value, --
							1, --
							r_jd_2.id_value, --
							r_jd_2.valor * -100, --o valor negativo inverte a conta contábil
							r_jd_2.external_id, --   --_external_id_conta, -- 
							r_jd_2.mkt_code, --
							r_jd_2.account_no, --
							nvl(r_jd_2.fml_acct_db, '77777777'),
							nvl(r_jd_2.fml_acct_cr, '99999999'),
							r_jd_2.ordem_interna, --
							v_centro_custo, --
							r_jd_2.divisao,
							r_jd_2.open_item_id); --
						
						EXCEPTION
							WHEN OTHERS THEN
							dbms_output.put_line('erro inserindo receita anteciada da conta [' || r_jd_2.external_id ||
												']  e element_id [' || r_jd_2.id_value || ']');
							dbms_output.put_line('INSERT ORACLE = ' || SQLERRM);
							
						END;
						
						END IF; --FIM DO IF DA RECEITA ANTECIPADA -- PROJETO VENCIMENTO / VENCIMENTO
				
						-- ABAIXO ADICIONADO BATIMENTO SAP X CONT_DET
						v_commit := v_commit + 2;
						IF v_commit > 10000
						THEN
						COMMIT;
						v_commit := 0;
						END IF;
						-- ACIMA ADICIONADO BATIMENTO SAP X CONT_DET
						
					END LOOP;
					
					COMMIT;
					
					--dbms_output.put_line('FIM LEITURA CURSOR c_jnl_detail_2 HORA = '|| TO_CHAR(SYSDATE,'RRRRMMDDHH24MISS'));
    
					--INSOC-027.1
					--*******************************************************************************
								-------------ABRINDO CURSOR c_jnl_detail_3----------
					--*******************************************************************************
  
					FOR r_jd_3 IN c_jnl_detail_3(v_jnl_ref_no, v_jnl_ref_no_serv) LOOP 
						------------------------------------------------------------------------------- 
							--------------ABRINDO CURSOR c_bill_detail_1-----------------
						------------------------------------------------------------------------------- 
						----      FOR r_bid_1 IN c_bill_detail_1(r_jd_3.bill_ref_no,r_jd_3.bill_ref_resets,r_jd_3.tax_type_code,r_jd_3.subscr_no,r_jd_3.subscr_no_resets, r_jd_3.id_type2) LOOP
						-- CORRECAO PARAMETROS CURSOR BATIMENTO SAP X CONT_DET
						FOR r_bid_1 IN c_bill_detail_1(r_jd_3.bill_ref_no,r_jd_3.bill_ref_resets,r_jd_3.tax_type_code,
																					 r_jd_3.subscr_no,r_jd_3.subscr_no_resets, r_jd_3.id_type2, 
																					 r_jd_3.Open_item_id, r_jd_3.account_category, r_jd_3.use_code  
																					 ) LOOP 
      
							BEGIN
								select nvl(COUNT(1), 0)
								INTO    V_VALIDA_IMPOSTO
								from    arborgvt_billing.Gvt_Classificacao_Tv tv
								where   id_classificacao = 3 
									and   tv.type_code = r_bid_1.type_code
									and   tv.subtype_code = r_bid_1.subtype_code;
							EXCEPTION
							WHEN OTHERS THEN
								dbms_output.put_line('ERRO AO VERIFICAR IMPOSTO. ');
								dbms_output.put_line('SELECT ORACLE = ' || SQLERRM);
							END;  
        
							BEGIN
			
								v_prefix:= NULL;
								---Regra ICMS
								if r_jd_3.tax_type_code between 1000000 and 1999999 then
									if r_jd_3.open_item_id in (90,91,92,99,100,101) then
									v_prefix:='31986174';
									else
									v_prefix:='817';
									end if;
								--Regra ISS
								elsif r_jd_3.tax_type_code between 2000000 and 2999999 then
									if r_jd_3.open_item_id in (90,91,92,99,100,101) then
									v_prefix:='31986171';
									else
									v_prefix:='814';
									end if;
								end if;
			
								IF V_VALIDA_IMPOSTO = 0 THEN
									select distinct jc.fml_acct_db cta_db, jc.fml_acct_cr cta_cr
									into   v_cta_db, v_cta_cred
									from   jnl_keys jk, jnl_custom jc
									where  jk.id_type = decode(r_bid_1.type_code,2,3,3,6,7,4)
									and  jk.id_value = r_bid_1.subtype_code
									and  jk.inactive_date is null 
									and  jk.account_category = r_jd_3.account_category    
									and  jk.use_code = 1 
									and  jc.jnl_code_id = jk.jnl_code_id
									-- INICIO ABAIXO  BATIMENTO SAP X CONT_DET
									and  (jk.Open_Item_Id is null
										or  jk.Open_Item_Id = r_jd_3.Open_Item_Id
										or  jk.Open_Item_Id = 20); 
									-- FINAL ACIMA BATIMENTO SAP X CONT_DET
									if v_prefix is not null then
										if length(v_prefix) = 3 then
										v_cta_db_new := substr(v_prefix,1,3) || substr(v_cta_cred,4); --replace(v_cta_cred,substr(v_cta_cred,1,3),substr(v_prefix,1,3));
										else
										v_cta_db_new := v_prefix;
										end if;
									else 
										v_cta_db_new := r_jd_3.fml_acct_db;
									end if;      
								ELSE 
									v_cta_db_new := r_jd_3.fml_acct_db;
								END IF;
						
							EXCEPTION
								WHEN OTHERS THEN
									dbms_output.put_line('SELECT ORACLE = ' || SQLERRM);
							END;    
	
		
							IF v_bill_ref_no_3 <> r_jd_3.bill_ref_no OR v_open_item_id_3 <> r_jd_3.open_item_id
								THEN
									v_bill_ref_no_3  := r_jd_3.bill_ref_no;
									v_open_item_id_3 := r_jd_3.open_item_id;
			
									BEGIN
										SELECT s.sin_seq_ref_no,
											s.sin_seq_ref_resets,
											s.full_sin_seq,
											s.group_id v_prep_status,
											s.open_item_id
										INTO   v_sin_seq_ref_no_2,
											v_sin_seq_ref_resets_2,
											v_full_sin_seq_2,
											v_group_id_2,
											v_open_item_id_ssn_2
										FROM   sin_seq_no s
										WHERE  s.bill_ref_no = r_jd_3.bill_ref_no
										AND    s.bill_ref_resets = 0
										AND    s.open_item_id =
											decode(r_jd_3.open_item_id, 1, 0, 2, 0, 3, 0, 91, 90, 92, 90, 94, 93, 95,
													93, 97, 96, 98, 96,100,99,101,99, r_jd_3.open_item_id); --PJ IPTV
			
									EXCEPTION
									WHEN no_data_found THEN
										BEGIN
											SELECT s.sin_seq_ref_no,
												s.sin_seq_ref_resets,
												s.full_sin_seq,
												s.group_id v_prep_status,
												s.open_item_id
											INTO   v_sin_seq_ref_no_2,
												v_sin_seq_ref_resets_2,
												v_full_sin_seq_2,
												v_group_id_2,
												v_open_item_id_ssn_2
											FROM   sin_seq_no s
											WHERE  s.bill_ref_no = r_jd_3.bill_ref_no
											AND    s.bill_ref_resets = 0
											AND    s.open_item_id = 0;
										
										EXCEPTION
											WHEN OTHERS THEN
											v_full_sin_seq_2     := NULL;
											v_open_item_id_ssn_2 := NULL;
					
										END;
				
									WHEN OTHERS THEN
										v_full_sin_seq_2    := NULL;
										v_open_item_id_ssn_2 := NULL;
									
										--dbms_output.put_line('r_jd_2.bill_ref_no = ' || r_jd_2.bill_ref_no || '  r_jd_2.opem_item_id = ' || r_jd_2.open_item_id);
			
									END;
							END IF;
			
							/* PJ1176 - ORIONII-885 */
							--v_centro_custo := r_jd_3.centro_custo;
		
							select 
								case when r_jd_3.ordem_interna is not null 
									then r_jd_3.ordem_interna||r_bid_1.ordem_interna
								else
									r_bid_1.ordem_interna||(select divisao from arborgvt_journals.gvt_kenan_sap_divisao kd where kd.mkt_code = r_jd_3.mkt_code)
								end 
							into v_ordem_interna
							from dual;
		
							IF SUBSTR(nvl(r_jd_3.fml_acct_cr, '99999999'),1,1) IN(1,2) THEN
								IF SUBSTR(nvl(r_jd_3.fml_acct_cr, '99999999'),1,1) = SUBSTR(nvl(v_cta_db_new, '77777777'),1,1) then
									v_centro_custo := null;
									v_ordem_interna := null;
								END IF;
							END IF;    
						
							BEGIN
		
								if r_jd_3.use_code = 4 then
									r_bid_1.valor := r_bid_1.valor *-1;
								end if;
								
								v_count := 0 ;
		
                if (r_bid_1.type_code = 2) then
								select  count(1) INTO v_count
									from arborgvt_journals.gvt_kenan_sap_param gks, product p, product_rate_key pk
										where  p.TRACKING_ID = r_bid_1.tracking_id
											and pk.TRACKING_ID (+)  = p.tracking_id 
											and gks.element = p.element_id  
											and (gks.component_id = p.COMPONENT_ID or gks.component_id = 0)
									and(( gks.JURISDICTION = pk.JURISDICTION 
									and gks.units_type = pk.UNITS_TYPE 
									and pk.INACTIVE_DT is null) or 
										( gks.JURISDICTION = 0
										and gks.units_type = 0
										))
									and gks.tipo_lancamento = r_jd_3.id_type
									and  gks.DATA_INATIVACAO is null
									and p.PRODUCT_INACTIVE_DT is null;
                 end if;
									
									v_centro_custo := acres_prefixo_centro_custo(r_jd_3.open_item_id, r_jd_3.divisao, r_jd_3.centro_custo);
									--
									
									if (v_count > 0) THEN
									
										v_Tracking_id := r_bid_1.tracking_id;
										
										v_total_amount := 0;
										v_Temp_amount := 0;
										index_curso  := 0;
										v_id_type := r_jd_3.id_type;
									
										FOR r_abert IN C_ABERTURA_FISCAL(v_Tracking_id, v_id_type)
										LOOP
										
										
											index_curso := index_curso +1;
											v_Temp_amount :=ROUND(r_bid_1.valor*(r_abert.percentual/100),2);
											v_total_amount := v_total_amount + v_Temp_amount;
											
											
											if index_curso = v_count 
											then
												if v_total_amount < r_bid_1.valor
												then
												v_temp_amount := v_temp_amount + (r_bid_1.valor -  v_total_amount);
												
												else 
												v_temp_amount := v_temp_amount - ( v_total_amount -  r_bid_1.valor);
												
												end if;
											end if;
											
											/* PJ1176 - ORIONII-885*/
											/*BUG-1277*/
											v_conta_contabil_credito :=
											CASE
												WHEN r_jd_3.tax_type_code between 1000000 and 1999999    -- ICMS
													THEN
														(CASE
															WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41111'
																THEN '21132118'                                                     
															WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41121'
																THEN '21132118'
															ELSE r_jd_3.fml_acct_cr
														END)
												WHEN r_jd_3.tax_type_code between 2000000 and 2999999    -- ISS
													THEN
														(CASE
															WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41111'
																THEN '21133111'
															WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41121'
																THEN '21133111'
															ELSE r_jd_3.fml_acct_cr
														END)
												ELSE r_jd_3.fml_acct_cr
											END;
											
											v_conta_contabil_debito := r_abert.CONTA_PARA;
															
										
											INSERT INTO arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET
											VALUES(r_lt.lote,
											'D',
											'BRL',
											v_full_sin_seq_2,
											r_jd_3.pos_neg,
											v_jnl_ref_no,
											v_jnl_ref_no_serv,
											r_jd_3.mkt_code,
											r_jd_3.jnl_subcycle_end_dt,
											r_jd_3.id_type,
											r_jd_3.id_type2,
											nvl(v_conta_contabil_credito, '99999999'), --usar a cont contábil após aplicado o replace 
											nvl(v_conta_contabil_debito, '77777777'), --usar a conta contábil de débito utilizada pelo journal 
											r_jd_3.rev_rcv_cost_ctr,
											r_jd_3.bill_ref_no,
											r_jd_3.bill_ref_resets,
											r_jd_3.account_no,
											r_jd_3.external_id,
											r_jd_3.account_category,
											v_open_item_id_ssn_2,
											v_temp_amount, --usar o valor do imposto calculado para o produto que incidiu o imposto, retornado no cursor de detalhes da fatura (r_bid_1) 
											r_jd_3.use_code,
											r_jd_3.ciclo,
											r_jd_3.divisao,
											v_centro_custo,
											r_bid_1.subtype_code, --usar o código do produto retornado no cursor de detalhes da fatura (r_bid_1) 
											v_ordem_interna,
											r_abert.component_id,
											r_abert.jurisdiction,
											r_abert.units_type
											);
										
										END LOOP;
				
									ELSE
									
										/* PJ1176 - ORIONII-885*/
										/*BUG-1277*/
										v_conta_contabil_credito :=
										CASE
											WHEN r_jd_3.tax_type_code between 1000000 and 1999999    -- ICMS
												THEN
													(CASE
														WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41111'
															THEN '21132118'                                                     
														WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41121'
															THEN '21132118'
														ELSE r_jd_3.fml_acct_cr
													END)
											WHEN r_jd_3.tax_type_code between 2000000 and 2999999    -- ISS
												THEN
													(CASE
														WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41111'
															THEN '21133111'
														WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41121'
															THEN '21133111'
														ELSE r_jd_3.fml_acct_cr
													END)
											ELSE r_jd_3.fml_acct_cr
										END;
										
										v_conta_contabil_debito :=
										CASE
											WHEN r_jd_3.tax_type_code between 1000000 and 1999999    -- ICMS
												THEN
													(CASE
														WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41111'
															THEN '31986114'
														WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41121'
															THEN '31986124'
														ELSE v_cta_db_new
													END)
											WHEN r_jd_3.tax_type_code between 2000000 and 2999999    -- ISS
												THEN
													(CASE
														WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41111'
															THEN '31986111'
														WHEN substr(r_bid_1.fml_acct_cr, 1, 5) = '41121'
															THEN '31986121'
														ELSE v_cta_db_new
													END)
											ELSE v_cta_db_new
										END;
		
										INSERT INTO arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET
										VALUES(r_lt.lote,
											'D',
											'BRL',
											v_full_sin_seq_2,
											r_jd_3.pos_neg,
											v_jnl_ref_no,
											v_jnl_ref_no_serv,
											r_jd_3.mkt_code,
											r_jd_3.jnl_subcycle_end_dt,
											r_jd_3.id_type,
											r_jd_3.id_type2,
											nvl(v_conta_contabil_credito, '99999999'), --usar a cont contábil após aplicado o replace 
											nvl(v_conta_contabil_debito, '77777777'), --usar a conta contábil de débito utilizada pelo journal 
											r_jd_3.rev_rcv_cost_ctr,
											r_jd_3.bill_ref_no,
											r_jd_3.bill_ref_resets,
											r_jd_3.account_no,
											r_jd_3.external_id,
											r_jd_3.account_category,
											v_open_item_id_ssn_2,
											r_bid_1.valor, --usar o valor do imposto calculado para o produto que incidiu o imposto, retornado no cursor de detalhes da fatura (r_bid_1) 
											r_jd_3.use_code,
											r_jd_3.ciclo,
											r_jd_3.divisao,
											v_centro_custo,
											r_bid_1.subtype_code, --usar o código do produto retornado no cursor de detalhes da fatura (r_bid_1) 
											v_ordem_interna,
											0,
											0,
											0);
									END IF;
							EXCEPTION
							WHEN OTHERS THEN
								dbms_output.put_line('ERRO AO INSERIR NA TABELA GVT_KENAN_SAP_SPED_CONT_DET. ');
								dbms_output.put_line('INSERT ORACLE = ' || SQLERRM);
							END;
						END LOOP;

						v_commit := v_commit + 1;
						IF v_commit > 10000
							THEN
								COMMIT;
								v_commit := 0;
							END IF;
					END LOOP;
  
					-- ADICIONADO NO batimento JNL X CONT_DET
					COMMIT;
				--	dbms_output.put_line('FIM LEITURA CURSOR c_jnl_detail_3 HORA = '|| TO_CHAR(SYSDATE,'RRRRMMDDHH24MISS'));

  
					--******************************************************************************
						-- ABRINDO CURSOR C_JNL_DETAIL_D
						--******************************************************************************
					FOR r_jd_d IN c_jnl_detail_d(v_jnl_ref_no, v_jnl_ref_no_serv)
					LOOP
					--******************************************************************************
						-- ABRINDO CURSOR C_BILL_DETAIL_D
						--******************************************************************************
						FOR r_bill_d IN c_bill_detail_d(r_jd_d.bill_ref_no, 
																						r_jd_d.bill_ref_resets, 
																						r_jd_d.id_value, 
																						r_jd_d.subscr_no, 
																						r_jd_d.subscr_no_resets,
																						r_jd_d.tipo_contabilizacao,
																						r_jd_d.tipo_modalidade,
																						r_jd_d.account_category,
																						'FIXA')
						LOOP
							BEGIN
								select distinct jc.fml_acct_db cta_db, jc.fml_acct_cr cta_cr
								into v_cta_db, v_cta_cred
								from jnl_keys jk, jnl_custom jc
								where jk.id_type = decode(r_bill_d.type_code,2,3,3,6,7,4)
								and jk.id_value = r_bill_d.subtype_code
								and jk.inactive_date is null 
								and jk.account_category = r_jd_d.account_category    
								and jk.use_code = 1 
								and jc.jnl_code_id = jk.jnl_code_id; 
								
								if r_jd_d.fml_acct_cr is not null and substr(r_jd_d.fml_acct_cr,1,1) = 4 then
									v_cta_db_new := substr(r_jd_d.fml_acct_cr,1,2) || substr(v_cta_cred,3); --replace(v_cta_cred,substr(v_cta_cred,1,2),substr(r_jd_d.fml_acct_cr,1,2));
								else  
									v_cta_db_new := substr(r_jd_d.fml_acct_cr,1,3) || substr(v_cta_cred,4);  --replace(v_cta_cred,substr(v_cta_cred,1,3),substr(r_jd_d.fml_acct_cr,1,3));
								end if;
								
								if r_jd_d.fml_acct_db is not null and substr(r_jd_d.fml_acct_db,1,1) = 1 then
									v_cta_cred_new := v_cta_db;
								end if;
								
							EXCEPTION
							WHEN OTHERS THEN
								dbms_output.put_line('ERRO SELECIONAR/DEFINIR A CONTA DE DEBITO. ');
								dbms_output.put_line('SELECT ORACLE = ' || SQLERRM);
							END;
							
							IF (v_bill_ref_no_2 <> r_jd_d.bill_ref_no OR v_open_item_id_2 <> r_bill_d.open_item_id)
							THEN
								v_bill_ref_no_2  := r_jd_d.bill_ref_no;
								v_open_item_id_2 := r_bill_d.open_item_id;
		
								BEGIN
									SELECT s.sin_seq_ref_no,
										s.sin_seq_ref_resets,
										s.full_sin_seq,
										s.group_id v_prep_status,
										s.open_item_id
									INTO   v_sin_seq_ref_no_2,
										v_sin_seq_ref_resets_2,
										v_full_sin_seq_2,
										v_group_id_2,
										v_open_item_id_ssn_2
									FROM   sin_seq_no s
									WHERE  s.bill_ref_no = r_jd_d.bill_ref_no
									AND    s.bill_ref_resets = 0
									AND    s.open_item_id =
										decode(r_bill_d.open_item_id, 1, 0, 2, 0, 3, 0, 91, 90, 92, 90, 94, 93, 95,
											93, 97, 96, 98, 96, 101,99,100,99, r_bill_d.open_item_id); --PJ IPTV
									
								EXCEPTION
								WHEN no_data_found THEN
									BEGIN
									SELECT s.sin_seq_ref_no,
										s.sin_seq_ref_resets,
										s.full_sin_seq,
										s.group_id v_prep_status,
										s.open_item_id
									INTO   v_sin_seq_ref_no_2,
										v_sin_seq_ref_resets_2,
										v_full_sin_seq_2,
										v_group_id_2,
										v_open_item_id_ssn_2
									FROM   sin_seq_no s
									WHERE  s.bill_ref_no = r_jd_d.bill_ref_no
									AND    s.bill_ref_resets = 0
									AND    s.open_item_id = 0;
									
									EXCEPTION
										WHEN OTHERS THEN
										v_full_sin_seq_2     := NULL;
										v_open_item_id_ssn_2 := NULL;
									END;
								WHEN OTHERS THEN
									v_full_sin_seq_2     := NULL;
									v_open_item_id_ssn_2 := NULL;
	
								END;
							END IF;
	
							select 
								case 
								when v_open_item_id_2 in (90,91,92) then
									r_bill_d.ordem_interna||r_jd_d.divisao
								when v_open_item_id_2 in (93,94,95,99,100,101) then
									r_bill_d.ordem_interna||r_jd_d.divisao
								else    
									decode(r_jd_d.open_item_id,20,'D','T')||substr(r_jd_d.divisao,1,2)||r_bill_d.ordem_interna
								end
							into v_ordem_interna
							from dual;    
							
							IF SUBSTR(nvl(v_cta_db_new, '99999999'),1,1) IN(1,2) THEN
								IF SUBSTR(nvl(v_cta_db_new, '99999999'),1,1) = SUBSTR(nvl(v_cta_cred_new, '77777777'),1,1) then
									v_centro_custo := null;
									v_ordem_interna := null;
								END IF;    
							END IF;    
		
							BEGIN
			
								if r_jd_d.use_code = 4 then
								r_bill_d.discount_amount := r_bill_d.discount_amount *-1;
								end if;
			
								v_count := 0;
			
                if r_bill_d.type_code = 2 then
								select  count(1) INTO v_count
								from arborgvt_journals.gvt_kenan_sap_param gks, product p, product_rate_key pk
									where  p.TRACKING_ID = r_bill_d.tracking_id
										and pk.TRACKING_ID (+)  = p.tracking_id 
										and gks.element = p.element_id  
										and (gks.component_id = p.COMPONENT_ID or gks.component_id = 0)
								and(( gks.JURISDICTION = pk.JURISDICTION 
								and gks.units_type = pk.UNITS_TYPE 
								and pk.INACTIVE_DT is null) or 
									( gks.JURISDICTION = 0
									and gks.units_type = 0
									))
								and gks.tipo_lancamento = r_jd_d.id_type
								and  gks.DATA_INATIVACAO is null
								and p.PRODUCT_INACTIVE_DT is null;
			          end if;
																		
								v_centro_custo := acres_prefixo_centro_custo(r_jd_d.open_item_id, r_jd_d.divisao, r_bill_d.centro_custo);
								--
			
								if (v_count > 0) THEN
			
									v_Tracking_id := r_bill_d.tracking_id;
									
									v_total_amount := 0;
									v_Temp_amount := 0;
									index_curso  := 0;
									v_id_type := r_jd_d.id_type;
				
			
									FOR r_abert IN C_ABERTURA_FISCAL(v_Tracking_id, v_id_type)
									LOOP
								
										index_curso := index_curso +1;
										v_Temp_amount :=ROUND(r_bill_d.discount_amount*(r_abert.percentual/100),2);
										v_total_amount := v_total_amount + v_Temp_amount;
					
					
										if index_curso = v_count 
										then
											if v_total_amount < r_bill_d.discount_amount 
											then
											v_temp_amount := v_temp_amount + ( r_bill_d.discount_amount  - v_total_amount);
											
											else 
											v_temp_amount := v_temp_amount - ( v_total_amount   - r_bill_d.discount_amount);
											end if;
										end if;
					
										INSERT INTO arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET
											VALUES 
											(r_lt.lote,
											'D',
											'BRL',
											v_full_sin_seq_2,
											r_jd_d.pos_neg,
											v_jnl_ref_no,
											v_jnl_ref_no_serv,
											r_jd_d.mkt_code,
											r_jd_d.jnl_subcycle_end_dt,
											r_jd_d.id_type,
											r_jd_d.id_type2,
											nvl(r_abert.CONTA_PARA, '99999999'), 
											nvl(v_cta_cred_new, '77777777'), 
											r_jd_d.rev_rcv_cost_ctr,
											r_jd_d.bill_ref_no,
											r_jd_d.bill_ref_resets,
											r_jd_d.account_no,
											r_jd_d.external_id,
											r_jd_d.account_category,
											v_open_item_id_2,
											v_temp_amount, 
											r_jd_d.use_code,
											r_jd_d.ciclo,
											case 
											when v_open_item_id_ssn_2 in (90,91,92,93,94,95) then
												'30PR'
											else    
												r_jd_d.divisao
											end,
											v_centro_custo,
											r_bill_d.subtype_code,  
											v_ordem_interna,
											r_abert.component_id,
											r_abert.jurisdiction,
											r_abert.units_type
											);
									END LOOP;
			
								ELSE 
									INSERT INTO arborgvt_journals.GVT_KENAN_SAP_SPED_CONT_DET
									VALUES 
									(r_lt.lote,
									'D',
									'BRL',
									v_full_sin_seq_2,
									r_jd_d.pos_neg,
									v_jnl_ref_no,
									v_jnl_ref_no_serv,
									r_jd_d.mkt_code,
									r_jd_d.jnl_subcycle_end_dt,
									r_jd_d.id_type,
									r_jd_d.id_type2,
									nvl(v_cta_db_new, '99999999'), 
									nvl(v_cta_cred_new, '77777777'), 
									r_jd_d.rev_rcv_cost_ctr,
									r_jd_d.bill_ref_no,
									r_jd_d.bill_ref_resets,
									r_jd_d.account_no,
									r_jd_d.external_id,
									r_jd_d.account_category,
									v_open_item_id_2,
									r_bill_d.discount_amount, 
									r_jd_d.use_code,
									r_jd_d.ciclo,
									case 
									when v_open_item_id_ssn_2 in (90,91,92,93,94,95) then
										'30PR'
									else    
										r_jd_d.divisao
									end,
									v_centro_custo,
									r_bill_d.subtype_code,  
									v_ordem_interna,
									0,
									0,
									0);
								END IF;
	
							EXCEPTION
							WHEN OTHERS THEN
								dbms_output.put_line('ERRO AO INSERIR NA TABELA GVT_KENAN_SAP_SPED_CONT_DET: ' || 'r_jd_d.bill_ref_no = ' 
											|| r_jd_d.bill_ref_no ||'  r_jd_d.opem_item_id = ' || r_jd_d.open_item_id);
								dbms_output.put_line('INSERT ORACLE = ' || SQLERRM);          
							END;          
		
							v_commit := v_commit + 2;
							IF v_commit > 10000
								THEN
									COMMIT;
									v_commit := 0;
							END IF;
						END LOOP;
					END LOOP;      
					
					commit;
					
					--******************************************************************************
					-- ABRINDO CURSOR JNL_DETAIL
					--******************************************************************************
					
					FOR r_jd IN c_jnl_detail(r_lt.lote, v_jnl_ref_no, v_jnl_ref_no_serv)
					LOOP
						v_linha := v_linha + 1;
      
						/*IF v_bill_ref_no <> r_jd.bill_ref_no OR v_open_item_id <> r_jd.open_item_id
						THEN
						v_bill_ref_no  := r_jd.bill_ref_no;
						v_open_item_id := r_jd.open_item_id;
						
						BEGIN
							SELECT s.sin_seq_ref_no,
								s.sin_seq_ref_resets,
								s.full_sin_seq,
								s.group_id v_prep_status,
								s.open_item_id
							INTO   v_sin_seq_ref_no,
								v_sin_seq_ref_resets,
								v_full_sin_seq,
								v_group_id,
								v_open_item_id_ssn
							FROM   sin_seq_no s
							WHERE  s.bill_ref_no = r_jd.bill_ref_no
							AND    s.bill_ref_resets = 0
							AND    s.open_item_id =
								decode(r_jd.open_item_id, 1, 0, 2, 0, 3, 0, 91, 90, 92, 90, 94, 93, 95,
										93, 97, 96, 98, 96, r_jd.open_item_id); --PJ IPTV
						
						EXCEPTION
							WHEN no_data_found THEN
							BEGIN
								SELECT s.sin_seq_ref_no,
									s.sin_seq_ref_resets,
									s.full_sin_seq,
									s.group_id v_prep_status,
									s.open_item_id
								INTO   v_sin_seq_ref_no,
									v_sin_seq_ref_resets,
									v_full_sin_seq,
									v_group_id,
									v_open_item_id_ssn
								FROM   sin_seq_no s
								WHERE  s.bill_ref_no = r_jd.bill_ref_no
								AND    s.bill_ref_resets = 0
								AND    s.open_item_id = 0;
							
							EXCEPTION
								WHEN OTHERS THEN
								v_full_sin_seq     := NULL;
								v_open_item_id_ssn := NULL;
								
								--dbms_output.put_line('r_jd.bill_ref_no = ' || r_jd.bill_ref_no || '  r_jd.opem_item_id = ' || r_jd.open_item_id);
								--dbms_output.put_line('NDF ORACLE = ' || SQLERRM);
							END;
							
							WHEN OTHERS THEN
							v_full_sin_seq     := NULL;
							v_open_item_id_ssn := NULL;
							
							--dbms_output.put_line('r_jd.bill_ref_no = ' || r_jd.bill_ref_no || '  r_jd.opem_item_id = ' || r_jd.open_item_id);
							--dbms_output.put_line('ORACLE = ' || SQLERRM);
						END;
						END IF;*/
					
						-- INVERTE SINAL ("BACKOUT")
						/*
						IF r_jd.use_code = 4
						THEN
						r_jd.valor := r_jd.valor * -1;
						dbms_output.put_line('BACKOUT -- r_jd.bill_ref_no = ' || r_jd.bill_ref_no ||
											'  r_jd.external_id = ' || r_jd.external_id);
						END IF;
						*/
						
						IF r_jd.valor >= 0
						THEN
							v_db_cre := 'C';
							v_db_deb := 'D';						
						ELSE
							v_db_cre := 'D';
							v_db_deb := 'C';						
						END IF;
       
						v_centro_custo := r_jd.centro_custo;
						
						IF SUBSTR(r_jd.fml_acct_cr,1,1) IN(1,2) THEN
							IF SUBSTR(r_jd.fml_acct_cr,1,1) = SUBSTR(r_jd.fml_acct_db,1,1) then
								v_centro_custo := null;
							END IF;     
						END IF;        
    
						BEGIN
							v_tot_reg_uf := v_tot_reg_uf + 1;
							
							INSERT INTO arborgvt_journals.GVT_Kenan_sap_sped_contabil
							(lote,tipo_registro,empresa_mkt_code,data_documento,tipo_lancamento,moeda,conta_contabil,montante_valor
							,codigo_debito_credito,centro_de_custo_rev_ctr,documento_jnl_ref_no,documento_jnl_ref_no_serv,data_credito,qtde_dias_lcto
							,cod_atribuicao,ajuste_ordem_credito,cod_banco,cod_nsa_banco,full_sin_seq,open_item_id,bill_ref_no
							,bill_ref_resets,external_id,account_no,account_category,bmf_trans_type
							,trans_source,cod_forma_arrec,sequencia_uf,divisao
							,centro_custo,conta_contabil_cp)
							VALUES
								(r_lt.lote,
								r_jd.tipo_registro,
								r_jd.mkt_code,
								r_jd.jnl_subcycle_end_dt,
								r_jd.id_type,
								r_jd.moeda,
								--nvl(r_jd.fml_acct_cr, '41111699'),
								r_jd.fml_acct_cr,
								abs(r_jd.valor),
								v_db_cre,
								r_jd.rev_rcv_cost_ctr,
								r_jd.jnl_ref_no,
								r_jd.jnl_ref_no_serv,
								NULL,
								NULL,
								r_jd.ciclo, -- NULL,
								NULL,
								NULL,
								NULL,
								r_jd.full_sin_seq,      
								r_jd.open_item_id,
								r_jd.bill_ref_no,
								r_jd.bill_ref_resets,
								r_jd.external_id,
								r_jd.account_no,
								r_jd.account_category,
								NULL,
								NULL,
								NULL,
								v_tot_reg_uf,
								r_jd.divisao,
								v_centro_custo,
								--INSOC_066
								r_jd.fml_acct_db);
        
							v_tot_valor_credito := v_tot_valor_credito + abs(r_jd.valor);
							v_tot_registros     := v_tot_registros + 1;
							v_tot_reg_uf        := v_tot_reg_uf + 1;
        
							INSERT INTO arborgvt_journals.GVT_Kenan_sap_sped_contabil
							(lote,tipo_registro,empresa_mkt_code,data_documento,tipo_lancamento,moeda,conta_contabil,montante_valor
							,codigo_debito_credito,centro_de_custo_rev_ctr,documento_jnl_ref_no,documento_jnl_ref_no_serv,data_credito,qtde_dias_lcto
							,cod_atribuicao,ajuste_ordem_credito,cod_banco,cod_nsa_banco,full_sin_seq,open_item_id,bill_ref_no
							,bill_ref_resets,external_id,account_no,account_category,bmf_trans_type
							,trans_source,cod_forma_arrec,sequencia_uf,divisao
							,centro_custo,conta_contabil_cp)
							VALUES
								(r_lt.lote,
								r_jd.tipo_registro,
								r_jd.mkt_code,
								r_jd.jnl_subcycle_end_dt,
								r_jd.id_type,
								r_jd.moeda,
								--nvl(r_jd.fml_acct_db, '11211117'),
								r_jd.fml_acct_db,
								abs(r_jd.valor),
								v_db_deb,
								r_jd.rev_rcv_cost_ctr,
								r_jd.jnl_ref_no,
								r_jd.jnl_ref_no_serv,
								NULL,
								NULL,
								r_jd.ciclo, -- NULL,
								NULL,
								NULL,
								NULL,
								r_jd.full_sin_seq,      
								r_jd.open_item_id,
								r_jd.bill_ref_no,
								r_jd.bill_ref_resets,
								r_jd.external_id,
								r_jd.account_no,
								r_jd.account_category,
								NULL,
								NULL,
								NULL,
								v_tot_reg_uf,
								r_jd.divisao,
								v_centro_custo,
								--INSOC_066
								r_jd.fml_acct_cr);
        
							v_tot_valor_debito := v_tot_valor_debito + abs(r_jd.valor);
							v_tot_registros    := v_tot_registros + 1;
        
						EXCEPTION
						WHEN OTHERS THEN
							dbms_output.put_line('r_jd.bill_ref_no = ' || r_jd.bill_ref_no ||
                                 '  r_jd.opem_item_id = ' || r_jd.open_item_id);
							dbms_output.put_line('INSERT ORACLE = ' || SQLERRM);
          
						END;
      
						v_commit := v_commit + 2;
						IF v_commit > 10000
							THEN
								COMMIT;
								v_commit := 0;
						END IF;
      
					END LOOP;
					--dbms_output.put_line('FIM Detail = ' || to_char(SYSDATE, 'DD/MM/YYYY HH24:MI:SS'));
          commit;
    
					UPDATE arborgvt_journals.GVT_Kenan_sap_sped_lotes
					SET    data_grava_lote = SYSDATE,
							t_nr_registros  = v_tot_registros,
							t_valor_debito  = v_tot_valor_debito,
							t_valor_credito = v_tot_valor_credito
					WHERE  lote = r_lt.lote;
					
					COMMIT;
    
					-- ATUALIZA SEQUENCIAL GUARDAR NA V_TOT_REG_UF  
					UPDATE arborgvt_journals.GVT_Kenan_sap_sped_sequencial
					SET    sequencial = v_tot_reg_uf
					WHERE  interface = 'FII001';
					
					COMMIT;
					
					dbms_output.put_line('VALOR SEQUENCIAL FINAL  = ' || v_tot_reg_uf);
					dbms_output.put_line('Total de Reg Gravados   = ' || v_tot_registros);
					dbms_output.put_line('------------------------------------------------------------------------');
    
				EXCEPTION
				WHEN OTHERS THEN
					dbms_output.put_line('LOTE = ' || r_lt.lote || '  JNL_REF_NO = ' ||
                             r_lt.jnl_ref_no || '  JNL_REF_NO_SERV = ' ||
                             r_lt.jnl_ref_no_serv);
					dbms_output.put_line('ORACLE = ' || SQLERRM);
      
					v_sqlerrm := SQLERRM;
					
					UPDATE arborgvt_journals.GVT_Kenan_sap_sped_lotes l
					SET    data_grava_lote   = SYSDATE,
						t_nr_registros    = 0,
						t_valor_debito    = 0,
						t_valor_credito   = 0,
						l.data_envio_lote = SYSDATE,
						l.nome_arquivo    = 'ORA=' || substr(v_sqlerrm, 1, 240),
						l.complete_flag   = 'E'
					WHERE  lote = r_lt.lote;
				
					COMMIT;
      
				END;
  
			END LOOP;

			dbms_output.put_line('FINAL  = ' || to_char(SYSDATE, 'DD/MM/YYYY HH24:MI:SS'));
			dbms_output.put_line('QTD LOTES TRATADOS  = ' || v_lotes_lidos);

	ELSE 

		dbms_output.put_line('ERRO: SOMA PERCENTUAL DOS PRODUTOS DEFERENTE DE 100%');

	END IF;
  
  
EXCEPTION
  WHEN OTHERS THEN
   dbms_output.put_line('ORACLE = ' || SQLERRM);

END;
/
