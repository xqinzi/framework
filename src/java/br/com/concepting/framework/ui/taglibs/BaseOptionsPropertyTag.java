package br.com.concepting.framework.ui.taglibs;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ScopeType;

/**
 * Classe que define a estrutura básica para os componentes visuais de seleção de opções.
 *
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseOptionsPropertyTag extends BasePropertyTag{
    private String               data                = "";
	private String               dataScope           = "";
	private List                 dataValues          = null;
	private Integer              dataStartIndex      = 0;
	private Integer              dataEndIndex        = 0;
	private List<OptionStateTag> optionStates        = null;
	private ExpressionProcessor  expressionProcessor = null;

    /**
     * Retorna o índice inicial dos dados.
     * 
     * @return Valor numérico contendo o índice inicial.
     */
    protected Integer getDataStartIndex(){
        return dataStartIndex;
    }

    /**
     * Define o índice inicial dos dados.
     * 
     * @param dataStartIndex Valor numérico contendo o índice inicial.
     */
    protected void setDataStartIndex(Integer dataStartIndex){
        this.dataStartIndex = dataStartIndex;
    }

    /**
     * Define o índice final dos dados.
     * 
     * @param dataEndIndex Valor numérico contendo o índice final.
     */
    protected Integer getDataEndIndex(){
        return dataEndIndex;
    }

    /**
     * Define o índice final dos dados.
     * 
     * @param dataEndIndex Valor numérico contendo o índice final.
     */
    protected void setDataEndIndex(Integer dataEndIndex){
        this.dataEndIndex = dataEndIndex;
    }

    /**
     * Retorna o processador de expressões aritméticas/lógicas.
     * 
     * @return Instância contendo o processador de expressões.
     */
    protected ExpressionProcessor getExpressionProcessor(){
        if(expressionProcessor == null){
            Locale currentLanguage = systemController.getCurrentLanguage();
            
            expressionProcessor = new ExpressionProcessor(currentLanguage);
        }
        
        return expressionProcessor;
    }
    
    /**
	 * Define uma lista contendo as instância das condições para tratamento das opções.
	 *
	 * @param optionStates Lista contendo as condições para tratamento.
	 */
	protected void setOptionStates(List<OptionStateTag> optionStates){
    	this.optionStates = optionStates;
    }
	
	/**
	 * Adiciona uma condição para tratamento das opções.
	 *
	 * @param optionState Instância contendo a condição para tratamento.
	 */
	protected void addOptionState(OptionStateTag optionState){
		if(optionStates == null)
			optionStates = new LinkedList<OptionStateTag>();
		
		optionStates.add(optionState);
	}
	
	/**
	 * Retorna uma lista contendo as instância das condições para tratamento das opções.
	 *
	 * @return Lista contendo as condições para tratamento.
	 */
	protected List<OptionStateTag> getOptionStates(){
    	return optionStates;
    }

	/**
	 * Retorna o identificador da lista de opções de seleção.
	 *
	 * @return String contendo o identificador da lista de opções.
	 */
	public String getData(){
    	return data;
    }

	/**
	 * Define o identificador da lista de opções de seleção.
	 *
	 * @param data String contendo o identificador da lista de opções.
	 */
	public void setData(String data){
    	this.data = data;
    }

    /**
     * Retorna o escopo de armazenamento da lista de opções de seleção.
     *
     * @return String que define o escopo de armazenamento.
     */
    public String getDataScope(){
        return dataScope;
    }

    /**
	 * Retorna o escopo de armazenamento da lista de opções de seleção.
	 *
	 * @return Constante que define o escopo de armazenamento.
	 */
	protected ScopeType getDataScopeType(){
	    try{
	        return ScopeType.valueOf(dataScope);
	    }
	    catch(Throwable e){
	        return null;
	    }
    }

    /**
     * Define o escopo de armazenamento da lista de opções de seleção.
     *
     * @param dataScope String que define o escopo de armazenamento.
     */
    public void setDataScope(String dataScope){
        this.dataScope = StringUtil.trim(dataScope).toUpperCase();
    }

	/**
	 * Define o escopo de armazenamento da lista de opções de seleção.
	 *
	 * @param dataScope Constante que define o escopo de armazenamento.
	 */
	protected void setDataScopeType(ScopeType dataScope){
	    if(dataScope != null)
	        this.dataScope = dataScope.toString();
	    else
	        this.dataScope = "";
    }

    /**
	 * Retorna a lista de opções de seleção.
	 *
	 * @return Instância contendo a lista de opções.
	 */
    protected <O> List<O> getDataValues(){
    	return dataValues;
    }

	/**
	 * Define a lista de opções de seleção.
	 *
	 * @param dataValues Instância contendo a lista de opções.
	 */
	protected <O> void setDataValues(List<O> dataValues){
    	this.dataValues = dataValues;
    }
	
	/**
	 * Renderiza os atributos do componentes.
	 * 
	 * @throws Throwable
	 */
	protected void renderDataAttributes() throws Throwable{
        if(getPropertyInfo() != null && data.length() > 0){
    	    String        name    = getName();
            StringBuilder tagName = new StringBuilder();
    
            tagName.append(name);
            tagName.append(".");
            tagName.append(AttributeConstants.DATA_KEY);
    
            HiddenPropertyTag dataTag = new HiddenPropertyTag();
            
            dataTag.setPageContext(pageContext);
    		dataTag.setName(tagName.toString());
    		dataTag.setValue(data);
    		dataTag.doStartTag();
    		dataTag.doEndTag();
    		
            tagName.delete(0, tagName.length());
            tagName.append(name);
            tagName.append(".");
            tagName.append(AttributeConstants.DATA_SCOPE_KEY);
    
            HiddenPropertyTag dataScopeTag = new HiddenPropertyTag();
    
            dataScopeTag.setPageContext(pageContext);
    		dataScopeTag.setName(tagName.toString());
    		dataScopeTag.setValue(getDataScope());
    		dataScopeTag.doStartTag();
    		dataScopeTag.doEndTag();
        }
	}
	
	/**
	 * Renderiza os controladores dos índices dos dados.
	 * 
	 * @throws Throwable
	 */
	protected void renderDataIndexesAttributes() throws Throwable{
        PropertyInfo propertyInfo = getPropertyInfo();
        String       name         = getName();
        List         dataValues   = getDataValues();
            
		if(propertyInfo != null && dataValues != null && dataValues.size() > 0){
            StringBuilder tagName = new StringBuilder();

            tagName.append(name);
            tagName.append(".");
            tagName.append(AttributeConstants.DATA_START_INDEX_KEY);

            HiddenPropertyTag startIndexTag = new HiddenPropertyTag();
    
            startIndexTag.setPageContext(pageContext);
    		startIndexTag.setName(tagName.toString());
    		startIndexTag.setValue(dataStartIndex);
    		startIndexTag.doStartTag();
    		startIndexTag.doEndTag();
    
            tagName.delete(0, tagName.length());
            tagName.append(name);
            tagName.append(".");
            tagName.append(AttributeConstants.DATA_END_INDEX_KEY);

            HiddenPropertyTag endIndexTag = new HiddenPropertyTag();
    
            endIndexTag.setPageContext(pageContext);
    		endIndexTag.setName(tagName.toString());
			endIndexTag.setValue(dataEndIndex);
    		endIndexTag.doStartTag();
    		endIndexTag.doEndTag();
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
		super.initialize();
		
        PropertyInfo propertyInfo = getPropertyInfo();

        if(hasMultipleSelection() == null)
            setMultipleSelection(propertyInfo != null && propertyInfo.isCollection());
		
        if(dataValues == null || dataValues.size() == 0){
    		if(data.length() > 0 && dataScope != null){
    		    ScopeType dataScope = getDataScopeType();
    		    
    		    if(dataScope == null){
    		        dataScope = ScopeType.FORM;
    		        
    		        setDataScopeType(dataScope);
    		    }
    		    
    	        String actionFormName = getActionFormName();

    			if(!data.startsWith(actionFormName)){
        			StringBuilder propertyId = new StringBuilder();
        			
        			if(dataScope == ScopeType.FORM || dataScope == ScopeType.MODEL){
        				propertyId.append(actionFormName);
        				propertyId.append(".");
        				
        				if(dataScope == ScopeType.MODEL){
        				    if(isForSearch())
        				        propertyId.append(AttributeConstants.SEARCH_MODEL_KEY);
        				    else
        				        propertyId.append(AttributeConstants.MODEL_KEY);
        				    
        				    propertyId.append(".");
        				}
        			}
        			
        			propertyId.append(data);
    			
        			data = propertyId.toString();
    			}
    			
    			dataValues = systemController.findAttribute(data, dataScope);
    		}
		}
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
		setData("");
		setDataScope("");
		setDataValues(null);
        setDataIsEmptyMessage("");
		setOptionStates(null);
	}
}