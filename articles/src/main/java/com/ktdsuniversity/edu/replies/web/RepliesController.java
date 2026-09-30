package com.ktdsuniversity.edu.replies.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.replies.service.RepliesService;
import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.ReplyListVO;

import lombok.AllArgsConstructor;

@RestController // (@Controller + @ReponseBody) 메소드에 @ResponseBody 생략 가능.
@AllArgsConstructor
public class RepliesController {

	private RepliesService repliesService;
	
	// GET /replies/{게시글아이디} 
	// 게시글에 등록된 댓글을 반환.
	@GetMapping("/replies/{articleId}")
	public ApiResponse<ReplyListVO> getReplies(@PathVariable String articleId) {
		return ApiResponse.OK(this.repliesService.readAllRepliesByArticleId(articleId));
	}
	
	// POST /replies/{게시글아이디}
	// 게시글에 댓글 작성 (파일 첨부 가능)
	@PostMapping("/replies/{articleId}")
	public ApiResponse<RepliesVO> makeNewReply(
				@PathVariable String articleId,
				RegistRepliesVO registRepliesVO) {
		return ApiResponse.OK(this.repliesService.createNewReply(articleId, registRepliesVO));
	}
	
	// PUT /replies/{게시글아이디}/{댓글아이디}
	// 게시글에 등록된 댓글을 수정 (파일 첨부 가능)
	@PutMapping("/replies/{articleId}/{replyId}")
	public ApiResponse<RepliesVO> updateReply(
				@PathVariable String articleId,
				@PathVariable String replyId,
				ModifyRepliesVO modifyRepliesVO) {
		return ApiResponse.OK(this.repliesService.updateReply(articleId, replyId, modifyRepliesVO));
	}
	
	// DELETE /replies/{게시글아이디}/{댓글아이디}
	// 게시글에 등록된 댓글 하나를 삭제
	// 첨부된 파일 제거
	@DeleteMapping("/replies/{articleId}/{replyId}")
	public ApiResponse<String> deleteReply(
				@PathVariable String articleId,
				@PathVariable String replyId) {
		return ApiResponse.OK(this.repliesService.deleteReply(articleId, replyId));
	}
	
	// PUT /replies/{게시글아이디}/recommend/{댓글아이디}
	// 게시글에 등록된 댓글 하나를 추천.
	@PutMapping("/replies/{articleId}/recommend/{replyId}")
	public ApiResponse<Long> recommendOneReply(
				@PathVariable String articleId,
				@PathVariable String replyId) {
		return ApiResponse.OK(this.repliesService.recommendOneReply(articleId, replyId));
	}
}
