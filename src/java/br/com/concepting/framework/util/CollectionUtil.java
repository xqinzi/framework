package br.com.concepting.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.collections.CollectionUtils;
 
/**
 * Classe utilit�ria para manipula��o de cole��es.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class CollectionUtil extends CollectionUtils{
    /**
     * Clona uma cole��o.
     * 
     * @param source Inst�ncia da cole��o.
     * @return Cole��o clonada.
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <O, C extends Collection<O>> C clone(C source) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
        C target = null;

        if(source != null){
            target = (C)ConstructorUtils.invokeConstructor(source.getClass(), null);
            
            for(O item : source)
                target.add((O)MethodUtil.invokeMethod(item, "clone", null));
        }
        
        return target;
    }
}
