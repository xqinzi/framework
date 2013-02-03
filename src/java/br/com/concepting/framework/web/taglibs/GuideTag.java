package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.tagext.BodyContent;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ComponentType;

/**
 * Classe que define o componente visual para uma guia.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class GuideTag extends BaseActionFormElementTag{
	private String onSelect = "";
	private String content  = null;

	/**
	 * Retorna o conteúdo da guia.
	 * 
	 * @return String contendo o conteúdo da guia.
	 */
	public String getContent(){
		return content;
	}

	/**
	 * Define o conteúdo da guia.
	 * 
	 * @param content String contendo o conteúdo da guia.
	 */
	public void setContent(String content){
		this.content = content;
	}

	/**
	 * Retorna o evento a ser executado quando a aba for selecionada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnSelect(){
		return onSelect;
	}

	/**
	 * Define o evento a ser executado quando a aba for selecionada.
	 * 
	 * @param onSelect String contendo o evento a ser executado.
	 */
	public void setOnSelect(String onSelect){
		this.onSelect = onSelect;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#initialize()
	 */
	protected void initialize() throws Throwable{
        setType(ComponentType.GUIDE);
	    
        String labelStyleClass = getLabelStyleClass();
        
		if(labelStyleClass.length() == 0){
		    labelStyleClass = TaglibConstants.DEFAULT_GUIDE_LABEL_STYLE_CLASS;
		    
			setLabelStyleClass(labelStyleClass);
		}

		super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
		GuidesTag guidesTag = (GuidesTag)findAncestorWithClass(this, GuidesTag.class);

		if(guidesTag != null){
			if(hasPermission()){
				GuideTag    guideTag     = (GuideTag)this.clone();
				BodyContent bodyContent  = guideTag.getBodyContent();
				String      guideContent = guideTag.getContent();

				if(bodyContent != null && guideContent == null){
				    guideContent = StringUtil.trim(bodyContent.getString());
				    
					guideTag.setContent(guideContent);
				}

				guidesTag.addGuideTag(guideTag);
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();

		setOnSelect("");
		setContent(null);
	}
}