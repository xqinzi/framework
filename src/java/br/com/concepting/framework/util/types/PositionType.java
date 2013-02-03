package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes dos tipos de posição de um objeto/componente.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public enum PositionType implements IEnum{
    /**
     * Constante que define o posicionamento no topo.
     */
    TOP("top"),
    
    /**
     * Constante que define o posicionamento no rodapé.
     */
    BOTTOM("bottom"),
    
    /**
     * Constante que define o posicionamento a esquerda.
     */
    LEFT("left"),
    
    /**
     * Constante que define o posicionamento a direita.
     */
    RIGHT("right");
    
    private String key;
    
    /**
     * Construtor - Define o valor da constante.
     * 
     * @param key String contendo o valor desejado.
     */
    private PositionType(String key){
        setKey(key);
    }
    
    /**
     * @see br.com.concepting.framework.util.interfaces.IEnum#getKey()
     */
    public <O> O getKey(){
        return (O)key;
    }
    
    /**
     * @see br.com.concepting.framework.util.interfaces.IEnum#setKey(java.lang.Object)
     */
    public <O> void setKey(O key){
        this.key = (String)key;
    }
    
    /**
     * @see java.lang.Enum#toString()
     */
    public String toString(){
        return key;
    }
    
    /**
     * @see br.com.concepting.framework.util.interfaces.IEnum#toEnum(java.lang.Object)
     */
    public <O> IEnum toEnum(O value) throws IllegalArgumentException{
        return toPositionType((String)value);
    }
    
    /**
     * Converte uma string em uma instância da constante.
     * 
     * @param value
     *            String contendo o valor desejado.
     * @return Instância da constante.
     */
    public static PositionType toPositionType(String value) throws IllegalArgumentException{
        if(value == null)
            throw new IllegalArgumentException();
        
        return valueOf(value.toUpperCase());
    }
}