package br.com.concepting.framework.util.helpers;

import java.util.Date;

/**
 * Classe que define uma data/hor�rio.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class DateTime extends Date{
    private static final long serialVersionUID = -6296515216038853856L;

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
    public DateTime(){
        super();
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param milliseconds Valor num�rico contendo a representa��o (em milisegundos) 
     * da data/hor�rio desejado.
     */
    public DateTime(long milliseconds){
        super(milliseconds);
    }
}
