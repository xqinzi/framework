package br.com.concepting.framework.util;

import java.util.Locale;

import br.com.concepting.framework.resource.SystemResource;
import br.com.concepting.framework.resource.SystemResourceLoader;

/**
 * Classe utilitária com rotinas de manipulação de idiomas.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class LanguageUtil{
    /**
     * Retorna a instância do idioma a partir de sua string identificadora.
     * 
     * @param value String identificadora do idioma.
     * @return Instância contendo as propriedades do idioma.
     */
    public static Locale getLanguageByString(String value){
        Locale language         = null;
        String languageBuffer[] = StringUtil.split((String)value, "_");

        if(languageBuffer.length == 1) 
            language = new Locale(languageBuffer[0]);
        else if(languageBuffer.length == 2) 
            language = new Locale(languageBuffer[0], languageBuffer[1]);
        else if(languageBuffer.length == 3) 
            language = new Locale(languageBuffer[0], languageBuffer[1], languageBuffer[2]);
        
        return language;
    }
    
    /**
     * Retorna a instância do idioma default.
     * 
     * @return Instância contendo as propriedades do idioma.
     */
    public static Locale getDefaultLanguage(){
        try{
            SystemResourceLoader loader   = new SystemResourceLoader();
            SystemResource       resource = loader.getDefault();
            Locale               language = resource.getDefaultLanguage();
            
            if(language == null)
                language = Locale.getDefault();
            
            return language;
        }
        catch(Throwable e){
            return Locale.getDefault();
        }
    }
}
