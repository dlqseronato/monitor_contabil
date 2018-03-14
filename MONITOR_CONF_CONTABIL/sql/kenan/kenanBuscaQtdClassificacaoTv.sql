   
   								select nvl(COUNT(1), 0)
								from    arborgvt_billing.Gvt_Classificacao_Tv tv
								where   id_classificacao = 3 
									and   tv.type_code = decode(?,3,2,6,3,4,7,?) 
									and   tv.subtype_code = ?
;