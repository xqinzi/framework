package br.com.concepting.framework.util;

import java.util.Collection;
import java.util.LinkedList;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.types.PagerActionType;

/**
 * Classe que implementa p�gina��o de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class Pager implements java.io.Serializable{
	private Integer         pages                  = 0;
	private Integer         currentPage            = 1;
	private Integer         itemsPerPage           = Constants.DEFAULT_ITEMS_PER_PAGE;
	private Boolean         showFirstPageButton    = false;
	private Boolean         showPreviousPageButton = false;
	private Boolean         showNextPageButton     = false;
	private Boolean         showLastPageButton     = false;
	private Collection      data                   = null;
	private PagerActionType lastAction             = PagerActionType.REFRESH_PAGE;

	/**
	 * Indica se o bot�o de primeira p�gina deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean showFirstPageButton(){
		return showFirstPageButton;
	}

	/**
	 * Define se o bot�o de primeira p�gina deve ser exibido.
	 * 
	 * @param showFirstPageButton True/False.
	 */
	public void showFirstPageButton(Boolean showFirstPageButton){
		this.showFirstPageButton = showFirstPageButton;
	}

	/**
	 * Indica se o bot�o de p�gina anterior deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean showPreviousPageButton(){
		return showPreviousPageButton;
	}

	/**
	 * Define se o bot�o de p�gina anterior deve ser exibido.
	 * 
	 * @param showPreviousPageButton True/False.
	 */
	public void showPreviousPageButton(Boolean showPreviousPageButton){
		this.showPreviousPageButton = showPreviousPageButton;
	}

	/**
	 * Indica se o bot�o de pr�xima p�gina deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean showNextPageButton(){
		return showNextPageButton;
	}

	/**
	 * Define se o bot�o de pr�xima p�gina deve ser exibido.
	 * 
	 * @param showNextPageButton True/False.
	 */
	public void showNextPageButton(Boolean showNextPageButton){
		this.showNextPageButton = showNextPageButton;
	}

	/**
	 * Indica se o bot�o de �ltima p�gina deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean showLastPageButton(){
		return showLastPageButton;
	}

	/**
	 * Define se o bot�o de �ltima p�gina deve ser exibido.
	 * 
	 * @param showLastPageButton True/False.
	 */
	public void showLastPageButton(Boolean showLastPageButton){
		this.showLastPageButton = showLastPageButton;
	}

	/**
	 * Retorna a p�gina atual.
	 * 
	 * @return Valor inteiro contendo a p�gina atual.
	 */
	public Integer getCurrentPage(){
		return currentPage;
	}

	/**
	 * Retorna o n�mero total de p�ginas.
	 * 
	 * @return Valor inteiro contendo o n�mero total de p�ginas.
	 */
	public Integer getPages(){
		return pages;
	}

	/**
	 * Retorna o n�mero de itens por p�ginas.
	 * 
	 * @return Valor inteiro contendo o n�mero de itens por p�gina.
	 */
	public Integer getItemsPerPage(){
		return itemsPerPage;
	}

	/**
	 * Define o n�mero de itens por p�ginas.
	 * 
	 * @param itemsPerPage Valor inteiro contendo o n�mero de itens por p�gina.
	 */
	public void setItemsPerPage(Integer itemsPerPage){
		if(itemsPerPage <= 0)
			this.itemsPerPage = Constants.DEFAULT_ITEMS_PER_PAGE;
		else
			this.itemsPerPage = itemsPerPage;

		refresh();
	}

	/**
	 * Retorna a �ltima a��o de pagina��o.
	 * 
	 * @return Constante que define a �ltima a��o de pagina��o.
	 */
	public PagerActionType getLastAction(){
		return lastAction;
	}

	/**
	 * Retorna a lista contendo os dados a serem paginados.
	 * 
	 * @return Inst�ncia contendo a lista dos dados a serem paginados.
	 */
	public Collection getData(){
		return data;
	}

	/**
	 * Define a lista contendo os dados a serem paginados.
	 * 
	 * @param data Inst�ncia contendo a lista dos dados a serem paginados.
	 */
	public void setData(Collection data){
		this.data = data;
		
		refresh();
	}

	/**
	 * Retorna o �ndice do primeiro elemento da p�gina.
	 * 
	 * @return Valor inteiro contendo o �ndice.
	 */
	public Integer getStartIndex(){
		return (currentPage - 1) * itemsPerPage;
	}

	/**
	 * Retorna o �ndice do �ltimo elemento da p�gina.
	 * 
	 * @return Valor inteiro contendo o �ndice.
	 */
	public Integer getEndIndex(){
		Integer index = (((currentPage - 1) * itemsPerPage) + itemsPerPage) - 1;

		if(data != null)
			if(index > data.size())
				index = data.size() - 1;

		return index;
	}

	/**
	 * Executa uma a��o de pagina��o.
	 * 
	 * @param action Inst�ncia constante que define a a��o de pagina��o.
	 */
	public void moveTo(PagerActionType action){
		if(action != PagerActionType.PREVIOUS_PAGE && action != PagerActionType.FIRST_PAGE && action != PagerActionType.NEXT_PAGE && action != PagerActionType.LAST_PAGE && action != PagerActionType.REFRESH_PAGE)
			return;

		lastAction = action;

		switch(action){
			case FIRST_PAGE: {
				currentPage = 1;

				break;
			}
			case PREVIOUS_PAGE: {
				Integer value = currentPage - 1;

				if(value >= 1)
					currentPage = value;

				break;
			}
			case NEXT_PAGE: {
				Integer value = currentPage + 1;

				if(value <= pages)
					currentPage = value;

				break;
			}
			case LAST_PAGE: {
				currentPage = pages;

				break;
			}
			default: {
			    break;
			}
		}

		refresh();
	}

	/**
	 * Posiciona o ponteiro para uma p�gina espec�fica.
	 * Se o valor passado for maior que o n�mero de p�ginas, o ponteiro ser� posicionado na 
	 * �ltima p�gina.
	 * Se o valor passado for menor que o 1, o ponteiro ser� posicionado na primeira p�gina.
	 * 
	 * @param page Valor inteiro contendo o n�mero da p�gina.
	 */
	public void goTo(Integer page){
		lastAction  = PagerActionType.REFRESH_PAGE;
		currentPage = page;

		if(currentPage < 1)
			moveTo(PagerActionType.FIRST_PAGE);
		
		if(currentPage > pages)
			moveTo(PagerActionType.LAST_PAGE);
		else
			refresh();
	}

	/**
	 * Atualiza controles de p�gina��o.
	 */
	private void refresh(){
		if(data == null)
			data = new LinkedList();

		if(data.size() == 0){
			currentPage = 1;
			pages       = 1;
		}
		else{
			pages = data.size() / itemsPerPage;
			if(data.size() % itemsPerPage > 0)
				pages++;
		}
		
		if(pages < currentPage)
			currentPage = 1;

		if(currentPage == 1){
			showFirstPageButton(false);
			showPreviousPageButton(false);
		}
		else{
			showFirstPageButton(true);
			showPreviousPageButton(true);
		}

		if(currentPage.equals(pages)){
			showNextPageButton(false);
			showLastPageButton(false);
		}
		else{
			showNextPageButton(true);
			showLastPageButton(true);
		}
	}
}