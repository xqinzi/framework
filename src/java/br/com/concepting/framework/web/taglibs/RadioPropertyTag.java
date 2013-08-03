package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.tagext.Tag;

import br.com.concepting.framework.util.types.ComponentType;

/**
 * Classe que define o componente visual para uma op��o de sele��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class RadioPropertyTag extends BaseOptionPropertyTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseOptionPropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        setComponentType(ComponentType.RADIO);
        
        Tag parent = findAncestorWithClass(this, OptionsPropertyTag.class);

        if(parent == null)
            parent = findAncestorWithClass(this, GridPropertyTag.class);

        if(parent == null){
            String        name           = getName();
            String        onClick        = getOnClick();
            StringBuilder onClickContent = new StringBuilder();

            onClickContent.append("if(this.checked) { setObject('");
            onClickContent.append(name);
            onClickContent.append("', this.value); } else { setObject('");
            onClickContent.append(name);
            onClickContent.append("', ''); }");
            onClickContent.append(onClick);
        
            setOnClick(onClickContent.toString());
        }

        super.initialize();
    }
}