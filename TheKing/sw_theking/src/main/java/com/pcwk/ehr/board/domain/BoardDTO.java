package com.pcwk.ehr.board.domain;

import com.pcwk.ehr.cmn.DTO;

public class BoardDTO extends DTO {
	private int seq;
	private String title;
	private String div;
	private String contents;
	private int readCnt;
	private String regDt;
	private String regId;
	private String modDt;
	private String modId;

	public BoardDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public BoardDTO(int seq, String title, String div, String contents, int readCnt, String regDt, String regId,
			String modDt, String modId) {
		super();
		this.seq = seq;
		this.title = title;
		this.div = div;
		this.contents = contents;
		this.readCnt = readCnt;
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
	}

	@Override
	public String toString() {
		return "BoardDTO [seq=" + seq + ", title=" + title + ", div=" + div + ", contents=" + contents + ", readCnt="
				+ readCnt + ", regDt=" + regDt + ", regId=" + regId + ", modDt=" + modDt + ", modId=" + modId + "]";
	}

}