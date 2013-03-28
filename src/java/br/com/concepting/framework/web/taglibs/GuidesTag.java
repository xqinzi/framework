package br.com.concepting.framework.web.taglibs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.web.helpers.RequestInfo;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual para um conjunto de guias de navegação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class GuidesTag extends BaseActionFormElementTag{
	private List<GuideTag> guidesTags     = null;
	private Boolean        showNavigation = true;

    /**
	 * Retorna a instância contendo a lista de guias vinculadas.
	 *
	 * @return Instância contendo a lista de guias.
	 */
	protected List<GuideTag> getGuidesTags(){
    	return guidesTags;
    }

	/**
	 * Define a instância contendo a lista de guias vinculadas.
	 *
	 * @param guidesTags Instância contendo a lista de guias.
	 */
	protected void setGuidesTags(List<GuideTag> guidesTags){
    	this.guidesTags = guidesTags;
    }
	
	/**
	 * Indica se a navegação entre abas deve ser exibida.
	 * 
	 * @return True/False.
	 */
	public Boolean showNavigation(){
     	return showNavigation;
    }

	/**
	 * Define se a navegação entre abas deve ser exibida.
	 * 
	 * @param showNavigation True/False.
	 */
	public void setShowNavigation(Boolean showNavigation){
     	this.showNavigation = showNavigation;
    }
	
	/**
	 * Adiciona uma guia ao componente.
	 * 
	 * @param guideTag Instância contendo as propriedades da guia desejada.
	 */
	protected void addGuideTag(GuideTag guideTag){
		if(guidesTags == null)
			guidesTags = new LinkedList<GuideTag>();
		
		guidesTags.add(guideTag);
	}
	
	/**
]	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#render()
	 */
	protected void render() throws Throwable{
	    if(hasPermission()){
	        if(guidesTags != null && guidesTags.size() > 0){
	            renderOpen();
	            renderBody();
	            renderClose();
	        }
	    }
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
        String width = getWidth();
	    
		print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
		print(TaglibConstants.DEFAULT_GUIDE_LABEL_STYLE_CLASS);
		print("\"");

        if(width.length() > 0){
            print(" style=\"width: ");
            print(width);
            
            if(!width.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        println(">");
        println("<tr>");
        print("<td class=\"");
        print(TaglibConstants.DEFAULT_GUIDES_DEFINITION_STYLE_CLASS);
        println("\">");
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
	    RequestInfo requestInfo  = getRequestInfo();
	    String      currentGuide = requestInfo.getCurrentGuide();
	    
		if(guidesTags.size() == 1){
			Iterator<GuideTag> iterator = guidesTags.iterator();

			currentGuide = iterator.next().getName();
			
			requestInfo.setCurrentGuide(currentGuide);
		}
		else{
			if(currentGuide.length() == 0){
				Iterator<GuideTag> iterator = guidesTags.iterator();
				
				currentGuide = iterator.next().getName();

				requestInfo.setCurrentGuide(currentGuide);
			}
			else{
				Boolean found = false;

				for(GuideTag guideTag : guidesTags){
					if(guideTag.getName().equals(currentGuide)){
						found = true;

						break;
					}
				}

				if(!found){
					Iterator<GuideTag> iterator = guidesTags.iterator();

					currentGuide = iterator.next().getName();
					
					requestInfo.setCurrentGuide(currentGuide);
				}
			}
		}

	    String  name                 = getName();
		Integer guidesSize           = guidesTags.size();
		Double  guideWidthAmount     = 100d;
		String  guideName            = "";
        String  guideLabelStyle      = "";
		String  guideLabelStyleClass = "";
        String  guideTooltip         = "";
		String  guideWidth           = "";
		String  guideOnSelect        = "";

		for(GuideTag guideTag : guidesTags){
		    guideWidth = guideTag.getWidth();
		    
			if(guideWidth.length() > 0){
				guideWidthAmount -= Double.parseDouble(guideWidth);

				guidesSize--;
			}
		}
		
		guideWidthAmount = (guideWidthAmount / guidesSize);

        StringBuilder tagName = new StringBuilder();
        
        tagName.append(name);
        tagName.append(".");
        tagName.append(AttributeConstants.CURRENT_GUIDE_KEY);
        
        HiddenPropertyTag currentGuideTag = new HiddenPropertyTag();
        
        currentGuideTag.setPageContext(pageContext);
        currentGuideTag.setName(tagName.toString());
        currentGuideTag.setValue(currentGuide);
        currentGuideTag.doStartTag();
        currentGuideTag.doEndTag();

        print("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"");
        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
        println("\">");
		println("<tr>");

		for(GuideTag guideTag : guidesTags){
			guideName  = guideTag.getName();
			guideWidth = guideTag.getWidth();

		    print("<td class=\"");
		    print(TaglibConstants.DEFAULT_GUIDE_DEFINITION_STYLE_CLASS);
            print("\" valign=\"");
            print(AlignmentType.BOTTOM);
            print("\" width=\"");

            if(guideWidth.length() > 0)
				print(guideWidth);
			else{
				print(guideWidthAmount);
				print("%");
			}
            
            print("\"");
			
			renderTooltip();
            
            println(">");
            
            print("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"");
            print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
            println("\">");
            println("<tr>");
            print("<td id=\"");
			print(name);
			print(".");
			print(guideName);
            print(".");
            print(AttributeConstants.GUIDE_DEFINITION_KEY);
            print("\" class=\"");

			if(guideName.equals(currentGuide))
				print("currentGuide");
			else
				print("guide");

			print("\"");
			
			if(isEnabled()){
				print(" onClick=\"setCurrentGuide('");
				print(guideName);
				print("', '");
				print(name);
				print("'");

				guideOnSelect = guideTag.getOnSelect();
				
				if(guideOnSelect.length() > 0){
					print(", ");
					print(guideOnSelect);
				}

				print(");\"");
			}
			
			guideTooltip = guideTag.getTooltip();
			
			if(guideTooltip.length() > 0){
				print(" title=\"");
				print(guideTooltip);
				print("\"");
			}
			
			println(">");

            guideLabelStyle      = guideTag.getLabelStyle();
            guideLabelStyleClass = guideTag.getLabelStyleClass();

			if(guideLabelStyle.length() > 0 || guideLabelStyleClass.length() > 0){
			    print("<span");
			    
				if(guideLabelStyleClass.length() > 0){
					print(" class=\"");
					print(guideLabelStyleClass);
					print("\"");
				}

				if(guideLabelStyle.length() > 0){
					print(" style=\"");
					print(guideLabelStyle);
					print("\"");
				}
			}
			
			print(">");
            print("&nbsp;");
            print(guideTag.getLabel());
			print("&nbsp;");
			
            if(guideLabelStyle.length() > 0 || guideLabelStyleClass.length() > 0)
                println("</span>");

			println("</td>");
			println("</tr>");
			println("</table>");

            println("</td>");
		}

		println("</tr>");
		println("</table>");
		
		println("</td>");
		println("</tr>");
		
		println("<tr>");
		print("<td class=\"");
		print(TaglibConstants.DEFAULT_GUIDE_DEFINITION_STYLE_CLASS);
		println("\">");

		Integer                cont                   = 0;
		NextGuideButtonTag     nextGuideButtonTag     = null;
		PreviousGuideButtonTag previousGuideButtonTag = null;
		String                 guideHeight            = "";
		String                 guideContent           = "";
        StringBuilder          content                = null;
		ScriptTag              scriptTag              = null;

		for(GuideTag guideTag : guidesTags){
		    guideName    = guideTag.getName();
            guideHeight  = getHeight();
            guideContent = StringUtil.trim(guideTag.getContent());
		    
			print("<div id=\"");
			print(name);
			print(".");
			print(guideName);
			print(".");
			print(AttributeConstants.GUIDE_CONTENT_KEY);
			print("\"");

			if(!guideName.equals(currentGuide))
				print(" style=\"display: NONE;\"");

			println(">");
			
			print("<table class=\"");
			print(TaglibConstants.DEFAULT_GUIDE_CONTENT_STYLE_CLASS);
			print("\"");

			if(guideHeight.length() > 0){
				print(" height=\"");
				print(guideHeight);
				print("\"");
			}

			println(">");
			println("<tr>");
			println("<td>");
			println(guideContent);
			println("</td>");
			println("</tr>");

			if(showNavigation){
 				if(guidesSize > 1){
 					println("<tr>");
 					print("<td class=\"");
 					print(TaglibConstants.DEFAULT_GUIDES_BUTTONS_STYLE_CLASS);
 					print("\">");
 
 					if(cont > 0 && isEnabled()){
 						if(guidesTags.get(cont - 1).isEnabled()){
 							previousGuideButtonTag = new PreviousGuideButtonTag(guidesTags.get(cont - 1), this);
 							previousGuideButtonTag.doStartTag();
 							previousGuideButtonTag.doEndTag();
 						}
 					}
 
 					if(cont >= 0 && (cont != guidesSize - 1) && isEnabled()){
 						if(guidesTags.get(cont + 1).isEnabled()){
 							nextGuideButtonTag = new NextGuideButtonTag(guidesTags.get(cont + 1), this);
 							nextGuideButtonTag.doStartTag();
 							nextGuideButtonTag.doEndTag();
 						}
 					}
 
 					println("</td>");
 					println("</tr>");
 				}
			}

			println("</table>");
			
			println("</div>");

			if(guideName.equals(currentGuide)){
			    content = new StringBuilder();
			    content.append("setCurrentGuide(\"");
			    content.append(guideName);
			    content.append("\", \"");
			    content.append(name);
			    content.append("\");");
			    content.append(StringUtil.getLineBreak());
			    
			    scriptTag = new ScriptTag();
			    scriptTag.setPageContext(pageContext);
			    scriptTag.setContent(content.toString());
			    scriptTag.doStartTag();
			    scriptTag.doEndTag();
			}

			cont++;
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</td>");
        println("</tr>");
        println("</table>");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    setComponentType(ComponentType.GUIDES);
	    
	    super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
	 */
    protected void clearAttributes(){
    	super.clearAttributes();
 
    	setShowNavigation(true);
    	setGuidesTags(null);
    }
    
    /**
     * Classe que define o componente visual genérico para um botão de navegação entre abas.
     * 
     * @author fvilarinho
     * @since 1.0
     */
    private abstract class GuideButtonTag extends ButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou variáveis internas.
		 * 
		 * @param guideTag
         * @param guidesTag
		 */
    	public GuideButtonTag(GuideTag guideTag, GuidesTag guidesTag){
    		super();
    		
    		setPageContext(guidesTag.getPageContext());
    		setOut(guidesTag.getOut());
    		
    		StringBuilder onClickContent = new StringBuilder();

			onClickContent.append("setCurrentGuide('");
			onClickContent.append(guideTag.getName());
			onClickContent.append("', '");
			onClickContent.append(guidesTag.getName());
			onClickContent.append("'");
			
			String guideOnSelect = guideTag.getOnSelect();

			if(guideOnSelect.length() > 0){
				onClickContent.append(", ");
				onClickContent.append(guideOnSelect);
			}

			onClickContent.append(");");
			
			setOnClick(onClickContent.toString());
			setResourceId(TaglibConstants.DEFAULT_GUIDES_I18N_RESOURCE_ID);
    	}
    }

    /**
     * Classe que define o componente visual genérico para um botão de navegação para a aba 
     * anterior.
     * 
     * @author fvilarinho
     * @since 1.0
     */
    private class PreviousGuideButtonTag extends GuideButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou variáveis internas.
		 * 
         * @param guideTag Instância do componente visual guide (guia).
         * @param guidesTag Instância do componente visual guides (guias).
		 */
        public PreviousGuideButtonTag(GuideTag guideTag, GuidesTag guidesTag){
    		super(guideTag, guidesTag);
    	}
    }

    /**
     * Classe que define o componente visual genérico para um botão de navegação para a 
     * próxima aba.
     * 
     * @author fvilarinho
     * @since 1.0
     */
    private class NextGuideButtonTag extends GuideButtonTag{
		/**
		 * Construtor - Inicializa objetos e/ou variáveis internas.
         * 
         * @param guideTag Instância do componente visual guide (guia).
         * @param guidesTag Instância do componente visual guides (guias).
		 */
    	public NextGuideButtonTag(GuideTag guideTag, GuidesTag guidesTag){
            super(guideTag, guidesTag);
    	}
    }
}