package br.com.concepting.framework.util;

import java.util.Collection;
import java.util.LinkedList;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.types.PagerActionType;

/**
 * Classe que implementa páginação de dados.
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
	 * Indica se o botão de primeira página deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean showFirstPageButton(){
		return showFirstPageButton;
	}

	/**
	 * Define se o botão de primeira página deve ser exibido.
	 * 
	 * @param showFirstPageButton True/False.
	 */
	public void showFirstPageButton(Boolean showFirstPageButton){
		this.showFirstPageButton = showFirstPageButton;
	}

	/**
	 * Indica se o botão de página anterior deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean showPreviousPageButton(){
		return showPreviousPageButton;
	}

	/**
	 * Define se o botão de página anterior deve ser exibido.
	 * 
	 * @param showPreviousPageButton True/False.
	 */
	public void showPreviousPageButton(Boolean showPreviousPageButton){
		this.showPreviousPageButton = showPreviousPageButton;
	}

	/**
	 * Indica se o botão de próxima página deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean showNextPageButton(){
		return showNextPageButton;
	}

	/**
	 * Define se o botão de próxima página deve ser exibido.
	 * 
	 * @param showNextPageButton True/False.
	 */
	public void showNextPageButton(Boolean showNextPageButton){
		this.showNextPageButton = showNextPageButton;
	}

	/**
	 * Indica se o botão de última página deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean showLastPageButton(){
		return showLastPageButton;
	}

	/**
	 * Define se o botão de última página deve ser exibido.
	 * 
	 * @param showLastPageButton True/False.
	 */
	public void showLastPageButton(Boolean showLastPageButton){
		this.showLastPageButton = showLastPageButton;
	}

	/**
	 * Retorna a página atual.
	 * 
	 * @return Valor inteiro contendo a página atual.
	 */
	public Integer getCurrentPage(){
		return currentPage;
	}

	/**
	 * Retorna o número total de páginas.
	 * 
	 * @return Valor inteiro contendo o número total de páginas.
	 */
	public Integer getPages(){
		return pages;
	}

	/**
	 * Retorna o número de itens por páginas.
	 * 
	 * @return Valor inteiro contendo o número de itens por página.
	 */
	public Integer getItemsPerPage(){
		return itemsPerPage;
	}

	/**
	 * Define o número de itens por páginas.
	 * 
	 * @param itemsPerPage Valor inteiro contendo o número de itens por página.
	 */
	public void setItemsPerPage(Integer itemsPerPage){
		if(itemsPerPage <= 0)
			this.itemsPerPage = Constants.DEFAULT_ITEMS_PER_PAGE;
		else
			this.itemsPerPage = itemsPerPage;

		refresh();
	}

	/**
	 * Retorna a última ação de paginação.
	 * 
	 * @return Constante que define a última ação de paginação.
	 */
	public PagerActionType getLastAction(){
		return lastAction;
	}

	/**
	 * Retorna a lista contendo os dados a serem paginados.
	 * 
	 * @return Instância contendo a lista dos dados a serem paginados.
	 */
	public Collection getData(){
		return data;
	}

	/**
	 * Define a lista contendo os dados a serem paginados.
	 * 
	 * @param data Instância contendo a lista dos dados a serem paginados.
	 */
	public void setData(Collection data){
		this.data = data;
		
		refresh();
	}

	/**
	 * Retorna o índice do primeiro elemento da página.
	 * 
	 * @return Valor inteiro contendo o índice.
	 */
	public Integer getStartIndex(){
		return (currentPage - 1) * itemsPerPage;
	}

	/**
	 * Retorna o índice do último elemento da página.
	 * 
	 * @return Valor inteiro contendo o índice.
	 */
	public Integer getEndIndex(){
		Integer index = (((currentPage - 1) * itemsPerPage) + itemsPerPage) - 1;

		if(data != null)
			if(index > data.size())
				index = data.size() - 1;

		return index;
	}

	/**
	 * Executa uma ação de paginação.
	 * 
	 * @param action Instância constante que define a ação de paginação.
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
	 * Posiciona o ponteiro para uma página específica.
	 * Se o valor passado for maior que o número de páginas, o ponteiro será posicionado na 
	 * última página.
	 * Se o valor passado for menor que o 1, o ponteiro será posicionado na primeira página.
	 * 
	 * @param page Valor inteiro contendo o número da página.
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
	 * Atualiza controles de páginação.
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