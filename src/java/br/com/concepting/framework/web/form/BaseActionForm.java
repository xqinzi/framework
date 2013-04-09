package br.com.concepting.framework.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.web.SystemController;
import br.com.concepting.framework.web.form.types.ActionFormMessageType;
import br.com.concepting.framework.web.form.util.ActionFormPopulator;
import br.com.concepting.framework.web.form.util.ActionFormValidator;

/**
 * Classe que define a estrutura b�sica de um formul�rio.
 * 
 * @author fvilarinho
 * @since 1.0
 */  
public abstract class BaseActionForm extends ActionForm{
	private String    name                      = "";
	private String    lastAction                = "";
	private String    action                    = "";
	private String    forward                   = "";
	private String    forwardOnFail             = "";
	private String    updateViews               = "";
	private Boolean   validateModel             = false;
	private Boolean   validateSearchModel       = false;
	private String    validateProperties        = "";
	private BaseModel model                     = null;
	private BaseModel searchModel               = null;
	private FormFile  uploadData                = null;
	private String    uploadDataProperty        = "";
	private String    uploadFileNameProperty    = "";
	private String    uploadContentTypeProperty = "";

    /**
     * Retorna o identificador da propriedade do modelo de dados 
     * que armazenar� o nome do arquivo do upload.
     * 
     * @return String contendo o identificador da propriedade.
     */
    public String getUploadFileNameProperty(){
        return uploadFileNameProperty;
    }

    /**
     * Define o identificador da propriedade do modelo de dados 
     * que armazenar� o nome do arquivo do upload.
     * 
     * @param uploadFileNameProperty String contendo o identificador da propriedade.
     */
    public void setUploadFileNameProperty(String uploadFileNameProperty){
        this.uploadFileNameProperty = uploadFileNameProperty;
    }

    /**
     * Retorna o identificador da propriedade do modelo de dados 
     * que armazenar� o tipo do arquivo do upload.
     * 
     * @return String contendo o identificador da propriedade.
     */
    public String getUploadContentTypeProperty(){
        return uploadContentTypeProperty;
    }

    /**
     * Define o identificador da propriedade do modelo de dados 
     * que armazenar� o tipo do arquivo do upload.
     * 
     * @param uploadContentTypeProperty String contendo o identificador da propriedade.
     */
    public void setUploadContentTypeProperty(String uploadContentTypeProperty){
        this.uploadContentTypeProperty = uploadContentTypeProperty;
    }

    /**
     * Retorna a inst�ncia contendo as propriedades do arquivo do upload.
     * 
     * @return Inst�ncia contendo as propridades do arquivo.
     */
    public FormFile getUploadData(){
        return uploadData;
    }

    /**
     * Define a inst�ncia contendo as propriedades do arquivo do upload.
     * 
     * @param uploadData Inst�ncia contendo as propridades do arquivo.
     */
    public void setUploadData(FormFile uploadData){
        this.uploadData = uploadData;
    }

    /**
     * Retorna o nome da propriedade que armazena os dados
     * do arquivo do upload.
     * 
     * @return String contendo o identificador da propriedade.
     */
    public String getUploadDataProperty(){
        return uploadDataProperty;
    }

    /**
     * Define o nome da propriedade que armazena os dados
     * do arquivo do upload.
     * 
     * @param uploadDataProperty String contendo o identificador da propriedade.
     */
    public void setUploadDataProperty(String uploadDataProperty){
        this.uploadDataProperty = uploadDataProperty;
    }

    /**
	 * Retorna os identificadores das views (separados por v�rgula) a serem atualizadas ap�s o
	 * processamento da a��o requisitada.
	 * 
	 * @return String contendo os identificadores das views.
	 */
	public String getUpdateViews(){
        return updateViews;
    }

    /**
     * Define os identificadores das views (separados por v�rgula) a serem atualizadas ap�s o
     * processamento da a��o requisitada.
     * 
     * @param updateViews String contendo os identificadores das views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }

    /**
	 * Retorna o nome do formul�rio.
	 * 
	 * @return String contendo o nome do formul�rio.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Define o nome do formul�rio.
	 * 
	 * @param name String contendo o nome do formul�rio.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Retorna o identificador da a��o a ser executada.
	 * 
	 * @return String contendo o identificador da a��o.
	 */
	public String getAction(){
		return action;
	}
	
	/**
	 * Define o identificador da a��o a ser executada.
	 * 
	 * @param action String contendo o identificador da a��o.
	 */
	public void setAction(String action){
		this.action = action;
	}
	
	/**
	 * Retorna o identificador da a��o anterior a ser executada.
	 * 
	 * @return String contendo o identificador da a��o anterior.
	 */
	public String getLastAction(){
		return lastAction;
	}
	
	/**
	 * Define o identificador da a��o anterior a ser executada.
	 * 
	 * @param lastAction String contendo o identificador da a��o anterior.
	 */
	public void setLastAction(String lastAction){
		this.lastAction = lastAction;
	}
	
	/**
	 * Retorna o identificador do redirecionamento em caso de falha no processamento.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForwardOnFail(){
		return forwardOnFail;
	}
	
	/**
	 * Define o identificador do redirecionamento em caso de falha no processamento.
	 * 
	 * @param forwardOnFail String contendo o identificador do redirecionamento.
	 */
	public void setForwardOnFail(String forwardOnFail){
		this.forwardOnFail = forwardOnFail;
	}
	
	/**
	 * Retorna o identificador do redirecionamento ap�s processamento.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForward(){
		return forward;
	}
	
	/**
	 * Define o identificador do redirecionamento ap�s processamento.
	 * 
	 * @param forward String contendo o identificador do redirecionamento.
	 */
	public void setForward(String forward){
		this.forward = forward;
	}
	
	/**
	 * Indica se o modelo de dados deve ser validado.
	 * 
	 * @return True/False.
	 */
	public Boolean validateModel(){
		return validateModel;
	}
	
	/**
	 * Indica se o modelo de dados deve ser validado.
	 * 
	 * @return True/False.
	 */
	public Boolean getValidateModel(){
		return validateModel();
	}
	
	/**
	 * Define se o modelo de dados deve ser validado.
	 * 
	 * @param validateModel True/False.
	 */
	public void setValidateModel(Boolean validateModel){
		this.validateModel = validateModel;
	}
	
	/**
	 * Indica se o modelo de dados de pesquisa deve ser validado.
	 * 
	 * @return True/False.
	 */
	public Boolean validateSearchModel(){
		return validateSearchModel;
	}
	
	/**
	 * Indica se o modelo de dados de pesquisa deve ser validado.
	 * 
	 * @return True/False.
	 */
	public Boolean getValidateSearchModel(){
		return validateSearchModel();
	}
	
	/**
	 * Define se o modelo de dados de pesquisa deve ser validado.
	 * 
	 * @param validateSearchModel True/False.
	 */
	public void setValidateSearchModel(Boolean validateSearchModel){
		this.validateSearchModel = validateSearchModel;
	}
	
	/**
	 * Retorna a inst�ncia do modelo de dados vinculado ao formul�rio.
	 * 
	 * @return Inst�ncia do modelo de dados.
	 */
    public <M extends BaseModel> M getModel(){
		if(model == null){
			Class<M> modelClass = null;
			
			try{
				modelClass = ModelUtil.getModelClassByActionForm(getClass());
				model      = (M)ConstructorUtils.invokeConstructor(modelClass, null);
			}
			catch(Throwable e){
			}
		}
		
		return (M) model;
	}
	
	/**
	 * Define a inst�ncia do modelo de dados vinculado ao formul�rio.
	 * 
	 * @param model Inst�ncia do modelo de dados.
	 */
	public <M extends BaseModel> void setModel(M model){
		this.model = model;
	}
	
	/**
	 * Retorna a inst�ncia do modelo de dados de pesquisa vinculado ao formul�rio.
	 * 
	 * @return Inst�ncia do modelo de dados de pesquisa.
	 */
    public <M extends BaseModel> M getSearchModel(){
		if(searchModel == null){
			Class<M> modelClass = null;
			
			try{
				modelClass  = ModelUtil.getModelClassByActionForm(getClass());
				searchModel = (M) ConstructorUtils.invokeConstructor(modelClass, null);
			}
			catch(Throwable e){
			}
		}
		
		return (M) searchModel;
	}
	
	/**
	 * Define a inst�ncia do modelo de dados de pesquisa vinculado ao formul�rio.
	 * 
	 * @param searchModel Inst�ncia do modelo de dados de pesquisa.
	 */
	public <M extends BaseModel> void setSearchModel(M searchModel){
		this.searchModel = searchModel;
	}
	
	/**
	 * Retorna uma string delimitada contendo as propriedades do modelo de dados a serem validadas.
	 * 
	 * @return String delimitada contendo as propriedades do modelo de dados.
	 */
	public String getValidateProperties(){
		return validateProperties;
	}
	
	/**
	 * Define uma string delimitada contendo as propriedades do modelo de dados a serem validadas.
	 * 
	 * @param validateProperties String delimitada contendo as propriedades do modelo de dados.
	 */
	public void setValidateProperties(String validateProperties){
		this.validateProperties = validateProperties;
	}
	
	/**
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
	    SystemController            requestController           = new SystemController(request, null);
		ActionFormMessageController actionFormMessageController = requestController.getActionFormMessageController();
		ActionErrors                errors                      = null;
		
		try{
			populate(requestController);
			
			errors = validate(requestController);
			
			if(errors != null && errors.size() > 0)
				setAction(getLastAction());
		}
		catch(Throwable e){
		    actionFormMessageController.addMessage(e);
		}
		
		return errors;
	}
	
	/**
	 * Popula o formul�rio.
	 * 
	 * @param systemController Inst�ncia do controlador dos dados da requisi��o.
	 * @throws Throwable
	 */
	private void populate(SystemController systemController) throws Throwable{
		ActionFormPopulator populator = new ActionFormPopulator(this, systemController);
		
		populator.populate();
	}
	
	/**
	 * Efetua a valida��o do formul�rio.
	 * 
	 * @param systemController Inst�ncia do controlador dos dados da requisi��o.
	 * @return Inst�ncia contendo as mensagens de valida��o do formul�rio.
	 */
	private ActionErrors validate(SystemController systemController){
        ActionFormMessageController actionFormMessageController = systemController.getActionFormMessageController();
		ActionErrors                actionErrors                = new ActionErrors();
		
		if(validateModel() || validateSearchModel()){
			ModelInfo modelInfo  = null;
			Class     modelClass = null;
			
			try{
				modelClass = ModelUtil.getModelClassByActionForm(getClass());
			}
			catch(Throwable e){
			}
			
			if(modelClass == null)
				return null;
			
			modelInfo = ModelUtil.getModelInfo(modelClass);
			
			if(modelInfo == null)
				return null;
			
			Class validatorClass = modelInfo.getValidatorClass();
			
			if(validatorClass != null){
				try{
					ActionFormValidator validator = (ActionFormValidator)ConstructorUtils.invokeConstructor(validatorClass, new Object[]{this, systemController});
					
					validator.validate();
				}
				catch(Throwable e){
				    actionFormMessageController.addMessage(e);
				}
			}
		}
		
		actionErrors.add(actionFormMessageController.getMessages(ActionFormMessageType.ERROR));
		actionErrors.add(actionFormMessageController.getMessages(ActionFormMessageType.VALIDATION));
		
		if(actionErrors.size() > 0)
		    setAction(getLastAction());
		
		return actionErrors;
	}
}