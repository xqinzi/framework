package br.com.concepting.framework.util.constants;

import br.com.concepting.framework.util.types.RrdArchiveType;
import br.com.concepting.framework.util.types.RrdDatasourceType;

/**
 * Classe que define as constantes utilizadas pelas rotinas de manipula��o de arquivos RRD.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class RrdConstants{
    /**
     * Constante que define o tipo de consolida��o default para armazenamento dos dados do RRD.
     */
    public static final RrdArchiveType DEFAULT_RRD_ARCHIVE_TYPE = RrdArchiveType.AVERAGE;
    
    /**
     * Constante que define o tipo de dado default de uma coluna do arquivo RRD.
     */
    public static final RrdDatasourceType DEFAULT_RRD_DATASOURCE_TYPE = RrdDatasourceType.GAUGE;
    
    /**
     * Constante que define o intervalo default dos registros do arquivo RRD.
     */
    public static final Integer DEFAULT_RRD_STEP = 300;
    
    /**
     * Constante que define o intervalo m�ximo default a ser considerado para falha de armazenamento
     * dos dados no arquivo RRD.
     */
    public static final Long DEFAULT_RRD_FAIL_INTERVAL = DEFAULT_RRD_STEP * 3l;
    
    /**
     * Constante que define a porcentagem de falha permitida no armazenamento dos dados no arquivo
     * RRD.
     */
    public static final Double DEFAULT_RRD_FAIL_PERCENTAGE = 0.5d;
    
    /**
     * Constante que define o identificador do comando respons�vel por manipular os arquivos RRD.
     */
    public static final String RRD_COMMAND = "rrdtool";
    
    /**
     * Constante que define o identificador do par�metro para cria��o de um arquivo RRD.
     */
    public static final String RRD_CREATE_ARGUMENT = "create";

    /**
     * Constante que define o identificador do par�metro para dump de um arquivo RRD.
     */
    public static final String RRD_DUMP_ARGUMENT = "dump";
    
    /**
     * Constante que define o identificador do par�metro para leitura de um arquivo RRD.
     */
    public static final String RRD_FETCH_ARGUMENT = "fetch";
    
    /**
     * Constante que define o identificador do par�metro para gera��o de um gr�fico.
     */
    public static final String RRD_GRAPH_ARGUMENT = "graph";

    /**
     * Constante que define o identificador do par�metro para gera��o de um gr�fico.
     */
    public static final String RRD_INFO_ARGUMENT = "info";

    /**
     * Constante que define o identificador do par�metro para gera��o de um gr�fico.
     */
    public static final String RRD_RESTORE_ARGUMENT = "restore";

    /**
     * Constante que define o identificador do par�metro para atualiza��o de registros de um
     * arquivo RRD.
     */
    public static final String RRD_UPDATE_ARGUMENT = "update";
}
