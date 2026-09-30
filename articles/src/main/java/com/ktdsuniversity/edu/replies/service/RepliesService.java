package com.ktdsuniversity.edu.replies.service;

import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.ReplyListVO;

public interface RepliesService {

	ReplyListVO readAllRepliesByArticleId(String articleId);

	RepliesVO createNewReply(String articleId, RegistRepliesVO registRepliesVO);

	RepliesVO updateReply(String articleId, String replyId, ModifyRepliesVO modifyRepliesVO);

	String deleteReply(String articleId, String replyId);

	long recommendOneReply(String articleId, String replyId);

}
