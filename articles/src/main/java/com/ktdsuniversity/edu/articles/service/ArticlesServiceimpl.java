package com.ktdsuniversity.edu.articles.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.files.components.MultipartHandler;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArticlesServiceimpl implements ArticlesService{
	
	private ArticlesDao articlesDao;
//	private FilesDao filesDao;
	private MultipartHandler multipartHandler;
//	@AllArgsConstructor
//	=
//	public ArticlesServiceimpl(ArticlesDao articlesDao) {
//		this.articlesDao = articlesDao;
//	}



	@Override
	public ArticleListVO readAllAricles() {

		
		long count = this.articlesDao.selectArticlesCount();
		List<ArticlesVO> articlesList = this.articlesDao.selectAllArticles();
		
		ArticleListVO list  = new ArticleListVO();
		list.setArticleCount(count);
		list.setArticleList(articlesList);
		
		return list;
	}


	@Override
	public ArticlesVO createNewArticle(RegistArticleVO registArticleVO) {
		
		String fileSetId = this.multipartHandler.storeFiles(registArticleVO.getFile(),
															registArticleVO.getEmail());
		
		registArticleVO.setFileSetId(fileSetId);
		
		int insertedRows = this.articlesDao.inserNewArticle(registArticleVO);
		
		// insert한 게시글의 ID로 게시글 정보를 조회한다.
		// -> insert한 게시글의 ID가 뭔지 모른다.
		
		System.out.println(insertedRows + "개의 row가 생성되었습니다.");
		
		if(insertedRows > 0) {
			return this.articlesDao.selectArticleByArticleId(registArticleVO.getId());
		}
		
		throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
		
	}


	@Override
	public ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO) {
		
		ArticlesVO article = this.articlesDao.selectArticleByArticleId(articleId);
		
		String fileSetId = this.multipartHandler.storeFiles(modifyArticleVO.getFile(),
										 modifyArticleVO.getEmail(),
										 article.getFileSetId());
		
		modifyArticleVO.setFileSetId(fileSetId);
		int updatedRows = this.articlesDao.updateArticle(articleId, modifyArticleVO);
		
		if(updatedRows == 0) {
			throw new IllegalArgumentException("존재하지 않는 게시글 입니다.");
		}
		return this.articlesDao.selectArticleByArticleId(articleId);
	}


	@Override
	public String deleteArticle(String articleId) {
		
		
		//Controller 가 아닌 클래스에서 세션데이터를 자동으로 주입 받을 수 없다.
		//고전적 방법: Controller에서 Service를 호출할때 파라미터로 세션의 데이터를 전달.
		//새로운 방법: Spring에서 Session 데이터를 가져온다. -> RequestContextHolder를 통해서 spring에서 가져올 수 있다 
		ServletRequestAttributes requestAttribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); //request의 전문을 가지고 와라
		HttpServletRequest request = requestAttribute.getRequest(); // 클라이언트가 요청한 정보가 다 나온다.
		HttpSession session = request.getSession();
		
		MembersVO loggedMember = (MembersVO) session.getAttribute("__LOGIN_USER__");
		
		ArticlesVO article = this.articlesDao.selectArticleByArticleId(articleId);
		
		if(!loggedMember.getEmail().equals(article.getEmail())) {
			throw new IllegalArgumentException("삭제할 수 없는 게시글 입니다.");
		}// <- 세션기반의 어플리케이션에서 필수 적으로 작성되는 코드 
		
		int delete = this.articlesDao.deleteArticle(articleId);
		if(delete == 0) {
			throw new IllegalArgumentException("존재하지않는 게시글 입니다");
		}
		
		int deleteCount = this.multipartHandler.deleteFiles(article.getFileSetId());
		
		System.out.println(deleteCount + "개의 파일이 삭제되었습니다.");
		
		return this.articlesDao.deleteArticleByArticleId(articleId);
	}


	@Override
	public ArticlesVO readOneArticle(String articleId) {
		
		int viewCount = this.articlesDao.updateIncreaseView(articleId);
		if(viewCount == 0) {
			throw new IllegalArgumentException("존재하지않는 게시글 입니다.");
		}
		return this.articlesDao.selectArticleByArticleId(articleId);
	}


	@Override
	public long recommendOneArticle(String articleId) {
		int recommendCount = this.articlesDao.updateIncreaseRecommendCount(articleId);
		if(recommendCount == 0) {
			throw new IllegalArgumentException("존재하지않는 게시글 입니다.");
		}
		return this.articlesDao.count(articleId);
	}

}
