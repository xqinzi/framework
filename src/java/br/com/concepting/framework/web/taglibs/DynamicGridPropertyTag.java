package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.web.taglibs.helpers.DynamicGridColumn;
import br.com.concepting.framework.web.types.ScopeType;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Classe que define o componente visual para uma tabela de dados dinâmica (colunas e botões).
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class DynamicGridPropertyTag extends GridPropertyTag{
    private String columnsData      = "";
    private String columnsDataScope = ScopeType.FORM.toString();
    
    /**
     * Retorna o identificador da lista de colunas.
     *
     * @return String contendo o identificador da lista de colunas.
     */
    public String getColumnsData(){
        return columnsData;
    }
    
    /**
     * Define o identificador da lista de colunas.
     *
     * @param columnsData String contendo o identificador da lista de colunas.
     */
    public void setColumnsData(String columnsData){
        this.columnsData = columnsData;
    }
    
    /**
     * Retorna o escopo de armazenamento da lista de colunas.
     *
     * @return String contendo o escopo de armazenamento.
     */
    public String getColumnsDataScope(){
        return columnsDataScope;
    }

    /**
     * Define o escopo de armazenamento da lista de colunas.
     *
     * @param columnsDataScope String contendo o escopo de armazenamento.
     */
    public void setColumnsDataScope(String columnsDataScope){
        this.columnsDataScope = columnsDataScope;
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.GridPropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
		super.initialize();
		
        String actionForm = getActionForm();

        if(columnsData.length() > 0 && actionForm.length() > 0){
		    ScopeType     columnsDataScopeType = ScopeType.toScopeType(columnsDataScope);
            StringBuilder propertyId           = new StringBuilder();
            
            if(columnsDataScopeType == ScopeType.FORM || columnsDataScopeType == ScopeType.MODEL){
                propertyId.append(actionForm);
                propertyId.append(".");
                
                if(columnsDataScopeType == ScopeType.MODEL){
                    if(isForSearch())
                        propertyId.append("searchModel");
                    else
                        propertyId.append("model");
                    
                    propertyId.append(".");
                }
            }
            
            propertyId.append(columnsData);
            
            columnsData = propertyId.toString();
            
		    List<DynamicGridColumn> columnsDataValues = systemController.findAttribute(columnsData, columnsDataScopeType);
		    
		    if(columnsDataValues != null && columnsDataValues.size() > 0){
		        GridColumnTag gridColumn = null;
		        Field         fields[]   = DynamicGridColumn.class.getDeclaredFields();
		        Object        value      = null;
		        
		        for(DynamicGridColumn columnsDataValue : columnsDataValues){
		            gridColumn = new GridColumnTag();
		            
		            for(Field field : fields){
		                try{
		                   value = PropertyUtil.getProperty(columnsDataValue, field.getName()); 
		                   
		                   PropertyUtil.setProperty(gridColumn, field.getName(), value);
		                }
		                catch(Throwable e){
		                }
		            }

		            gridColumn.setPageContext(pageContext);
		            gridColumn.doStartTag();
		            gridColumn.doEndTag();
		        }
		    }
		}
	}
    
    /**
     * @see br.com.concepting.framework.web.taglibs.GridPropertyTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setColumnsData("");
        setColumnsDataScope(ScopeType.FORM.toString());
    }
}
