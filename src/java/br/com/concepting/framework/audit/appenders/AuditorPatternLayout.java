package br.com.concepting.framework.audit.appenders;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.SimpleLayout;
import org.apache.log4j.spi.LoggingEvent;

import br.com.concepting.framework.audit.model.AuditorBusinessComplementModel;
import br.com.concepting.framework.audit.model.AuditorModel;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define a estrutura básica do layout de exibição das mensagens de 
 * auditoria.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class AuditorPatternLayout extends SimpleLayout{
    private AuditorModel model   = null;
	private String       pattern = "";

    /**
	 * Retorna a máscara de formatação das mensagens de auditoria.
	 * 
	 * @return String contendo a máscara de formatação.
	 */
	public String getPattern(){
		return pattern;
	}

	/**
	 * Define a máscara de formatação das mensagens de auditoria.
	 * 
	 * @param pattern String contendo a máscara de formatação.
	 */
	public void setPattern(String pattern){
		this.pattern = pattern;
	}

	/**
	 * Retorna a instância do modelo de dados de auditoria.
	 * 
	 * @return Instância contendo o modelo de dados de auditoria.
	 */
    public <A extends AuditorModel> A getModel(){
		return (A)model;
	}

    /**
     * Define a instância do modelo de dados de auditoria.
     * 
     * param model Instância contendo o modelo de dados de auditoria.
     */
	public <A extends AuditorModel> void setModel(A model){
		this.model = model;
	}

	/**
	 * @see org.apache.log4j.SimpleLayout#format(org.apache.log4j.spi.LoggingEvent)
	 */
	public String format(LoggingEvent event){
		StringBuilder result = new StringBuilder();

		try{
    		String pattern            = PropertyUtil.fillPropertiesInString(event, this.pattern, false);
    		String businessComplement = formatBusinessComplement();
    
            pattern = StringUtil.replaceAll(pattern, "#{businessComplement}", businessComplement);
    		pattern = PropertyUtil.fillPropertiesInString(model, pattern);
    
    		result.append(pattern);
    		result.append(StringUtil.getLineBreak());
		}
		catch(Throwable e){
		}
    
		return result.toString();
	}

	/**
	 * Retorna o complemento da auditoria formatado.
	 * 
	 * @return String contendo o complemento da auditoria formatado.
	 */
	private <C extends AuditorBusinessComplementModel> String formatBusinessComplement(){
        String        currentClass          = "";
        Collection<C> businessComplement    = model.getBusinessComplement();
        Boolean       hasBusinessComplement = false;
		Object        itemValue             = null;
        StringBuilder result                = new StringBuilder();
        Integer       cont                  = 0;
		
		if(businessComplement != null){ 
			for(C item : businessComplement){
    			itemValue = item.getPropertyValue();
    			
    			if(!currentClass.equals(item.getModelClass())){
                    hasBusinessComplement = true;
                    
    				if(currentClass.length() > 0)
    					result.append("), ");
    				
    				result.append(item.getModelClass());
    				result.append(" (");
    				
    				currentClass = item.getModelClass();
    			}

			    if(cont > 0)
			        result.append(", ");
    			
			    if(item.getPropertyId().length() > 0){
        			result.append(item.getPropertyId());
        			result.append("=");
			    }
    			
    			if(itemValue == null)
    				result.append("null");
    			else{
                    if(item.getPropertyId().length() > 0){
        				if(itemValue instanceof Collection)
        					result.append("[");
    
        				if(itemValue instanceof Date || itemValue instanceof String)
    						result.append("\"");
                    }
                    
					result.append(PropertyUtil.format(itemValue));

                    if(item.getPropertyId().length() > 0){
    					if(itemValue instanceof Date || itemValue instanceof String)
    						result.append("\"");
        			    
        				if(itemValue instanceof Collection)
        					result.append("]");
                    }
    			}
    			
    			cont++;
			}
		    
    		if(hasBusinessComplement)
    			result.append(")");
		}
		
		return result.toString();
	}
}