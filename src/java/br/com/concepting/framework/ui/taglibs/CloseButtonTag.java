package br.com.concepting.framework.ui.taglibs;

/**
 * Classe que define o componente visual closeButton (botão fechar).
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class CloseButtonTag extends ButtonTag{
    /**
     * @see br.com.concepting.framework.ui.taglibs.ButtonTag#initialize()
     */
    public void initialize() throws Throwable{
        String        onClick        = getOnClick();
        StringBuilder onClickContent = new StringBuilder();
        
        if(getParent() instanceof DialogBoxTag){
            onClickContent.append("hideDialogBox('");
            
            DialogBoxTag dialogBox = (DialogBoxTag) getParent();
            
            onClickContent.append(dialogBox.getName());
            onClickContent.append("');");
        }    
        
        if(onClick.length() > 0){
            if(getParent() instanceof DialogBoxTag)
                onClickContent.append(" ");
            
            onClickContent.append(onClick);
            
            if(!onClick.endsWith(";"))
                onClickContent.append(";");
        }

        setOnClick(onClickContent.toString());
        
        super.initialize();
    }
}