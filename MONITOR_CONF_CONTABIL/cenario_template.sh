#!/bin/ksh -x
############################################################################
#   Aplicacao: PROATIVO
#   Script   : cenario_template.sh
#   Autor    : Hugo Tomita
#   Descricao: Executa java do cenario_template do proativo.
############################################################################
#
#
#####################################
####  ROTINA CARREGA PARAMETROS  ####
#####################################
#
#
rot_parametros()
{
set +vx
. /app/gvt/scripts/proativo/cenarios/carrega_funcoes.sh
set -vx
export GVT_CONFIG_BD_DIR='/app/gvt/scripts/parametros/CONFIG_DQ'
}
#
#
########################
####  EXECUTA JAVA  ####
########################
#
rot_exec_java()
{
cd /app/gvt/scripts/proativo/cenarios/cenario_template
rot_erro "$?"

/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.45-35.b13.el6.x86_64/jre/bin/java -jar -Xms512m -Xmx4096m cenario_template.jar
rot_erro "$?"
}
#
#
#####################
####  PRINCIPAL  ####
#####################
#
#
rot_parametros
rot_erro "$?"

rot_exec_java
rot_erro "$?"
