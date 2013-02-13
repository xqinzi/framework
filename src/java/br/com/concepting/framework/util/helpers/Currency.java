package br.com.concepting.framework.util.helpers;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Classe que define uma valor monetário.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class Currency extends BigDecimal{
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param value Valor numérico desejado.
     */
    public Currency(BigInteger value){
        super(value);
    }

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param value Valor numérico desejado.
     */
    public Currency(double value){
        super(value);
    }

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param value Valor numérico desejado.
     */
    public Currency(int value){
        super(value);
    }

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param value Valor numérico desejado.
     */
    public Currency(long value){
        super(value);
    }

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param value Valor numérico desejado.
     */
    public Currency(String value){
        super(value);
    }
}