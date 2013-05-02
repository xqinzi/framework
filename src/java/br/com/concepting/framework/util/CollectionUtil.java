package br.com.concepting.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.collections.CollectionUtils;

public class CollectionUtil extends CollectionUtils{
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
