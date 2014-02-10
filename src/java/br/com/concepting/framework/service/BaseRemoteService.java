package br.com.concepting.framework.service;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * Classe que define a estrutura básica para uma classe de serviço distribuída.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseRemoteService extends BaseService implements SessionBean{
    private static final long serialVersionUID = 8259171457875387099L;

    private SessionContext sessionContext = null;

	/**
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException{
		this.sessionContext = sessionContext;
	}

	/**
	 * Retorna a instância contendo as propriedades do contexto do serviço.
	 *
	 * @return Instância contendo as propriedades do contexto.
	 */
	public SessionContext getSessionContext(){
     	return sessionContext;
    }

	/**
	 * Executado no momento do instanciamento do serviço.
	 */
    public void ejbCreate(){
    }

    /**
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate() throws EJBException, RemoteException{
    }

    /**
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate() throws EJBException, RemoteException{
    }

    /**
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove() throws EJBException, RemoteException{
    }
}