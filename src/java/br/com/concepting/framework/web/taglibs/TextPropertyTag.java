package br.com.concepting.framework.web.taglibs;

import java.util.Locale;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Classe que define o componente visual para entrada de texto com ou sem mascaramento.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class TextPropertyTag extends BasePropertyTag{
	private Integer size      = 0;
	private Integer maxlength = 0;
	private Boolean readOnly  = false;

	/**
	 * Indica se o campo é de somente leitura.
	 * 
	 * @return True/False.
	 */
	public Boolean isReadOnly(){
		return readOnly;
	}
	
	/**
	 * Indica se o campo é de somente leitura.
	 * 
	 * @return True/False.
	 */
	public Boolean getReadOnly(){
		return isReadOnly();
	}
	
	/**
	 * Define se o campo permite somente leitura.
	 * 
	 * @param readOnly True/False.
	 */
	public void setReadOnly(Boolean readOnly){
		this.readOnly = readOnly;
	}
	
	/**
	 * Retorna o tamanho do componente definido em caracteres.
	 * 
	 * @return Valor inteiro contendo do componente em caracteres.
	 */
	public Integer getSize(){
		return size;
	}
	
	/**
	 * Define o tamanho do componente definido em caracteres.
	 * 
	 * @param size Valor inteiro contendo do componente em caracteres.
	 */
	public void setSize(Integer size){
		this.size = size;
	}
	
	/**
	 * Retorna o número máximo de caracteres permitidos para digitação.
	 * 
	 * @return Valor inteiro contendo o número máximo de caracteres.
	 */
	public Integer getMaxlength(){
		return maxlength;
	}
	
	/**
	 * Define o número máximo de caracteres permitidos para digitação.
	 * 
	 * @param maxlength Valor inteiro contendo o número máximo de caracteres.
	 */
	public void setMaxlength(Integer maxlength){
		this.maxlength = maxlength;
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderAttributes()
	 */
	protected void renderAttributes() throws Throwable{
		super.renderAttributes();
		
		Integer size = getSize();
		
		if(size > 0){
			print(" size=\"");
			print(size + 1);
			print("\"");
		}
		
		Integer maxLength = getMaxlength();
		
		if(maxLength > 0){
			print(" maxlength=\"");
			print(maxLength);
			print("\"");
		}
		
		Boolean isReadOnly = isReadOnly();
		
		if(isReadOnly)
			print(" readOnly");
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(getComponentType() == null)
	        setComponentType(ComponentType.TEXT_BOX);
	    
		super.initialize();
		
		PropertyInfo propertyInfo = getPropertyInfo();
		String       value        = getFormattedValue();
		Integer      size         = 0;
		
		if(propertyInfo == null){
		    size = value.length();
		    
		    setMaxlength(size);
		    setSize(size);
		}
		else{
			if(actionFormMessageController.hasValidationMessage(getName()))
				value = getRequestInfo().getValue();
			
            Integer maximumLength = getMaxlength();
            
            if(maximumLength == 0)
                maximumLength = propertyInfo.getMaximumLength();

            size = getSize();
		
			if(size == 0)
			    size = maximumLength;
			
			String pattern         = getPattern();
			Locale currentLanguage = systemController.getCurrentLanguage();
			
			if(propertyInfo.isNumber() && pattern.length() > 0)
				pattern = NumberUtil.normalizePattern(pattern, currentLanguage);
			
			if(pattern.length() > 0){
		        maxlength = pattern.length();
    			    
			    if(size == 0)
			        size = pattern.length() + 2;
			    
				String        onKeyPress        = getOnKeyPress();
				StringBuilder onKeyPressContent = new StringBuilder();
				
				onKeyPressContent.append("format");
				
				if(propertyInfo.isNumber())
					onKeyPressContent.append("Number");
				
				onKeyPressContent.append("Property(this, '");
				onKeyPressContent.append(pattern);
				onKeyPressContent.append("', event); ");
				onKeyPressContent.append(onKeyPress);

				setOnKeyPress(onKeyPressContent.toString());
			}
            
            if(maximumLength > 0)
                setMaxlength(maximumLength);
            
            if(size > 0)
                setSize(size);
		}
		
		AlignmentType alignment    = getAlignmentType();
		String        style        = getStyle();
		StringBuilder styleContent = new StringBuilder();
		
		if(alignment != null){
			styleContent.append("text-align: ");
			styleContent.append(alignment);
			styleContent.append(";");
			
			if(style.length() > 0){
				styleContent.append(" ");
				styleContent.append(style);
			}
			
			setStyle(styleContent.toString());
		}
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
		setAlignment("");
		setSize(0);
		setMaxlength(0);
		setReadOnly(false);
	}
}