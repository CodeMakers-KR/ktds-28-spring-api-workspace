package com.ktdsuniversity.edu.replies.web;

import org.springframework.web.bind.annotation.RestController;

@RestController // (@Controller + @ReponseBody) 메소드에 @ResponseBody 생략 가능.
public class RepliesController {

	// GET /replies/{게시글아이디} 
	// 게시글에 등록된 댓글을 반환.
	
	// POST /replies/{게시글아이디}
	// 게시글에 댓글 작성 (파일 첨부 가능)
	
	// PUT /replies/{게시글아이디}/{댓글아이디}
	// 게시글에 등록된 댓글을 수정 (파일 첨부 가능)
	
	// DELETE /replies/{게시글아이디}/{댓글아이디}
	// 게시글에 등록된 댓글 하나를 삭제
	// 첨부된 파일 제거
	
	// PUT /replies/{게시글아이디}/recommend/{댓글아이디}
	// 게시글에 등록된 댓글 하나를 추천.
}
