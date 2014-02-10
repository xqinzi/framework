package br.com.concepting.framework.util.helpers;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import br.com.concepting.framework.model.util.PropertyUtil;

/**
 * Classe que define um datasource (fonte de dados) a ser utilizado por um relatório.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class ReportDataSource implements JRRewindableDataSource{
	private Collection<?> data        = null;
	private Iterator<?>   iterator    = null;
	private Object        currentItem = null;

	/**
	 * Construtor - Define os dados do datasource de um relatório.
	 *
	 * @param data Instância contendo a lista de dados a ser utilizada no datasource.
	 */
	public ReportDataSource(Collection<?> data){
		super();

		this.data = data;

		moveFirst();
	}

	/**
	 * @see net.sf.jasperreports.engine.JRDataSource#next()
	 */
	public boolean next(){
		boolean hasNext = false;

		if(iterator != null){
			hasNext = iterator.hasNext();
			if(hasNext)
				currentItem = iterator.next();
		}

		return hasNext;
	}

	/**
	 * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
	 */
	public Object getFieldValue(JRField field) throws JRException{
		if(currentItem != null){
			String fieldName = field.getName();

			try{
				Class<?> clazz      = PropertyUtil.getPropertyType(currentItem, fieldName);
				Object   fieldValue = PropertyUtil.getProperty(currentItem, fieldName);
				Class<?> superClass = clazz;

				while(true){
					if(superClass.getSuperclass() == null || superClass.getSuperclass().equals(Object.class))
						break;

					superClass = superClass.getSuperclass();
				}

				if(clazz.equals(AbstractCollection.class))
					return new ReportDataSource((Collection<?>)fieldValue);

				return fieldValue;
			}
			catch(Throwable e){
				throw new JRException(e);
			}
		}

		return null;
	}

	/**
	 * @see net.sf.jasperreports.engine.JRRewindableDataSource#moveFirst()
	 */
	public void moveFirst(){
		if(data != null)
			iterator = data.iterator();
	}
}