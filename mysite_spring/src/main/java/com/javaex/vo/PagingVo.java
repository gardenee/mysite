package com.javaex.vo;

import java.util.List;

public class PagingVo {
	
	private List<BoardVo> bList;
	private int lstCnt = 10;
	private int totCnt;
	private int currPage;
	private int btnCnt = 10;
	private int startBtn;
	private int endBtn;
	private boolean next = false;
	private boolean prev = false;
	
	public PagingVo() {
		
	}

	public PagingVo(List<BoardVo> bList, int totCnt, int currPage) {
		this.bList = bList;
		this.totCnt = totCnt;
		this.currPage = currPage;
		this.endBtn = ((currPage-1)/btnCnt + 1) * btnCnt;
		this.startBtn = endBtn - btnCnt + 1;
		
		if (lstCnt * endBtn < totCnt) next = true;
		else endBtn = ((totCnt-1)/lstCnt)+1;
		
		if (startBtn != 1) prev = true;
	}

	public List<BoardVo> getbList() {
		return bList;
	}

	public void setbList(List<BoardVo> bList) {
		this.bList = bList;
	}

	public int getLstCnt() {
		return lstCnt;
	}

	public void setLstCnt(int lstCnt) {
		this.lstCnt = lstCnt;
	}

	public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getBtnCnt() {
		return btnCnt;
	}

	public void setBtnCnt(int btnCnt) {
		this.btnCnt = btnCnt;
	}

	public int getStartBtn() {
		return startBtn;
	}

	public void setStartBtn(int startBtn) {
		this.startBtn = startBtn;
	}

	public int getEndBtn() {
		return endBtn;
	}

	public void setEndBtn(int endBtn) {
		this.endBtn = endBtn;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	@Override
	public String toString() {
		return "PagingVo [bList=" + bList + ", lstCnt=" + lstCnt + ", totCnt=" + totCnt + ", currPage=" + currPage
				+ ", btnCnt=" + btnCnt + ", startBtn=" + startBtn
				+ ", endBtn=" + endBtn + ", next=" + next + ", prev=" + prev + "]";
	}
	
}
