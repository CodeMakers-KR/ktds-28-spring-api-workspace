package com.ktdsuniversity.edu.replies.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController // (@Controller + @ReponseBody) 메소드에 @ResponseBody 생략 가능.
@AllArgsConstructor
public class RepliesController {

	private RepliesService repliesService;

	@GetMapping("/articles/{articleId}/replies")
	public ApiResponse<ReplyListVO> getReplies(@PathVariable String articleId) {
		try {
			return ApiResponse.OK(this.repliesService.readAllRepliesByArticleId(articleId));
		} catch (IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}
	
	@PostMapping("/articles/{articleId}/replies")
	public ApiResponse<RepliesVO> makeNewReply(
			@PathVariable String articleId, 
			@Valid @ModelAttribute RegistRepliesVO registRepliesVO,
			BindingResult validationResult) {
		
		if (validationResult.hasErrors()) {
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		}
		
		try {
			return ApiResponse.OK(this.repliesService.createNewReply(articleId, registRepliesVO));
		} catch (IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}

	@PutMapping("/articles/{articleId}/replies/{replyId}")
	public ApiResponse<RepliesVO> updateReply(
			@PathVariable String articleId, 
			@PathVariable String replyId,
			@Valid @ModelAttribute ModifyRepliesVO modifyRepliesVO,
			BindingResult validationResult) {
		
		if (validationResult.hasErrors()) {
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		}
		
		try {
			return ApiResponse.OK(this.repliesService.updateReply(articleId, replyId, modifyRepliesVO));
		} catch (IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}

	@DeleteMapping("/articles/{articleId}/replies/{replyId}")
	public ApiResponse<String> deleteReply(@PathVariable String articleId, @PathVariable String replyId) {
		try {
			return ApiResponse.OK(this.repliesService.deleteReply(articleId, replyId));
		} catch (IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}

	@PutMapping("/articles/{articleId}/replies/recommend/{replyId}")
	public ApiResponse<Long> recommendOneReply(@PathVariable String articleId, @PathVariable String replyId) {
		try {
			return ApiResponse.OK(this.repliesService.recommendOneReply(articleId, replyId));
		} catch (IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}
}
