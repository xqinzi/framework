package br.com.concepting.framework.util.constants;

import br.com.concepting.framework.util.types.RrdArchiveType;
import br.com.concepting.framework.util.types.RrdDatasourceType;

/**
 * Classe que define as constantes utilizadas pelas rotinas de manipulação de arquivos RRD.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class RrdConstants{
    /**
     * Constante que define o tipo de consolidação default para armazenamento dos dados do RRD.
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
     * Constante que define o intervalo máximo default a ser considerado para falha de armazenamento
     * dos dados no arquivo RRD.
     */
    public static final Long DEFAULT_RRD_FAIL_INTERVAL = DEFAULT_RRD_STEP * 3l;
    
    /**
     * Constante que define a porcentagem de falha permitida no armazenamento dos dados no arquivo
     * RRD.
     */
    public static final Double DEFAULT_RRD_FAIL_PERCENTAGE = 0.5d;
    
    /**
     * Constante que define o identificador do comando responsável por manipular os arquivos RRD.
     */
    public static final String RRD_COMMAND = "rrdtool";
    
    /**
     * Constante que define o identificador do parâmetro para criação de um arquivo RRD.
     */
    public static final String RRD_CREATE_ARGUMENT = "create";

    /**
     * Constante que define o identificador do parâmetro para dump de um arquivo RRD.
     */
    public static final String RRD_DUMP_ARGUMENT = "dump";
    
    /**
     * Constante que define o identificador do parâmetro para leitura de um arquivo RRD.
     */
    public static final String RRD_FETCH_ARGUMENT = "fetch";
    
    /**
     * Constante que define o identificador do parâmetro para geração de um gráfico.
     */
    public static final String RRD_GRAPH_ARGUMENT = "graph";

    /**
     * Constante que define o identificador do parâmetro para geração de um gráfico.
     */
    public static final String RRD_INFO_ARGUMENT = "info";

    /**
     * Constante que define o identificador do parâmetro para geração de um gráfico.
     */
    public static final String RRD_RESTORE_ARGUMENT = "restore";

    /**
     * Constante que define o identificador do parâmetro para atualização de registros de um
     * arquivo RRD.
     */
    public static final String RRD_UPDATE_ARGUMENT = "update";
}
