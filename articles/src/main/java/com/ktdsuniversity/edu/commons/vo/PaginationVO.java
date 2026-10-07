package com.ktdsuniversity.edu.commons.vo;

import lombok.Data;

@Data
public class PaginationVO {

	/**
	 * 노출할 페이지의 번호
	 */
	private int pageNo;
	
	/**
	 * 한 페이지에 노출시킬 아이템의 개수 : default = 10
	 */
	private int listSize;
	
	/**
	 * 총 페이지 개수 (총 항목의 개수 / listSize)
	 */
	private long pageCount;
	
	public PaginationVO() {
		this.listSize = 10;
	}
	
	public void calculatePageCount(long itemCount) {
		this.pageCount = Math.ceilDiv(itemCount, this.listSize);
	}
	
	public static void main(String[] args) {
		PaginationVO pageTest = new PaginationVO();
		pageTest.calculatePageCount(62);
		
		System.out.println(pageTest);
	}
	
}
