package com.pcwk.ehr.cmn;

public class SearchDTO extends DTO {

	private String searchDiv; //검색 구분
	private String searchWord; //검색어
	private String div; //구분
	
	public SearchDTO() {
	}

	/**
	 * @return the searchDiv
	 */
	public String getSearchDiv() {
		return searchDiv;
	}

	/**
	 * @param searchDiv the searchDiv to set
	 */
	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}
	
	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	@Override
	public String toString() {
		return "SearchDTO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + ", div=" + div + "]";
	}
	
}
