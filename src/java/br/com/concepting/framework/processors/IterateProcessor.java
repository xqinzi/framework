package br.com.concepting.framework.processors;

import java.util.Collection;
import java.util.Locale;

import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que implementa um processador l�gico de itera��o de listas e arrays.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class IterateProcessor extends ExpressionProcessor{
    private String  name  = "";
    private String  index = "";
	private Integer start = null;
	private Integer end   = null;
	
	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param content Inst�ncia do conte�do XML.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
	 */
	public IterateProcessor(String domain, Object declaration, XmlNode content, Locale language){
		super(domain, declaration, content, language);
	}

	/**
	 * Retorna o �ndice inicial da itera��o.
	 * 
	 * @return Valor num�rico contendo o �ndice inicial.
	 */
	public Integer getStart(){
    	return start;
    }

    /**
     * Define o �ndice inicial da itera��o.
     * 
     * @param start Valor num�rico contendo o �ndice inicial.
     */
	public void setStart(Integer start){
    	this.start = start;
    }

    /**
     * Retorna o �ndice final da itera��o.
     * 
     * @return Valor num�rico contendo o �ndice final.
     */
	public Integer getEnd(){
    	return end;
    }

    /**
     * Define o �ndice final da itera��o.
     * 
     * @param end Valor num�rico contendo o �ndice final.
     */
	public void setEnd(Integer end){
    	this.end = end;
    }
	
	/**
	 * Retorna o identificador da vari�vel da itera��o.
	 * 
	 * @return String contendo o identificador da vari�vel. 
	 */
	public String getName(){
        return name;
    }

    /**
     * Define o identificador da vari�vel da itera��o.
     * 
     * @param name String contendo o identificador da vari�vel. 
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retorna o identificador da vari�vel contendo o �ndice da itera��o.
     * 
     * @return String contendo o identificador da vari�vel contendo o �ndice. 
     */
    public String getIndex(){
        return index;
    }

    /**
     * Define o identificador da vari�vel contendo o �ndice da itera��o.
     * 
     * @param index String contendo o identificador da vari�vel contendo o �ndice. 
     */
    public void setIndex(String index){
        this.index = index;
    }

    /**
	 * @see br.com.concepting.framework.processors.BaseProcessor#process()
	 */
	public String process() throws Throwable{
		Object value   = evaluate();
		Object array[] = null;
		Object item    = null;

		if(value instanceof Collection)
			array = ((Collection)value).toArray();
		else if(value instanceof Object[])
			array = (Object[])value;

		StringBuilder buffer = new StringBuilder();

		if(array != null){
			setValue("");

			if(start == null)
				start = 0;
			
			if(end == null)
				end = array.length;
			
			String result = "";
			
			for(Integer cont = start ; cont < array.length ; cont++){
				if(cont > end)
					break;
					
				item = array[cont];
				
				if(name.length() > 0)
				    addVariable(name, item);
				
				if(index.length() > 0)
				    addVariable(index, cont);
				
				setDeclaration(item);

				result = StringUtil.trim(super.process());

				if(result.length() > 0){
					if(buffer.length() > 0)
	    				buffer.append(StringUtil.getLineBreak());
					
    				buffer.append(result);
				}
			}
		}

		if(buffer.length() == 0)
		    return ProcessorConstants.REMOVE_TAG;
		    
		return buffer.toString();
	}
}