package br.com.concepting.framework.persistence.util;

import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.hibernate.transform.ResultTransformer;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe utilitária responsável por transformar o resultado de uma query em instâncias de modelos de 
 * dados.
 *
 * @author fvilarinho
 * @since 2.0
 */
public class ModelTransformer implements ResultTransformer{
	private Class modelClass = null;
	
	/**
	 * Construtor - Define a classe do modelo de dados a ser utilizada.
	 *
	 * @param modelClass Classe que define o modelo de dados.
	 */
	public ModelTransformer(Class modelClass){
		super();
		
		this.modelClass = modelClass;
	}

	/**
	 * @see org.hibernate.transform.ResultTransformer#transformList(java.util.List)
	 */
	public List transformList(List collection){
		return collection;
	}

	/**
	 * @see org.hibernate.transform.ResultTransformer#transformTuple(java.lang.Object[], java.lang.String[])
	 */
	public Object transformTuple(Object[] tuple, String[] aliases){
		BaseModel model = null;
		Integer   cont  = 0;
		
		try{
    		model = (BaseModel)ConstructorUtils.invokeConstructor(modelClass, null);
    		
    		for(cont = 0 ; cont < tuple.length ; cont++){
    			try{
        			PropertyUtil.setProperty(model, StringUtil.replaceAll(aliases[cont].toLowerCase(), "_", "."), tuple[cont]);
    			}
    			catch(Throwable e){
        			try{
            			PropertyUtil.setProperty(model, StringUtil.replaceAll(aliases[cont], "_", "."), tuple[cont]);
        			}
        			catch(Throwable e1){
        			}
    			}
    		}
		}
		catch(Throwable e){
		}
		
		return model;
	}
}
