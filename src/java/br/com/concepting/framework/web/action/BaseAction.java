package br.com.concepting.framework.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.FormModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.exceptions.ItemNotSelectedException;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.web.SecurityController;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.util.ServiceUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.SystemController;
import br.com.concepting.framework.web.action.types.ActionType;
import br.com.concepting.framework.web.form.ActionFormMessageController;
import br.com.concepting.framework.web.form.BaseActionForm;
import br.com.concepting.framework.web.form.types.ActionFormMessageType;

/** 
 * Classe que define a estrutura b�sica para as classes de implementam as a��es de um formul�rio.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseAction extends DispatchAction{
	protected SystemController            systemController            = null;
    protected ActionFormMessageController actionFormMessageController = null;
	protected SecurityController          securityController          = null;
	private   BaseActionForm              actionForm                  = null;
	private   ActionMapping               actionMapping               = null;

	/**
	 * Retorna a inst�ncia da classe de servi�o vinculada a um modelo de dados.
	 * 
	 * @param modelClass Inst�ncia contendo o modelo de dados.
	 * @return Inst�ncia da classe de servi�o.
	 * @throws InternalErrorException
	 */
    protected <S extends IService, M extends BaseModel> S getService(Class<M> modelClass) throws InternalErrorException{
		try{
			if(modelClass == null)
				modelClass = ModelUtil.getModelClassByAction(getClass());

            LoginSessionModel loginSession = securityController.getLoginSession();
			S                 service      = ServiceUtil.instantiate(modelClass);
			
			service.setLoginSession(loginSession);
			
			return service;
		}
		catch(Throwable e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Retorna a inst�ncia da classe de servi�o vinculada a a��o.
	 * 
	 * @return Inst�ncia da classe de servi�o.
	 * @throws InternalErrorException
	 */
    protected <S extends IService> S getService() throws InternalErrorException{
		return getService(null);
	}

	/**
	 * Retorna a inst�ncia da classe do formul�rio vinculado a a��o.
	 * 
	 * @return Inst�ncia da classe do formul�rio.
	 */
    protected <F extends BaseActionForm> F getActionForm(){
		return (F)actionForm;
	}
	
	/**
	 * Retorna a inst�ncia contendo os mapeamentos das a��es.
	 * 
	 * @return Inst�ncia contendo os mapeamentos.
	 */
	protected ActionMapping getActionMapping(){
    	return actionMapping;
    }

	/**
	 * Inicializa as propriedade do formul�rio vinculado a classe de a��o.
	 */
	protected void initializeActionForm() throws Throwable{
	    String name   = actionForm.getName();
	    String action = actionForm.getAction();
	    
		PropertyUtil.clearAllProperties(actionForm);
		
		actionForm.setName(name);
		actionForm.setAction(action);
	}

	/**
	 * Carrega os objetos vinculados ao formul�rio atual.
	 */
	protected void loadActionFormObjects() throws Throwable{
		LoginSessionModel loginSession = securityController.getLoginSession();
		SystemModuleModel systemModule = (loginSession != null ? loginSession.getSystemModule() : null);
		
		if(systemModule != null){
		    try{
		        FormModel form = systemModule.getForm(actionForm.getName());
		        
		        if(form != null){
		            IService service = getService(form.getClass());
		            
		            form = service.loadReference(form, "objects");
		            
		            systemModule.setForm(form);
		            
                    loginSession.setSystemModule(systemModule);
                    
                    securityController.setLoginSession(loginSession);
		        }
		    }
		    catch(Throwable e){
		    }
		}
	}

	/**
	 * M�todo inicial que define a inicializa��o do formul�rio.
	 * Este m�todo limpa todas as propriedades do formul�rio.
	 * 
	 * @throws Throwable
	 */
	public void init() throws Throwable{
		initializeActionForm();
		
		loadActionFormObjects();
	}

    /**
	 * M�todo executado quando a a��o atual foi cancelada.
	 * 
	 * @throws Throwable
	 */
	public void cancel() throws Throwable{
	}

	/**
	 * M�todo executado quando algum elemento do formul�rio requisitou uma atualiza��o.
	 * 
	 * @throws Throwable
	 */
	public void refresh() throws Throwable{
	}
	
	/**
	 * Efetua o upload de um arquivo.
	 * 
	 * @throws Throwable
	 */
	public void upload() throws Throwable{
	    BaseActionForm actionForm = getActionForm();
	    BaseModel      model      = actionForm.getModel();
	    FormFile       uploadData = actionForm.getUploadData();
	    
	    if(model == null || uploadData == null)
	        throw new ItemNotSelectedException();
	    
        String uploadDataProperty = actionForm.getUploadDataProperty();
        
        PropertyUtil.setProperty(model, uploadDataProperty, uploadData.getFileData());
        
        String uploadFileNameProperty = actionForm.getUploadFileNameProperty();
        
        if(uploadFileNameProperty.length() > 0)
            PropertyUtil.setProperty(model, uploadFileNameProperty, uploadData.getFileName());
        
        String uploadContentTypeProperty = actionForm.getUploadContentTypeProperty();
        
        if(uploadContentTypeProperty.length() > 0)
            PropertyUtil.setProperty(model, uploadContentTypeProperty, uploadData.getContentType());
        
        actionForm.setUploadData(null);
        actionForm.setUploadDataProperty("");
        actionForm.setUploadFileNameProperty("");
        actionForm.setUploadContentTypeProperty("");

        actionFormMessageController.addSuccessMessage();
	}
	
	/**
	 * @see org.apache.struts.actions.DispatchAction#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
        systemController            = new SystemController(request, response);
        actionFormMessageController = systemController.getActionFormMessageController();
        securityController          = systemController.getSecurityController();
        actionMapping               = mapping;
		actionForm                  = (BaseActionForm)form;

        String action = StringUtil.trim(getMethodName(mapping, form, request, response, AttributeConstants.ACTION_KEY));

        if(action.length() == 0)
            action = ActionType.INIT.getMethod();

        actionForm.setName(actionMapping.getName());
		actionForm.setAction(action);

		try{
			MethodUtils.invokeMethod(this, action, null);
			
			return processForward();
		}
		catch(NoSuchMethodException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Efetua o redirecionamento ap�s a execu��o da a��o.
	 * 
	 * @return Inst�ncia contendo as propriedades do redirecionamento.
	 */
	protected ActionForward processForward(){
		ActionForward forward    = findForward();
		String        action     = actionForm.getAction();
		ActionType    actionType = null;
		
		try{
		    actionType = ActionType.valueOf(action.toUpperCase());
		}
		catch(Throwable e){
		    actionType = null;
		}

		if(actionType != ActionType.REFRESH)
			actionForm.setLastAction(action);

		if(!systemController.hasForwardOrRedirect() && !systemController.hasOutputContent())
			return forward;

		return null;
	}

	/**
	 * Encontra a inst�ncia do redirecionamento desejado.
	 * 
	 * @return Inst�ncia contendo as propriedades do redirecionamento.
	 */
	protected ActionForward findForward(){
		ActionMessages messages = actionFormMessageController.getMessages(ActionFormMessageType.ERROR);
		String         forward  = "";

		if(messages != null && messages.size() > 0){
			forward = actionForm.getForwardOnFail();
			
			if(forward.length() == 0)
			    forward = actionForm.getForward();

			actionForm.setAction(actionForm.getLastAction());
		}
		else{
			messages = actionFormMessageController.getMessages(ActionFormMessageType.WARNING);
			
			if(messages != null && messages.size() > 0){
				forward = actionForm.getForwardOnFail();

	            if(forward.length() == 0)
	                forward = actionForm.getForward();

	            actionForm.setAction(actionForm.getLastAction());
			}
			else{
				messages = actionFormMessageController.getMessages(ActionFormMessageType.VALIDATION);
				
				if(messages != null && messages.size() > 0){
					forward = actionForm.getForwardOnFail();
					
		           if(forward.length() == 0)
		                forward = actionForm.getForward();

		            actionForm.setAction(actionForm.getLastAction());
				}
				else
					forward = actionForm.getForward();
			}
		}

		ActionForward actionForward = actionMapping.findForward(forward);

		if(actionForward == null || actionForward.getPath() == null || actionForward.getPath().length() == 0)
			actionForward = actionMapping.getInputForward();

		return actionForward;
	}
}