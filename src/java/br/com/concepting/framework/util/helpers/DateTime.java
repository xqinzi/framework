package br.com.concepting.framework.util.helpers;

import java.util.Date;

/**
 * Classe que define uma data/horário.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class DateTime extends Date{
    private static final long serialVersionUID = -6296515216038853856L;

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     */
    public DateTime(){
        super();
    }

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param milliseconds Valor numérico contendo a representação (em milisegundos) 
     * da data/horário desejado.
     */
    public DateTime(long milliseconds){
        super(milliseconds);
    }
}
