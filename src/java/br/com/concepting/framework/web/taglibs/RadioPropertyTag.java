package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.tagext.Tag;

import br.com.concepting.framework.web.types.ComponentType;

/**
 * Classe que define o componente visual para uma opção de seleção.
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

            onClickContent.append("if(this.checked) { document.getElementById('");
            onClickContent.append(name);
            onClickContent.append("').value = this.value; } else { ");
            onClickContent.append("document.getElementById('");
            onClickContent.append(name);
            onClickContent.append("').value = ''; }");
            onClickContent.append(onClick);
        
            setOnClick(onClickContent.toString());
        }

        super.initialize();
    }
}