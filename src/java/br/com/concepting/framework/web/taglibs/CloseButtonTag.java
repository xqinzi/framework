package br.com.concepting.framework.web.taglibs;

/**
 * Classe que define o componente visual para o bot�o com a fun��o de fechar uma caixa de di�logo.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class CloseButtonTag extends ButtonTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.ButtonTag#initialize()
     */
    public void initialize() throws Throwable{
        String        onClick        = getOnClick();
        StringBuilder onClickContent = new StringBuilder();
        
        if(getParent() instanceof DialogBoxTag){
            onClickContent.append("hideDialogBox('");
            onClickContent.append(getName());
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