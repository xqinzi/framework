package br.com.concepting.framework.web.taglibs;

import java.util.List;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.FormModel;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.util.ImageUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.SortOrderType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ScopeType;
import br.com.concepting.framework.web.types.VisibilityType;

/**
 * Classe que define o componente visual para itens de menus.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class MenuBarTag extends BaseOptionsPropertyTag{
	private Boolean fixed                         = false;     
	private String  menuItemStyleClass            = "";
	private String  menuItemSelectedStyleClass    = "";
	private String  menuBarItemStyleClass         = "";
	private String  menuBarItemSelectedStyleClass = "";
    private String  menuBoxItemStyleClass         = "";
    private String  menuBoxItemSelectedStyleClass = "";
    
    /**
     * Retorna o estilo CSS para os itens da barra de menu.
     * 
     * @return String contendo o estilo CSS.
     */
	public String getMenuBarItemStyleClass(){
        return menuBarItemStyleClass;
    }

    /**
     * Define o estilo CSS para os itens da barra de menu.
     * 
     * @param menuBarItemStyleClass String contendo o estilo CSS.
     */
    public void setMenuBarItemStyleClass(String menuBarItemStyleClass){
        this.menuBarItemStyleClass = menuBarItemStyleClass;
    }

    /**
     * Retorna o estilo CSS para o item selecionado da barra de menu.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getMenuBarItemSelectedStyleClass(){
        return menuBarItemSelectedStyleClass;
    }

    /**
     * Define o estilo CSS para o item selecionado da barra de menu.
     * 
     * @param menuBarItemSelectedStyleClass String contendo o estilo CSS.
     */
    public void setMenuBarItemSelectedStyleClass(String menuBarItemSelectedStyleClass){
        this.menuBarItemSelectedStyleClass = menuBarItemSelectedStyleClass;
    }

    /**
     * Retorna o estilo CSS para o itens da caixa de itens de menu.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getMenuBoxItemStyleClass(){
        return menuBoxItemStyleClass;
    }

    /**
     * Define o estilo CSS para o itens da caixa de itens de menu.
     * 
     * @param menuBoxItemStyleClass String contendo o estilo CSS.
     */
    public void setMenuBoxItemStyleClass(String menuBoxItemStyleClass){
        this.menuBoxItemStyleClass = menuBoxItemStyleClass;
    }

    /**
     * Retorna o estilo CSS para o item selecionado da caixa de itens de menu.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getMenuBoxItemSelectedStyleClass(){
        return menuBoxItemSelectedStyleClass;
    }

    /**
     * Define o estilo CSS para o item selecionado da caixa de itens de menu.
     * 
     * @param menuBoxItemSelectedStyleClass String contendo o estilo CSS.
     */
    public void setMenuBoxItemSelectedStyleClass(String menuBoxItemSelectedStyleClass){
        this.menuBoxItemSelectedStyleClass = menuBoxItemSelectedStyleClass;
    }

    /**
	 * Indica se a barra de menus deve ter uma posição fixa.
	 * 
	 * @return True/False.
	 */
	public Boolean isFixed(){
    	return fixed;
    }

	/**
	 * Indica se a barra de menus deve ter uma posição fixa.
	 * 
	 * @return True/False.
	 */
	public Boolean getFixed(){
    	return isFixed();
    }

	/**
	 * Define se a barra de menus deve ter uma posição fixa.
	 * 
	 * @param fixed True/False.
	 */
	public void setFixed(Boolean fixed){
    	this.fixed = fixed;
    }
	
    /**
	 * Retorna o estilo CSS para os ítens de menu.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getMenuItemStyleClass(){
		return menuItemStyleClass;
	}

	/**
	 * Define o estilo CSS para os ítens de menu.
	 * 
	 * @param menuItemStyleClass String contendo o estilo CSS.
	 */
	public void setMenuItemStyleClass(String menuItemStyleClass){
		this.menuItemStyleClass = menuItemStyleClass;
	}

	/**
	 * Retorna o estilo CSS para os ítens de menu quando os mesmos estiverem selecionados.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getMenuItemSelectedStyleClass(){
		return menuItemSelectedStyleClass;
	}

	/**
	 * Define o estilo CSS para os ítens de menu quando os mesmos estiverem selecionados.
	 * 
	 * @param menuItemSelectedStyleClass String contendo o estilo CSS.
	 */
	public void setMenuItemSelectedStyleClass(String menuItemSelectedStyleClass){
		this.menuItemSelectedStyleClass = menuItemSelectedStyleClass;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(menuItemStyleClass.length() == 0)
	        menuItemStyleClass = TaglibConstants.DEFAULT_MENU_ITEM_STYLE_CLASS;

	    if(menuItemSelectedStyleClass.length() == 0)
            menuItemSelectedStyleClass = TaglibConstants.DEFAULT_MENU_ITEM_SELECTED_STYLE_CLASS;
	    
	    if(menuBarItemStyleClass.length() == 0)
	        menuBarItemStyleClass = menuItemStyleClass;

        if(menuBarItemSelectedStyleClass.length() == 0)
            menuBarItemSelectedStyleClass = menuItemSelectedStyleClass;

        if(menuBoxItemStyleClass.length() == 0)
            menuBoxItemStyleClass = menuItemStyleClass;

        if(menuBoxItemSelectedStyleClass.length() == 0)
            menuBoxItemSelectedStyleClass = menuItemSelectedStyleClass;

        setComponentType(ComponentType.MENU_BAR);
	    
		super.initialize();
		
        String actionForm = getActionForm();
		
		if(getData().length() == 0 && actionForm.length() > 0){
			LoginSessionModel loginSession = securityController.getLoginSession();
			SystemModuleModel systemModule = (loginSession != null ? loginSession.getSystemModule() : null);
			FormModel         form         = (systemModule != null ? systemModule.getForm(actionForm) : null);
			List<ObjectModel> objects      = null;
			
			if(form != null)
				objects = form.getObjects();

			setDataValues(objects);
		}
		
		if(getName().length() == 0){
			StringBuilder tagName = new StringBuilder();
			
			tagName.append(getTagId());
			tagName.append((int)(Math.random() * 9999));

			setName(tagName.toString());
		}
	}
	
	/**
	 * Renderiza os ítems de menu do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderMenuItems() throws Throwable{
		List<ObjectModel> dataValues = getDataValues();
		
		if(dataValues != null && dataValues.size() > 0)
		    renderMenuItems(dataValues, null);
	}
	
	/**
	 * Renderiza um item de menu.
	 * 
	 * @param menuItem Instância contendo as propriedades do item de menu.
	 * @throws Throwable
	 */
	private void renderMenuItemIcon(ObjectModel menuItem) throws Throwable{
        String        menuItemIconUrl    = menuItem.getIconUrl();
        byte[]        menuItemIconData   = menuItem.getIconData();
        String        menuItemIconWidth  = menuItem.getIconWidth();
        String        menuItemIconHeight = menuItem.getIconHeight();
        StringBuilder menuItemIconId     = new StringBuilder();
        
        if(menuItemIconUrl.length() > 0 || menuItemIconData != null){
            print("<img src=\"");
            print(systemController.getContextPath());
            print("/");
            
            if(menuItemIconData != null){
                menuItemIconId.append(TaglibConstants.DEFAULT_MENU_ITEM_ICON_STYLE_CLASS);
                menuItemIconId.append(menuItem.toString().hashCode());
                
                systemController.setAttribute(menuItemIconId.toString(), menuItemIconData, ScopeType.SESSION);
                
                print("contentLoaderServlet?contentData=");
                print(menuItemIconId);
                print("&contentType=");
                println(ImageUtil.getImageFormat(menuItemIconData));
            }
            else{
                String skin = systemController.getCurrentSkin();
                        
                if(menuItemIconUrl.startsWith("/")){
                    print("skins/");
                    print(skin);
                }
                
                print(menuItemIconUrl);
            }
                
            print("\"");

            if(menuItemIconWidth.length() > 0){
                print(" width=\"");
                print(menuItemIconWidth);
                print("\"");
            }

            if(menuItemIconHeight.length() > 0){
                print(" height=\"");
                print(menuItemIconHeight);
                print("\"");
            }

            println(">");
        }
	}

	/**
	 * Renderiza os ítens de menu.
	 * 
	 * @param menuItems Lista contendo as instâncias das opções de menu.
	 * @param parentMenu Instância contendo as propriedades do menu pai.
     * 
     * @throws Throwable
	 */
	private void renderMenuItems(List<ObjectModel> menuItems, ObjectModel parentMenu) throws Throwable{
	    if(menuItems == null)
	        return;
	    
		print("<table class=\"");
		
        if(parentMenu != null)
            print(TaglibConstants.DEFAULT_MENU_BOX_CONTENT_STYLE_CLASS);
        else
            print(TaglibConstants.DEFAULT_MENU_BAR_CONTENT_STYLE_CLASS);
        
		println("\">");
		println("<tr>");
		
		LoginSessionModel loginSession  = securityController.getLoginSession();
		UserModel         user          = null;
		Boolean           hasPermission = true;
		Boolean           superUser     = false;

		if(menuItems != null && menuItems.size() > 0)
			ModelUtil.sort(menuItems, "sequence", SortOrderType.ASCEND);
		
		ObjectModel   parentMenuItem       = null;
        String        menuItemName         = ""; 
        String        menuItemLabel        = "";
        String        menuItemAction       = "";
        String        menuItemActionTarget = "";
        Boolean       hasSubmenuItems      = false;
		ComponentType menuItemType         = null;

		for(ObjectModel menuItem : menuItems){
		    menuItemName   = menuItem.getName();
		    parentMenuItem = menuItem.getParent();
		    
			if((parentMenu == null && parentMenuItem == null) || (parentMenu != null && parentMenu.equals(parentMenuItem))){
			    if(getData().length() == 0){
    			    hasPermission = (loginSession != null && loginSession.getId() != null && loginSession.getId() > 0);
    			    
    			    if(hasPermission){
                        user          = loginSession.getUser();
                        hasPermission = (user != null && user.getId() != null && user.getId() > 0); 
                        
                        if(hasPermission){
                            superUser = user.isSuperUser();
                            
                            if(superUser)
                                hasPermission = true;
                            else
                                hasPermission = user.hasPermission(menuItem);
                        }
    			    }
			    }

				if(hasPermission){
                    hasSubmenuItems = menuItem.hasChildNodes();
				    menuItemType    = menuItem.getType();
				    
     				if(menuItemType == ComponentType.MENU_ITEM_SEPARATOR){
     					if(parentMenu != null){
 					        print("<td align=\"");
 					        print(AlignmentType.CENTER);
 					        println("\" colspan=\"3\">");
     						print("<div class=\"");
     						print(TaglibConstants.DEFAULT_MENU_BOX_SEPARATOR_STYLE_CLASS);
     						println("\"></div>");
     						println("</td>");
     					}
     					else{
                            print("<td align=\"");
                            print(AlignmentType.CENTER);
                            println("\">");
                            print("<div class=\"");
                            print(TaglibConstants.DEFAULT_MENU_BAR_SEPARATOR_STYLE_CLASS);
                            println("\"></div>");
     						println("</td>");
     					}
     				}
     				else{
     				    if(parentMenuItem != null){
                            print("<td id=\"");
                            print(menuItemName);
                            print(".");
                            print(AttributeConstants.MENU_ITEM_ICON_KEY);
                            print("\" class=\"");
                            print(menuBoxItemStyleClass);
                            print("\" align=\"");
                            print(AlignmentType.CENTER);
                            println("\" width=\"1\">");
                            
                            renderMenuItemIcon(menuItem);
                            
                            println("</td>");
     				    }

                        print("<td id=\"");
     					print(menuItemName);
     					print(".");
     					print(AttributeConstants.MENU_ITEM_KEY);
     					print("\" class=\"");
     					
     					if(parentMenuItem != null)
     					    print(menuBoxItemStyleClass);
     					else
     					    print(menuBarItemStyleClass);
     					
     					print("\"");
     					
     					menuItemAction = StringUtil.trim(menuItem.getAction());
     
     					if(menuItemAction.length() > 0){
     						print(" onClick=\"");
     						
     						if(menuItemAction.toLowerCase().startsWith("javascript:")) 
     						    print(menuItemAction);
     						else{
         						menuItemActionTarget = StringUtil.trim(menuItem.getActionTarget());
         						
         						if(menuItemActionTarget.length() > 0){
         							print("window.open('");

         							if(!menuItemAction.startsWith("http://"))
                                        print(systemController.getContextPath());
         							
         							print(menuItemAction);
         							print("', '");
         							print(menuItemActionTarget);
         							print("');");
         						}
         						else{
         						    print("showLoadingBox(document.forms[0]); location.href = '");
    
                                    if(!menuItemAction.startsWith("http://"))
                                        print(systemController.getContextPath());
                                    
                                    print(menuItemAction);
                                    print("';");
         						}
     						}

     						print("\"");
     					}
     
     					print(" onMouseover=\"selectMenuItem(this, '");
     					
     					if(parentMenuItem != null)
     					    print(menuBoxItemSelectedStyleClass);
     					else
     					    print(menuBarItemSelectedStyleClass);
     					
     					print("');\" onMouseout=\"unselectMenuItem(this, '");

                        if(parentMenuItem != null)
                            print(menuBoxItemStyleClass);
                        else
                            print(menuBarItemStyleClass);

     					print("');\">");
     					
                        if(parentMenuItem == null)
     					    renderMenuItemIcon(menuItem);
     					
     					menuItemLabel = StringUtil.trim(menuItem.getLabel());

     					if(menuItemLabel.length() > 0)
     						print(menuItemLabel);

                        println("</td>");
     
 						print("<td id=\"");
 						print(menuItemName);
 						print(".");
 						print(AttributeConstants.MENU_ITEM_ARROW_KEY);
 						print("\" class=\"");

                        if(parentMenuItem != null)
                            print(menuBoxItemStyleClass);
                        else
                            print(menuBarItemStyleClass);

                        print("\" align=\"");
                        print(AlignmentType.RIGHT);
                        print("\" width=\"1\">");

 						if(hasSubmenuItems && parentMenu != null)
     						print("&raquo;");

 						println("</td>");
     				}
     
     				if(parentMenu != null){
     					println("</tr>");
     					println("<tr>");
     				}
				}
			}
		}

		println("</tr>");
		println("</table>");
		
		List<ObjectModel> submenusItems    = null;
		Boolean           hasSubmenusItems = false;

		for(ObjectModel menuItem : menuItems){
		    menuItemName   = menuItem.getName();
		    parentMenuItem = menuItem.getParent();
		    
			if((parentMenu == null && parentMenuItem == null) || (parentMenu != null && parentMenu.equals(parentMenuItem))){
			    hasSubmenusItems = menuItem.hasChildNodes();
			    
				if(hasSubmenusItems){
					submenusItems = menuItem.getChildNodes();
					
					print("<div id=\"");
					print(menuItemName);
					print(".");
					print(AttributeConstants.MENU_BOX_KEY);
					print("\" class=\"");
					print(TaglibConstants.DEFAULT_MENU_BOX_STYLE_CLASS);
					print("\" style=\"visibility: ");
					print(VisibilityType.HIDDEN);
					println(";\">");

					renderMenuItems(submenusItems, menuItem);

					println("</div>");
				}
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		print("<div id=\"");
		print(getName());
		print(".");
		print(AttributeConstants.MENU_BAR_KEY);
		print("\" class=\"");
		print(TaglibConstants.DEFAULT_MENU_BAR_STYLE_CLASS);
		println("\">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		renderMenuItems();
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</div>");
		
		if(fixed){
		    StringBuilder content = new StringBuilder();
		    
            content.append("renderFixedMenu(\"");
            content.append(getName());
            content.append("\");");

            ScriptTag scriptTag = new ScriptTag();

            scriptTag.setPageContext(pageContext);
            scriptTag.setContent(content.toString());
            scriptTag.doStartTag();
            scriptTag.doEndTag();
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
	 */
    protected void clearAttributes(){
    	super.clearAttributes();
	     
	    setMenuItemStyleClass("");
	    setMenuItemSelectedStyleClass("");
	    setFixed(false);
    }
}