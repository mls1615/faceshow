package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImFriendDelete implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String From_Account;//必填 	需要为该Identifier删除好友。
    @JsonProperty
    private String []To_Account;//必填 	待删除的好友的Identifier。 黑名单的Identifier。
    @JsonProperty
    private String DeleteType="Delete_Type_Single";//必填 	删除模式，默认已经单方面删除了不用传递了
    @JsonIgnore
    public String getFrom_Account() {
        return From_Account;
    }
    @JsonIgnore
    public void setFrom_Account(String from_Account) {
        From_Account = from_Account;
    }
    @JsonIgnore
    public String[] getTo_Account() {
        return To_Account;
    }
    @JsonIgnore
    public void setTo_Account(String[] to_Account) {
        To_Account = to_Account;
    }
    @JsonIgnore
    public String getDeleteType() {
        return DeleteType;
    }
    @JsonIgnore
    public void setDeleteType(String deleteType) {
        DeleteType = deleteType;
    }
}
