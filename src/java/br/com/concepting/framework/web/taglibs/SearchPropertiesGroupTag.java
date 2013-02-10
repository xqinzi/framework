package br.com.concepting.framework.web.taglibs;

import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.jsp.tagext.BodyContent;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.web.action.types.ActionType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual que agrupo propriedades de pesquisa.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SearchPropertiesGroupTag extends BaseActionFormElementTag{
    private String             action             = "";
	private String             forward            = "";
	private String             forwardOnFail      = "";
	private String             onSubmit           = "";
    private SearchButtonTag    searchButtonTag    = null;
	private Collection<String> searchProperties   = null;
	private String             updateViews        = "";
    private Boolean            validate           = false;
    private String             validateProperties = "";
    
    /**
     * Retorna os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
     * 
     * @return String contendo os identificadores das views.
     */
	public String getUpdateViews(){
        return updateViews;
    }

    /**
     * Define os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
     * 
     * @param updateViews String contendo os identificadores das views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }

    /**
	 * Retorna a instância do componente que define o botão de pesquisa.
	 * 
	 * @return Instância contendo as propriedades do botão de pesquisa.
	 */
	protected SearchButtonTag getSearchButtonTag(){
    	return searchButtonTag;
    }

    /**
     * Define a instância do componente que define o botão de pesquisa.
     * 
     * @param searchButtonTag Instância contendo as propriedades do botão de pesquisa.
     */
	protected void setSearchButtonTag(SearchButtonTag searchButtonTag){
    	this.searchButtonTag = searchButtonTag;
    }

	/**
	 * Retorna a lista contendo os identificadores das propriedades de pesquisa vinculadas 
	 * ao grupo.
	 *
	 * @return Lista contendo os identificadores das propriedades de pesquisa. 
	 */
	protected Collection<String> getSearchProperties(){
    	return searchProperties;
    }

	/**
	 * Define a lista contendo os identificadores das propriedades de pesquisa vinculadas 
	 * ao grupo.
	 *
	 * @param searchProperties Lista contendo os identificadores das propriedades de pesquisa. 
	 */
	protected void setSearchProperties(Collection<String> searchProperties){
    	this.searchProperties = searchProperties;
    }

	/**
	 * Retorna a constante da ação de envio dos dados do formulário.
	 * 
	 * @return String contendo o identificador da ação.
	 */
	public String getAction(){
		return action;
	}
	
    /**
     * Define o identificador da ação de envio dos dados do formulário.
     * 
     * @param action Constante que define a ação.
     */
	protected void setAction(ActionType action){
	    if(action != null)
	        this.action = action.toString().toLowerCase();
	    else
	        this.action = "";
	}

	/**
	 * Define o identificador da ação de envio dos dados do formulário.
	 * 
	 * @param action String contendo o identificador da ação.
	 */
	public void setAction(String action){
        this.action = action;
	}

	/**
	 * Retorna o identificador do redirecionamento em caso de sucesso.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForward(){
		return forward;
	}

	/**
	 * Define o identificador do redirecionamento em caso de sucesso.
	 * 
	 * @param forward String contendo o identificador do redirecionamento.
	 */
	public void setForward(String forward){
		this.forward = forward;
	}

	/**
	 * Retorna o identificador do redirecionamento em caso de erro.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForwardOnFail(){
		return forwardOnFail;
	}

	/**
	 * Define o identificador do redirecionamento em caso de erro.
	 * 
	 * @param forwardOnFail String contendo o identificador do redirecionamento.
	 */
	public void setForwardOnFail(String forwardOnFail){
		this.forwardOnFail = forwardOnFail;
	}

	/**
	 * Retorna as ações a serem executadas no momento da pesquisa.
	 * 
	 * @return String contendo as ações a serem executadas no momento da pesquisa.
	 */
	public String getOnSubmit(){
		return onSubmit;
	}

	/**
	 * Define as ações a serem executadas no momento da pesquisa.
	 * 
	 * @param onSubmit String contendo as ações a serem executadas no momento da pesquisa.
	 */
	public void setOnSubmit(String onSubmit){
		this.onSubmit = onSubmit;
	}

	/**
	 * Indica se o formulário deve ser validado.
	 * 
	 * @return True/False.
	 */
	public Boolean validate(){
		return validate;
	}
	
    /**
     * Indica se o formulário deve ser validado.
     * 
     * @return True/False.
     */
    public Boolean getValidate(){
        return validate();
    }

	/**
	 * Define se o formulário deve ser validado.
	 * 
	 * @param validate True/False.
	 */
	public void setValidate(Boolean validate){
		this.validate = validate;
	}

	/**
	 * Retorna uma string delimitada contendo as propriedades a serem validadas.
	 * 
	 * @return String contendo as propriedades a serem validadas.
	 */
	public String getValidateProperties(){
		return validateProperties;
	}

	/**
	 * Defime uma string delimitada contendo as propriedades a serem validadas.
	 * 
	 * @param validateProperties String contendo as propriedades a serem validadas.
	 */
	public void setValidateProperties(String validateProperties){
		this.validateProperties = validateProperties;
	}

	/**
	 * Adiciona uma propriedade de pesquisa.
	 * 
	 * @param searchProperty String contendo o identificador da propriedade de pesquisa.
	 */
	protected void addSearchProperty(String searchProperty){
		if(searchProperties == null)
			searchProperties = new LinkedList<String>();

		if(!searchProperties.contains(searchProperty))
			searchProperties.add(searchProperty);
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(action == null)
	        action = ActionType.SEARCH.getMethod();
	    
	    String labelStyleClass = getLabelStyleClass();
	    
		if(labelStyleClass.length() == 0){
		    labelStyleClass = TaglibConstants.DEFAULT_SEARCH_PROPERTIES_GROUP_LABEL_STYLE_CLASS;
		    
			setLabelStyleClass(labelStyleClass);
		}
		
		String styleClass = getStyleClass();
		
		if(styleClass.length() == 0){
		    styleClass =TaglibConstants.DEFAULT_SEARCH_PROPERTIES_GROUP_STYLE_CLASS;
		    
		    setStyleClass(styleClass);
		}
		
		String name = getTagId();
		
		setName(name);
		
		super.initialize();
		
		StringBuilder tagName = new StringBuilder();
		
		tagName.append(name);
		tagName.append((int)(Math.random() * 9999));
		
		setName(tagName.toString());
		
        searchButtonTag = new SearchButtonTag();
        searchButtonTag.setPageContext(pageContext);
        searchButtonTag.setAction(action);
        searchButtonTag.setForward(forward);
        searchButtonTag.setForwardOnFail(forwardOnFail);
        searchButtonTag.setUpdateViews(updateViews);
        searchButtonTag.setValidate(validate);
        searchButtonTag.setParent(this);
        searchButtonTag.doStartTag();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderLabelOpen()
	 */
	protected void renderLabelOpen() throws Throwable{
		print("<legend");
		
		String labelStyle = getLabelStyle();

		if(labelStyle.length() > 0){
			print(" style=\"");
			print(labelStyle);
			print(";\"");
		}
		
		String labelStyleClass = getLabelStyleClass();

		if(labelStyleClass.length() > 0){
			print(" class=\"");
			print(labelStyleClass);
			print("\"");
		}

		println(">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderLabelClose()
	 */
	protected void renderLabelClose() throws Throwable{
		println("</legend>");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		print("<fieldset");

        renderTooltip();
        
        String style = getStyle();

        if(style.length() > 0){
            print(" style=\"");
            print(style);
            print(";\"");
        }
        
        String styleClass = getStyleClass();

        if(styleClass.length() > 0){
            print(" class=\"");
            print(styleClass);
            print("\"");
        }

        println(">");
        
        String label = getLabel();

		if(label.length() > 0)
			renderLabel();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</fieldset>");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		print("<table class=\"");
		print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
		println("\">");
		
		println("<tr>");
		println("<td height=\"5\"></td>");
		println("</tr>");

		println("<tr>");
		println("<td>");
		
		BodyContent bodyContent = getBodyContent();

		if(bodyContent != null){
		    String content = StringUtil.trim(getBodyContent().getString());
		    
			println(content);
		}

		println("</td>");
		
		print("<td align=\"");
		print(AlignmentType.RIGHT.toString().toLowerCase());
		print("\" valign=\"");
		print(AlignmentType.TOP.toString().toLowerCase());
		print("\" width=\"1\">");
		
        if(onSubmit.length() > 0)
            searchButtonTag.setOnClick(onSubmit);
        else{
            if(validate){
                StringBuilder validatePropertiesContent = new StringBuilder();
     
                if(validateProperties.length() > 0){
                    String validatePropertiesIds[] = StringUtil.split(validateProperties);
     
                    for(String validatePropertyId : validatePropertiesIds){
                        if(validatePropertiesContent.length() > 0)
                            validatePropertiesContent.append(",");
     
                        validatePropertiesContent.append("search.");
                        validatePropertiesContent.append(validatePropertyId);
                    }
                }
                else{
                    if(searchProperties != null && searchProperties.size() > 0){
                        for(String searchProperty : searchProperties){
                            if(validatePropertiesContent.length() > 0)
                                validatePropertiesContent.append(",");
     
                            validatePropertiesContent.append(searchProperty);
                        }
                    }
                }

                searchButtonTag.setValidateProperties(validatePropertiesContent.toString());
            }
        }

        searchButtonTag.doEndTag();

        println("&nbsp;");
		println("</td>");
		println("</tr>");

        println("<tr>");
        println("<td height=\"5\"></td>");
        println("</tr>");

		println("</table>");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
		GridPropertyTag gridTag = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);

		if(gridTag == null)
			super.render();
	}
 
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
	 */
    protected void clearAttributes(){
	    super.clearAttributes();

	    setAction("");
	    setForward("");
	    setForwardOnFail("");
	    setValidate(false);
	    setValidateProperties("");
	    setOnSubmit("");
	    setSearchProperties(null);
	    setSearchButtonTag(null);
	    setUpdateViews("");
    }
}