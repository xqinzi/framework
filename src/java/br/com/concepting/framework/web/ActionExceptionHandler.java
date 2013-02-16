package br.com.concepting.framework.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.web.action.types.ActionType;
import br.com.concepting.framework.web.form.ActionFormMessageController;
import br.com.concepting.framework.web.form.BaseActionForm;

/**
 * Classe responsável por interceptar os erros gerados no processamento de uma
 * ação de um formulário.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ActionExceptionHandler extends ExceptionHandler{
    private SystemController            systemController            = null;
    private ActionFormMessageController actionFormMessageController = null;
	private BaseActionForm              actionForm                  = null;
	private ActionMapping               actionMapping               = null;
	
	/**
	 * Retorna a instância do redirecionamento do tratamento da exceção.
	 * 
	 * @return Instância contendo as propriedades do redirecionamento.
	 */
	private ActionForward findForward(){
        actionForm.setAction(actionForm.getLastAction());

        String forward = actionForm.getForwardOnFail();
		
		if(forward.length() == 0)
		    forward = actionForm.getForward();

		ActionForward actionForward = actionMapping.findForward(forward);

		if(actionForward == null || actionForward.getPath() == null || actionForward.getPath().length() == 0) 
			actionForward = actionMapping.getInputForward();

		return actionForward;
	}
	
	/**
	 * Efetua o processamento do redirecionamento do tratamento da exceção.
	 * 
	 * @return Instância contendo as propriedades do redirecionamento.
	 */
	private ActionForward processForward(){
        ActionForward forward    = findForward();
        String        action     = actionForm.getAction();
        ActionType    actionType = ActionType.valueOf(action.toUpperCase());

        if(actionType != ActionType.REFRESH)
            actionForm.setLastAction(action);

        return forward;
	}

	/**
	 * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception, org.apache.struts.config.ExceptionConfig, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(Exception exception, ExceptionConfig config, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException{
        actionMapping               = mapping;
		actionForm                  = (BaseActionForm)form;
		systemController            = new SystemController(request, response);
		actionFormMessageController = systemController.getActionFormMessageController();
		
		Throwable e = ExceptionUtil.getOriginException(exception);
		
		if(ExceptionUtil.isExpectedException(e)){
		    actionFormMessageController.addMessage(e);

			return processForward();
		}

		if(!ExceptionUtil.isInternalErrorException(e))
		    e = new InternalErrorException(e);
		
		systemController.setCurrentException(e);

		return new ActionForward(config.getPath());
	}
}