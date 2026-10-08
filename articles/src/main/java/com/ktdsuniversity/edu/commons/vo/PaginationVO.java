package com.ktdsuniversity.edu.commons.vo;

import lombok.Data;

@Data
public class PaginationVO {
	
	
	/** 노출할 페이지의 번호 */
	private long pageNo;

	/** 한 페이지에 노출시킬 아이템의 개수 : default = 10 */
	private int listSize;
	
	/** 총 페이지 개수(총 항목개수 / listSize) */
	private long pageCount;
	
	/**페이지 그룹으로 묶을 페이지의 개수 : Default = 10	 */
	private int pageCountInGroup;
	
	/** 총 페이지 그룹의 개수 */
	private long groupCount;
	
	/** 현재페이지의 그룹 번호 */
	private long groupNo;
	
	/** 현재 페이지 그룹의 시작 페이지 번호 */
	private long groupStartPageNo;
	
	/** 현재 페이지 그룹의 마지막 페이지 번호 */
	private long groupEndPageNo;
	
	/** 다음 페이지 그룹 존재 여부 */
	private boolean hasNextGroup;
	
	/** 이전 페이지 그룹 존재 여부 */
	private boolean hasPrevGroup;
	
	/** 다음 페이지그룹의 시작 페이지 번호  */
	private long nextGroupstartPageNo;
	
	/** 이전 페이지 그룹의 마지막 페이지 번호 */
	private long prevGroupEndPageNo;
	
	public PaginationVO() {
		this.listSize = 10;
		this.pageCountInGroup = 10;
	}
	
	
	public void calculatePageCount(long itemCount) {
		
		this.pageCount =  Math.ceilDiv(itemCount, this.listSize);
		
		//그룹의 총 개수 계산. (페이지 수 / 그룹당 페이지의 수) - 소수점 올림 
		this.groupCount = Math.ceilDiv(this.pageCount, this.pageCountInGroup);
		
		//현재 페이지의 그룹 계산 (페이지 번호 / 그룹당 페이지의 수) - 소수점 버림
		this.groupNo = this.pageNo / this.pageCountInGroup;
		
		//현재 페이지 그룹의 첫 번째 페이지 번호(현재 페이지의 그룹번호 * 그룹당 페이지 수)
		this.groupStartPageNo = this.groupNo * this.pageCountInGroup;
		
		//현재 페이지 그룹의 마지막 페이지 번호((현재페이지의 그룹번호 + 1) * 그룹당 페이지 수 -1)
		this.groupEndPageNo = (this.groupNo + 1) * this.pageCountInGroup -1;
		
		//현재 그룹의 마지막 페이지가 총 페이지의 수의 이상일때
		if(this.groupEndPageNo >= this.pageCount) {
			// 현재 그룹의 마지막 페이수를 보전한다. (총 페이지 수 - 1)
			this.groupEndPageNo = this.pageCount -1;
		}
		
		
		// 다음 그룹이 있는지 확인한다. ( 현재 그룹 번호 + 1< 총 그룹의 수 )
		this.hasNextGroup = this.groupNo + 1 < this.groupCount;
		
		// 이전 그룹이 있는지 확인한다. (현재 그룹 번호 > 0)
		this.hasPrevGroup = this.groupNo > 0;
		
		//다음 그룹이 존재할 때 다음 그룹의 시작 페이지 번호 (현재 그룹의 마지막 페이지 번호 + 1)
		if(this.hasNextGroup) {
			this.nextGroupstartPageNo = this.groupEndPageNo + 1;
		}
		//이전 그룹이 존재 할때 이전 그룹의 마지막 페이지 번호 (현재 그룹의 시작 페이지 번호 - 1)
		if(this.hasPrevGroup) {
			this.prevGroupEndPageNo = this.groupStartPageNo - 1;
		}
		
		
		
	}
	
}
