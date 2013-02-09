package br.com.concepting.framework.web.taglibs;

/**
 * Classe que define o componente visual para o botão com a função de fechar uma caixa de diálogo.
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

        if(onClick.length() > 0){
            onClickContent.append(onClick);
            
            if(getParent() instanceof DialogBoxTag)
                onClickContent.append("; ");
        }
        
        if(getParent() instanceof DialogBoxTag){
            onClickContent.append("hideDialogBox('");
            onClickContent.append(getName());
            onClickContent.append("');");
            
            setOnClick(onClickContent.toString());
        }    
        
        super.initialize();
    }
}