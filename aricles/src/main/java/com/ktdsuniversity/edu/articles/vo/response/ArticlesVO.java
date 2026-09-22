package com.ktdsuniversity.edu.articles.vo.response;

/**
 * 
 */
public class ArticlesVO {

	private String id;
	private String subject;
	private String content;
	private String email;
	private long view_Cnt;
	private long recommendCnt;
	private String delYn;
	private String crtDt;
	private String mdfyDt;
	private String fileSetId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getView_Cnt() {
		return view_Cnt;
	}

	public void setView_Cnt(long view_Cnt) {
		this.view_Cnt = view_Cnt;
	}

	public long getRecommendCnt() {
		return recommendCnt;
	}

	public void setRecommendCnt(long recommendCnt) {
		this.recommendCnt = recommendCnt;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getCrtDt() {
		return crtDt;
	}

	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}

	public String getMdfyDt() {
		return mdfyDt;
	}

	public void setMdfyDt(String mdfyDt) {
		this.mdfyDt = mdfyDt;
	}

	public String getFileSetId() {
		return fileSetId;
	}

	public void setFileSetId(String fileSetId) {
		this.fileSetId = fileSetId;
	}

	@Override
	public String toString() {
		return "ArticlesVO [id=" + id + ", subject=" + subject + ", content=" + content + ", email=" + email
				+ ", view_Cnt=" + view_Cnt + ", recommendCnt=" + recommendCnt + ", delYn=" + delYn + ", crtDt=" + crtDt
				+ ", mdfyDt=" + mdfyDt + ", fileSetId=" + fileSetId + "]";
	}

}
