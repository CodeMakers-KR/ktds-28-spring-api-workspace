package com.ktdsuniversity.edu.replies.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import com.ktdsuniversity.edu.replies.service.RepliesService;
import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.ReplyListVO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController // (@Controller + @ReponseBody) 메소드에 @ResponseBody 생략 가능.
@AllArgsConstructor
public class RepliesController {

	private RepliesService repliesService;

	@GetMapping("/articles/{articleId}/replies/list")
	public ApiResponse<ReplyListVO> getReplies(
			@Pattern(regexp = "^AR-[0-9]{8}-[0-9]{6,8}$", message="잘못된 요청입니다.") 
			@PathVariable String articleId) {
		return ApiResponse.OK(this.repliesService.readAllRepliesByArticleId(articleId));
	}
	
	@PostMapping("/articles/{articleId}/replies")
	public ApiResponse<RepliesVO> makeNewReply(
			@Pattern(regexp = "^AR-[0-9]{8}-[0-9]{6,8}$", message="잘못된 요청입니다.")
			@PathVariable String articleId, 
			@Valid @ModelAttribute RegistRepliesVO registRepliesVO,
			BindingResult validationResult,
			// HttpSession에 등록된 __LOGIN_USER__ 에 있는 MembersVO를 파라미터로 받아와라!
			@SessionAttribute("__LOGIN_USER__") MembersVO membersVO) {
		
		if (validationResult.hasErrors()) {
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		}
		
		registRepliesVO.setEmail( membersVO.getEmail() );
		
		return ApiResponse.OK(this.repliesService.createNewReply(articleId, registRepliesVO));
	}

	@PutMapping("/articles/{articleId}/replies/{replyId}")
	public ApiResponse<RepliesVO> updateReply(
			@Pattern(regexp = "^AR-[0-9]{8}-[0-9]{6,8}$", message="잘못된 요청입니다.")
			@PathVariable String articleId, 
			@Pattern(regexp = "^RP-[0-9]{8}-[0-9]{6,8}$", message="잘못된 요청입니다.")
			@PathVariable String replyId,
			@Valid @ModelAttribute ModifyRepliesVO modifyRepliesVO,
			BindingResult validationResult,
			// HttpSession에 등록된 __LOGIN_USER__ 에 있는 MembersVO를 파라미터로 받아와라!
			@SessionAttribute("__LOGIN_USER__") MembersVO membersVO) {
		
		if (validationResult.hasErrors()) {
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		}
		
		modifyRepliesVO.setEmail( membersVO.getEmail() );
		
		return ApiResponse.OK(this.repliesService.updateReply(articleId, replyId, modifyRepliesVO));
	}

	@DeleteMapping("/articles/{articleId}/replies/{replyId}")
	public ApiResponse<String> deleteReply(
			@Pattern(regexp = "^AR-[0-9]{8}-[0-9]{6,8}$", message="잘못된 요청입니다.")
			@PathVariable String articleId, 
			@Pattern(regexp = "^RP-[0-9]{8}-[0-9]{6,8}$", message="잘못된 요청입니다.")
			@PathVariable String replyId) {
		return ApiResponse.OK(this.repliesService.deleteReply(articleId, replyId));
	}

	@PutMapping("/articles/{articleId}/replies/recommend/{replyId}")
	public ApiResponse<Long> recommendOneReply(
			@Pattern(regexp = "^AR-[0-9]{8}-[0-9]{6,8}$", message="잘못된 요청입니다.")
			@PathVariable String articleId, 
			@Pattern(regexp = "^RP-[0-9]{8}-[0-9]{6,8}$", message="잘못된 요청입니다.")
			@PathVariable String replyId) {
		return ApiResponse.OK(this.repliesService.recommendOneReply(articleId, replyId));
	}
}
