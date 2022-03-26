package com.sarfaraz.management.model;

import java.util.List;

public class ResponseData {
	private int totalpage;
	private long totaldata;
	private int size;
	private int pagenumber;
	private List data;

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public long getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(int totaldata) {
		this.totaldata = totaldata;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPagenumber() {
		return pagenumber;
	}

	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public ResponseData(int totalpage, long totaldata, int size, int pagenumber, List data) {
		super();
		this.totalpage = totalpage;
		this.totaldata = totaldata;
		this.size = size;
		this.pagenumber = pagenumber;
		this.data = data;
	}
	

	public ResponseData() {
		super();

	}

}
