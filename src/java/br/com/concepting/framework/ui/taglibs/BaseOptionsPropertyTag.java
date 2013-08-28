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
 * Classe que define a estrutura b�sica para os componentes visuais de sele��o de op��es.
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
     * Retorna o �ndice inicial dos dados.
     * 
     * @return Valor num�rico contendo o �ndice inicial.
     */
    protected Integer getDataStartIndex(){
        return dataStartIndex;
    }

    /**
     * Define o �ndice inicial dos dados.
     * 
     * @param dataStartIndex Valor num�rico contendo o �ndice inicial.
     */
    protected void setDataStartIndex(Integer dataStartIndex){
        this.dataStartIndex = dataStartIndex;
    }

    /**
     * Define o �ndice final dos dados.
     * 
     * @param dataEndIndex Valor num�rico contendo o �ndice final.
     */
    protected Integer getDataEndIndex(){
        return dataEndIndex;
    }

    /**
     * Define o �ndice final dos dados.
     * 
     * @param dataEndIndex Valor num�rico contendo o �ndice final.
     */
    protected void setDataEndIndex(Integer dataEndIndex){
        this.dataEndIndex = dataEndIndex;
    }

    /**
     * Retorna o processador de express�es aritm�ticas/l�gicas.
     * 
     * @return Inst�ncia contendo o processador de express�es.
     */
    protected ExpressionProcessor getExpressionProcessor(){
        if(expressionProcessor == null){
            Locale currentLanguage = systemController.getCurrentLanguage();
            
            expressionProcessor = new ExpressionProcessor(currentLanguage);
        }
        
        return expressionProcessor;
    }
    
    /**
	 * Define uma lista contendo as inst�ncia das condi��es para tratamento das op��es.
	 *
	 * @param optionStates Lista contendo as condi��es para tratamento.
	 */
	protected void setOptionStates(List<OptionStateTag> optionStates){
    	this.optionStates = optionStates;
    }
	
	/**
	 * Adiciona uma condi��o para tratamento das op��es.
	 *
	 * @param optionState Inst�ncia contendo a condi��o para tratamento.
	 */
	protected void addOptionState(OptionStateTag optionState){
		if(optionStates == null)
			optionStates = new LinkedList<OptionStateTag>();
		
		optionStates.add(optionState);
	}
	
	/**
	 * Retorna uma lista contendo as inst�ncia das condi��es para tratamento das op��es.
	 *
	 * @return Lista contendo as condi��es para tratamento.
	 */
	protected List<OptionStateTag> getOptionStates(){
    	return optionStates;
    }

	/**
	 * Retorna o identificador da lista de op��es de sele��o.
	 *
	 * @return String contendo o identificador da lista de op��es.
	 */
	public String getData(){
    	return data;
    }

	/**
	 * Define o identificador da lista de op��es de sele��o.
	 *
	 * @param data String contendo o identificador da lista de op��es.
	 */
	public void setData(String data){
    	this.data = data;
    }

    /**
     * Retorna o escopo de armazenamento da lista de op��es de sele��o.
     *
     * @return String que define o escopo de armazenamento.
     */
    public String getDataScope(){
        return dataScope;
    }

    /**
	 * Retorna o escopo de armazenamento da lista de op��es de sele��o.
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
     * Define o escopo de armazenamento da lista de op��es de sele��o.
     *
     * @param dataScope String que define o escopo de armazenamento.
     */
    public void setDataScope(String dataScope){
        this.dataScope = StringUtil.trim(dataScope).toUpperCase();
    }

	/**
	 * Define o escopo de armazenamento da lista de op��es de sele��o.
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
	 * Retorna a lista de op��es de sele��o.
	 *
	 * @return Inst�ncia contendo a lista de op��es.
	 */
    protected <O> List<O> getDataValues(){
    	return dataValues;
    }

	/**
	 * Define a lista de op��es de sele��o.
	 *
	 * @param dataValues Inst�ncia contendo a lista de op��es.
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
	 * Renderiza os controladores dos �ndices dos dados.
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