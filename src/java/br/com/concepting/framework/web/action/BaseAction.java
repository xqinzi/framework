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
 * Classe que define a estrutura básica para as classes de implementam as ações de um formulário.
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
	 * Retorna a instância da classe de serviço vinculada a um modelo de dados.
	 * 
	 * @param modelClass Instância contendo o modelo de dados.
	 * @return Instância da classe de serviço.
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
	 * Retorna a instância da classe de serviço vinculada a ação.
	 * 
	 * @return Instância da classe de serviço.
	 * @throws InternalErrorException
	 */
    protected <S extends IService> S getService() throws InternalErrorException{
		return getService(null);
	}

	/**
	 * Retorna a instância da classe do formulário vinculado a ação.
	 * 
	 * @return Instância da classe do formulário.
	 */
    protected <F extends BaseActionForm> F getActionForm(){
		return (F)actionForm;
	}
	
	/**
	 * Retorna a instância contendo os mapeamentos das ações.
	 * 
	 * @return Instância contendo os mapeamentos.
	 */
	protected ActionMapping getActionMapping(){
    	return actionMapping;
    }

	/**
	 * Inicializa as propriedade do formulário vinculado a classe de ação.
	 */
	protected void initializeActionForm() throws Throwable{
	    String name   = actionForm.getName();
	    String action = actionForm.getAction();
	    
		PropertyUtil.clearAllProperties(actionForm);
		
		actionForm.setName(name);
		actionForm.setAction(action);
	}

	/**
	 * Carrega os objetos vinculados ao formulário atual.
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
	 * Método inicial que define a inicialização do formulário.
	 * Este método limpa todas as propriedades do formulário.
	 * 
	 * @throws Throwable
	 */
	public void init() throws Throwable{
		initializeActionForm();
		
		loadActionFormObjects();
	}

    /**
	 * Método executado quando a ação atual foi cancelada.
	 * 
	 * @throws Throwable
	 */
	public void cancel() throws Throwable{
	}

	/**
	 * Método executado quando algum elemento do formulário requisitou uma atualização.
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
	 * Efetua o redirecionamento após a execução da ação.
	 * 
	 * @return Instância contendo as propriedades do redirecionamento.
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
	 * Encontra a instância do redirecionamento desejado.
	 * 
	 * @return Instância contendo as propriedades do redirecionamento.
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