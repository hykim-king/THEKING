package com.pcwk.ehr.board.domain;

import com.pcwk.ehr.cmn.DTO;

public class BoardDTO extends DTO {
	private int boardNo;     //게시판 번호
	private String title;    //게시판 제목
	private String contents; //게시판 내용
	private int views;       //조회수
	private int boardPart;   //게시판 구분
	private String regId;    //작성자
	private String regDate;  //등록일
	private String modDate;  //수정일

	public BoardDTO() {
	}

	/**
	 * @return the boardNo
	 */
	public int getBoardNo() {
		return boardNo;
	}

	/**
	 * @param boardNo the boardNo to set
	 */
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the views
	 */
	public int getViews() {
		return views;
	}

	/**
	 * @param views the views to set
	 */
	public void setViews(int views) {
		this.views = views;
	}

	/**
	 * @return the boardPart
	 */
	public int getBoardPart() {
		return boardPart;
	}

	/**
	 * @param boardPart the boardPart to set
	 */
	public void setBoardPart(int boardPart) {
		this.boardPart = boardPart;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @param boardNo
	 * @param title
	 * @param contents
	 * @param views
	 * @param boardPart
	 * @param regId
	 * @param regDate
	 * @param modDate
	 */
	public BoardDTO(int boardNo, String title, String contents, int views, int boardPart, String regId, String regDate,
			String modDate) {
		super();
		this.boardNo = boardNo;
		this.title = title;
		this.contents = contents;
		this.views = views;
		this.boardPart = boardPart;
		this.regId = regId;
		this.regDate = regDate;
		this.modDate = modDate;
	}

	@Override
	public String toString() {
		return "BoardDTO [boardNo=" + boardNo + ", title=" + title + ", contents=" + contents + ", views=" + views
				+ ", boardPart=" + boardPart + ", regId=" + regId + ", regDate=" + regDate + ", modDate=" + modDate
				+ "]";
	}

}