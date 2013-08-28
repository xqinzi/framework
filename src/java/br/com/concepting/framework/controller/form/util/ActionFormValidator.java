package br.com.concepting.framework.controller.form.util;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.MethodUtils;

import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.form.ActionFormMessageController;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.helpers.RequestInfo;

/** 
 * Classe que implementa a validação para as propriedades do modelo de dados vinculado ao formulário.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class ActionFormValidator{
    protected SystemController            systemController            = null;
    protected ActionFormMessageController actionFormMessageController = null;
    protected SecurityController          securityController          = null;
    private   BaseActionForm              actionForm                  = null;

	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 * 
	 * @param actionForm Instância do formulário desejado.
	 * @param systemController Instância do controlador do fluxo de dados WEB.
	 */
	public ActionFormValidator(BaseActionForm actionForm, SystemController systemController){
		super();

		this.systemController            = systemController;
		this.actionFormMessageController = systemController.getActionFormMessageController();
		this.securityController          = systemController.getSecurityController();
        this.actionForm                  = actionForm;
	}

	/**
	 * Efetua a validação do formulário.
	 */
	public void validate(){
		Collection<RequestInfo> requestInfos = systemController.getRequestInfos();

		for(RequestInfo requestInfo : requestInfos){
		    if(requestInfo.isEditable())
		        validateEditable(requestInfo);
		    else
		        validate(requestInfo);
		}
	}

    /**
     * Efetua a validação de uma propriedade de um modelo de dados.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     */
	private void validate(RequestInfo requestInfo){
		String  name     = requestInfo.getName();
		Boolean validate = false;
		
		if(name.startsWith("search.") && actionForm.validateSearchModel())
		    validate = true;
		else if(!name.startsWith("search.") && actionForm.validateModel())
		    validate = true;
		
		if(validate){
			BaseModel model = null;
			
			if(name.startsWith("search."))
			    model = actionForm.getSearchModel();
			else
			    model = actionForm.getModel();
			
			if(model == null)
				return;

			Class     modelClass = model.getClass();
			ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass);	

			if(modelInfo == null)
				return;
			
			PropertyInfo       propertyInfo       = null;
			String             propertyId         = StringUtil.replaceAll(name, "search.", "");
			Collection<String> validateProperties = Arrays.asList(StringUtil.split(actionForm.getValidateProperties()));

			if(validateProperties.size() == 0 || validateProperties.contains(name)){
				propertyInfo = modelInfo.getPropertyInfo(propertyId);

				if(propertyInfo != null)
					validate(requestInfo, propertyInfo);
			}
		}
	}

	/**
	 * Valida uma coluna editável em um grid.
	 * 
	 * @param requestInfo Instância contendo as propriedades do parâmetro da requisição.
	 */
	private void validateEditable(RequestInfo requestInfo){
		String    name      = requestInfo.getName();
		BaseModel model     = (name.contains("search.") ? actionForm.getSearchModel() : actionForm.getModel());
		ModelInfo modelInfo = (model != null ? ModelUtil.getModelInfo(model.getClass()) : null);

		if(modelInfo == null)
			return;

		String        names[]    = StringUtil.split(name, "_");
		StringBuilder nameBuffer = new StringBuilder();

		nameBuffer.append(names[0]);
		nameBuffer.append(".");
		nameBuffer.append(names[1]);

		PropertyInfo propertyInfo = modelInfo.getPropertyInfo(nameBuffer.toString());

		if(propertyInfo == null){
			Integer pos = names[0].lastIndexOf(".");

			if(pos >= 0)
				names[0] = names[0].substring(0, pos);

			nameBuffer.delete(0, nameBuffer.length());
			nameBuffer.append(names[0]);
			nameBuffer.append(".");
			nameBuffer.append(names[1]);

			propertyInfo = modelInfo.getPropertyInfo(nameBuffer.toString());
		}

		if(propertyInfo != null)
			validate(requestInfo, propertyInfo);
	}

	/**
	 * Efetua a validação de uma propriedade de um modelo de dados.
	 * 
	 * @param requestInfo Instância contendo as propriedades da requisição.
	 * @param propertyInfo Instância contendo os dados da propriedade.
	 */
	private void validate(RequestInfo requestInfo, PropertyInfo propertyInfo){
		String value = requestInfo.getValue();

		if(value != null){
			ValidationType validations[] = propertyInfo.getValidations();

			for(ValidationType validation : validations){
				switch(validation){
    				case REQUIRED: {
    					validateRequired(requestInfo, propertyInfo);
    
    					break;
    				}
    				case DATE_TIME: {
    					validateDateTime(requestInfo, propertyInfo);
    
    					break;
    				}
    				case NUMBER: {
    					validateNumber(requestInfo, propertyInfo);
    
    					break;
    				}
    				case WORD_COUNT: {
    					validateWordCount(requestInfo, propertyInfo);
    
    					break;
    				}
    				case MINIMUM_LENGTH: {
    					validateMinimumLength(requestInfo, propertyInfo);
    
    					break;
    				}
    				case MAXIMUM_LENGTH: {
    					validateMaximumLength(requestInfo, propertyInfo);
    
    					break;
    				}
    				case COMPARE: {
    					validateCompare(requestInfo, propertyInfo);
    
    					break;
    				}
    				case RANGE: {
    					validateRange(requestInfo, propertyInfo);
    
    					break;
    				}
    				case EMAIL: {
    					validateEmail(requestInfo, propertyInfo);
    
    					break;
    				}
    				case PATTERN: {
    					validatePattern(requestInfo, propertyInfo);
    
    					break;
    				}
    				case REGULAR_EXPRESSION: {
    				    validateRegularExpression(requestInfo, propertyInfo);
    				    
    				    break;
    				}
    				default: {
    				    validateCustom(requestInfo, propertyInfo);
    				}
				}
			}
		}
	}
	
    /**
     * Efetua uma validação customizada.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private void validateCustom(RequestInfo requestInfo, PropertyInfo propertyInfo){
        StringBuilder validationMethodId = null;
        String        customValidationId = propertyInfo.getCustomValidationId();
        
        if(customValidationId.length() > 0){
            validationMethodId = new StringBuilder();
            validationMethodId.append("validate");
            validationMethodId.append(StringUtil.capitalize(customValidationId));

            try{
                String  name   = requestInfo.getName();
                String  label  = requestInfo.getLabel();
                Boolean result = (Boolean)MethodUtils.invokeMethod(this, validationMethodId.toString(), new Object[]{requestInfo, propertyInfo});

                if(!result)
                    actionFormMessageController.addValidationCustomMessage(customValidationId, name, label);
            }
            catch(Throwable e){
                actionFormMessageController.addMessage(e);
            }
        }
	}

    /**
     * Efetua a validação de obrigatoriedade.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
    private void validateRequired(RequestInfo requestInfo, PropertyInfo propertyInfo){
		String  value  = requestInfo.getValue();
		Boolean result = true;

		if(value.length() == 0 || propertyInfo.isNumber()){
	        String name  = requestInfo.getName();
	        String label = requestInfo.getLabel();
	        
			if(value.length() == 0)
			    result = false;
			else{
    			if(propertyInfo.isNumber()){
    				Locale currentLanguage = systemController.getCurrentLanguage();
    				String pattern         = requestInfo.getPattern();
    				Class  clazz           = propertyInfo.getClazz();
    				
    				try{
    	                Number number = NumberUtil.parse(clazz, value, pattern, currentLanguage);
    	                
    	                if(number.intValue() == 0)
    	                    result = false;
                    }
                    catch(ParseException e){
                    }
    			}
			}
			
			if(!result)
			    actionFormMessageController.addValidationRequiredMessage(name, label);
		}
	}

    /**
     * Efetua a validação de datas.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private void validateDateTime(RequestInfo requestInfo, PropertyInfo propertyInfo){
		String value = requestInfo.getValue();

		if(value.length() > 0){
	        String name    = requestInfo.getName();
	        String label   = requestInfo.getLabel();
			String pattern = requestInfo.getPattern();
            Date   date    = null;

			try{
				date = DateTimeUtil.parse(value, pattern);
			}
			catch(Throwable e){
			}

			if(date == null)
			    actionFormMessageController.addValidationDateTimeMessage(name, label, pattern);
		}
	}

    /**
     * Efetua a validação de números.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
    private void validateNumber(RequestInfo requestInfo, PropertyInfo propertyInfo){
		String value = requestInfo.getValue();

		if(value.length() > 0){
            Locale  currentLanguage = systemController.getCurrentLanguage();
            String  name            = requestInfo.getName();
            String  label           = requestInfo.getLabel();
            String  pattern         = requestInfo.getPattern();
            Class   clazz           = propertyInfo.getClazz();
            Number  number          = null;

			try{
				number = NumberUtil.parse(clazz, value, pattern, currentLanguage);
			}
			catch(Throwable e){
			}

			if(number == null)
			    actionFormMessageController.addValidationNumberMessage(name, label);
		}
	}

    /**
     * Efetua a validação de comparação de datas/números.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
    private void validateCompare(RequestInfo requestInfo, PropertyInfo propertyInfo){
		String        name        = requestInfo.getName();
		String        label       = requestInfo.getLabel();
        Integer       pos         = name.lastIndexOf(".");
		StringBuilder compareName = new StringBuilder();

		if(pos > 0){
			compareName.append(name.substring(0, pos));
			compareName.append(".");
		}

		compareName.append(propertyInfo.getComparePropertyId());

        Locale        currentLanguage    = systemController.getCurrentLanguage();
		RequestInfo   compareRequestInfo = systemController.getRequestInfo(compareName.toString());
		String        compareLabel       = compareRequestInfo.getLabel();
		ConditionType compareCondition   = propertyInfo.getCompareCondition();
        String        compareValue       = compareRequestInfo.getValue();
        Integer       compareFlag        = 0;
		String        pattern            = requestInfo.getPattern();
		String        value              = requestInfo.getValue();
        Class         clazz              = propertyInfo.getClazz();
		Boolean       result             = true;

		if(propertyInfo.isNumber()){
			try{
				Number number        = NumberUtil.parse(clazz, value, pattern, currentLanguage);
				Number compareNumber = NumberUtil.parse(clazz, compareValue, pattern, currentLanguage);

				switch(compareCondition){
    				case GREATER_THAN: {
    					try{
    						compareFlag = (Integer)MethodUtils.invokeMethod(number, "compareTo", compareNumber);
    					}
    					catch(Throwable e){
    						compareFlag = -1;
    					}
    
    					result = (compareFlag > 0);
    
    					break;
    				}
    				case LESS_EQUAL_THAN: {
    					try{
    						compareFlag = (Integer)MethodUtils.invokeMethod(number, "compareTo", compareNumber);
    					}
    					catch(Throwable e){
    						compareFlag = 1;
    					}
    
    					result = (compareFlag <= 0);
    
    					break;
    				}
    				case LESS_THAN: {
    					try{
    						compareFlag = (Integer)MethodUtils.invokeMethod(number, "compareTo", compareNumber);
    					}
    					catch(Throwable e){
    						compareFlag = 1;
    					}
    
    					result = (compareFlag < 0);
    
    					break;
    				}
                    default: {
                        try{
                            compareFlag = (Integer)MethodUtils.invokeMethod(number, "compareTo", compareNumber);
                        }
                        catch(Throwable e){
                            compareFlag = -1;
                        }
    
                        result = (compareFlag >= 0);
    
                        break;
                    }
				}
			}
			catch(Throwable e){
				result = true;
			}
		}
		else if(propertyInfo.isDate()){
			try{
				Date date        = DateTimeUtil.parse(value, pattern);
				Date compareDate = DateTimeUtil.parse(compareValue, pattern);

				compareFlag = (date.compareTo(compareDate));

				switch(compareCondition){
    				case GREATER_THAN: {
    					result = (compareFlag > 0);
    
    					break;
    				}
    				case LESS_EQUAL_THAN: {
    					result = (compareFlag <= 0);
    
    					break;
    				}
    				case LESS_THAN: {
    					result = (compareFlag < 0);
    
    					break;
    				}
                    default: {
                        result = (compareFlag >= 0);
    
                        break;
                    }
				}
			}
			catch(Throwable e){
				result = true;
			}
		}

		if(!result)
		    actionFormMessageController.addValidationCompareMessage(name, label, compareCondition, compareName.toString(), compareLabel);
	}

    /**
     * Efetua a validação de quantidade de palavras.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private void validateWordCount(RequestInfo requestInfo, PropertyInfo propertyInfo){
		String value = requestInfo.getValue();

		if(value.length() > 0){
		    String  name      = requestInfo.getName();
		    String  label     = requestInfo.getLabel();
			Integer wordCount = propertyInfo.getWordCount();
			String  words[]   = StringUtil.split(value, " ");

			if(words.length < wordCount)
			    actionFormMessageController.addValidationWordCountMessage(name, label, wordCount);
		}
	}

    /**
     * Efetua a validação de tamanho mínimo de caracteres.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private void validateMinimumLength(RequestInfo requestInfo, PropertyInfo propertyInfo){
	    String  name          = requestInfo.getName();
	    String  label         = requestInfo.getLabel();
		Integer minimumLength = propertyInfo.getMinimumLength();
		String  value         = requestInfo.getValue();

		if(value.length() < minimumLength)
		    actionFormMessageController.addValidationMinimumLengthMessage(name, label, minimumLength);
	}

    /**
     * Efetua a validação de tamanho máximo de caracteres.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private void validateMaximumLength(RequestInfo requestInfo, PropertyInfo propertyInfo){
        String  name          = requestInfo.getName();
        String  label         = requestInfo.getLabel();
		Integer maximumLength = propertyInfo.getMaximumLength();
		String  value         = requestInfo.getValue();

		if(value.length() > maximumLength)
		    actionFormMessageController.addValidationMaximumLengthMessage(name, label, maximumLength);
	}

    /**
     * Efetua a validação de range de datas/números.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
    private void validateRange(RequestInfo requestInfo, PropertyInfo propertyInfo){
	    Locale  currentLanguage = systemController.getCurrentLanguage();
	    String  name            = requestInfo.getName();
	    String  label           = requestInfo.getLabel();
		Integer compareFlag     = 0;
		Number  number          = null;
		Number  startNumber     = null;
		Number  endNumber       = null;
		Date    date            = null;
		Date    startDate       = null;
		Date    endDate         = null;
		String  startRange      = propertyInfo.getStartRange();
		String  endRange        = propertyInfo.getEndRange();
		Class   clazz           = propertyInfo.getClazz();
		String  pattern         = requestInfo.getPattern();
		Number  numberRange[]   = NumberUtil.getRange(clazz);
		String  value           = requestInfo.getValue();
        Boolean result          = true;

		if(propertyInfo.isDate()){
    		try{
				date = DateTimeUtil.parse(value, pattern);
    		}
    		catch(Throwable e){
    		}

    		try{
		        startDate = DateTimeUtil.parse(startRange, pattern);
    		}
    		catch(Throwable e){
    		}

            try{
                endDate = DateTimeUtil.parse(endRange, pattern);
            }
            catch(Throwable e){
            }

    		if(date != null && startDate != null && endDate != null){
    			try{
    				compareFlag = (Integer)MethodUtils.invokeMethod(startDate, "compareTo", date);
    			}
    			catch(Throwable e){
    				compareFlag = 1;
    			}

    			result = (compareFlag <= 0);

    			if(result){
    			    try{
    			        compareFlag = (Integer)MethodUtils.invokeMethod(endDate, "compareTo", date);
    				}
    				catch(Throwable e){
    					compareFlag = -1;
    				}
    
    				result = (compareFlag > 0);
    			}
    		}
            
            if(!result)
                actionFormMessageController.addValidationRangeMessage(name, label, startDate, endDate);
		}
    	else if(propertyInfo.isNumber()){
            try{
                number = NumberUtil.parse(clazz, value, pattern, currentLanguage);
            }
            catch(Throwable e){
            }

            try{
                startNumber = NumberUtil.parse(clazz, startRange, pattern, currentLanguage);
            }
            catch(Throwable e){
                if(propertyInfo.isNumber())
                    startNumber = numberRange[0];
            }

            try{
                endNumber = NumberUtil.parse(clazz, endRange, pattern, currentLanguage);
            }
            catch(Throwable e){
                if(propertyInfo.isNumber())
                    endNumber = numberRange[1];
            }

            if(number != null && startNumber != null && endNumber != null){
                try{
                    compareFlag = (Integer)MethodUtils.invokeMethod(startNumber, "compareTo", number);
                }
                catch(Throwable e){
                    compareFlag = 1;
                }

                result = (compareFlag <= 0);

                if(result){
                    try{
                        compareFlag = (Integer)MethodUtils.invokeMethod(endNumber, "compareTo", number);
                    }
                    catch(Throwable e){
                        compareFlag = -1;
                    }
    
                    result = (compareFlag > 0);
                }
        	}
            
            if(!result)
                actionFormMessageController.addValidationRangeMessage(name, label, startNumber, endNumber);
    	}
	}
	
    /**
     * Efetua a validação de expressão regular.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private void validateRegularExpression(RequestInfo requestInfo, PropertyInfo propertyInfo){
	    String value = requestInfo.getValue();
        
        if(value.length() > 0){
            String  name              = requestInfo.getName();
            String  label             = requestInfo.getLabel();
            String  regularExpression = propertyInfo.getRegularExpression();
            Pattern regexp            = Pattern.compile(regularExpression);
            Matcher matcher           = regexp.matcher(value);
            
            if(!matcher.matches())
                actionFormMessageController.addValidationRegularExpressionMessage(name, label, regularExpression);
        }
	}

    /**
     * Efetua a validação de e-Mail.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private void validateEmail(RequestInfo requestInfo, PropertyInfo propertyInfo){
		String value = requestInfo.getValue();

		if(value.length() > 0){
		    String  name    = requestInfo.getName();
		    String  label   = requestInfo.getLabel();
			String  pattern = ".+@.+\\.[a-z]+";
			Pattern regexp  = Pattern.compile(pattern);
			Matcher matcher = regexp.matcher(value);

			if(!matcher.matches())
			    actionFormMessageController.addValidationEmailMessage(name, label);
		}
	}

	/**
	 * Efetua a validação de máscara de entrada.
	 * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
	 */
	private void validatePattern(RequestInfo requestInfo, PropertyInfo propertyInfo){
	    String  name    = requestInfo.getName();
	    String  label   = requestInfo.getLabel();
		String  pattern = requestInfo.getPattern();
		String  value   = requestInfo.getValue();
        Boolean result  = true;

		if(value.length() > 0 && pattern.length() > 0){
			Integer       numberCount    = 0;
			Integer       characterCount = 0;
			StringBuilder regex          = new StringBuilder();

			for(Integer cont = 0 ; cont < pattern.length() ; cont++){
				if(pattern.charAt(cont) == '9'){
					if(characterCount > 0){
						regex.append("([a-zA-Z0-9]{1,");
						regex.append(characterCount);
						regex.append("})");

						characterCount = 0;
					}

					numberCount++;
				}
				else if(pattern.charAt(cont) == '#'){
					if(numberCount > 0){
						regex.append("([0-9]{1,");
						regex.append(numberCount);
						regex.append("})");

						numberCount = 0;
					}

					characterCount++;
				}
				else{
					if(numberCount > 0){
						regex.append("([0-9]{1,");
						regex.append(numberCount);
						regex.append("})");

						numberCount = 0;
					}

					if(characterCount > 0){
						regex.append("([a-zA-Z0-9]{1,");
						regex.append(characterCount);
						regex.append("})");

						characterCount = 0;
					}

					regex.append("\\");
					regex.append(pattern.charAt(cont));
				}
			}

			if(numberCount > 0){
				regex.append("([0-9]{1,");
				regex.append(numberCount);
				regex.append("})");

				numberCount = 0;
			}

			if(characterCount > 0){
				regex.append("([a-zA-Z0-9]{1,");
				regex.append(characterCount);
				regex.append("})");

				characterCount = 0;
			}

			Pattern regexp  = Pattern.compile(regex.toString());
			Matcher matcher = regexp.matcher(value);

			result = matcher.matches();
		}

		if(!result)
		    actionFormMessageController.addValidationPatternMessage(name, label, pattern);
	}
}