package com.faceshow.modules.video.entity;

import java.io.Serializable;
import java.util.Date;

public class VideoCommentReply implements Serializable {
    /**
     * 视频评论ID主键
     */
    private Integer replyId;

    /**
     * 视频评论ID主键
     */
    private Integer commentId;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 评论人
     */
    private Integer userId;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 评论时间时间
     */
    private Date createTime;

    /**
     * 是否审核 0 未审核 1 已审核
     */
    private Byte statues;

    private static final long serialVersionUID = 1L;

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getStatues() {
        return statues;
    }

    public void setStatues(Byte statues) {
        this.statues = statues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", replyId=").append(replyId);
        sb.append(", commentId=").append(commentId);
        sb.append(", comment=").append(comment);
        sb.append(", userId=").append(userId);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", statues=").append(statues);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}