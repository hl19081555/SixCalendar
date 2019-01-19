package com.example.android.sixcalendar.entries;

import java.util.Arrays;

/**
 * Created by jackie on 2019/1/19.
 */

public class ChatbotDingding {
    public enum MESSAGE_TYPE{text, link, markdown, actionCard, links}

    private MESSAGE_TYPE msgtype; // 类型
    private CText text; // 针对 msgtype 是 TEXT
    private CAt at; // 针对 msgtype 是 TEXT
    private CLink link; // 针对 msgtype 是 link
    private CMarkdown markdown; // 针对 msgtype 是 markdown
    private CActionCard actionCard; // 针对 msgtype 是 actionCard
    private CFeedCard links; // 针对 msgtype 是 feedCard

    public MESSAGE_TYPE getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(MESSAGE_TYPE msgtype) {
        this.msgtype = msgtype;
        switch (msgtype) {
            case text:
                text = new CText();
                break;
            case link:
                link = new CLink();
                break;
            case markdown:
                markdown = new CMarkdown();
                break;
            case actionCard:
                actionCard = new CActionCard();
                break;
            case links:
                links = new CFeedCard();
                break;
        }
    }

    public CText getText() {
        return text;
    }

    public CAt getAt() {
        return at;
    }

    public CLink getLink() {
        return link;
    }

    public CMarkdown getMarkdown() {
        return markdown;
    }

    public CActionCard getActionCard() {
        return actionCard;
    }

    public CFeedCard getFeedCard() {
        return links;
    }

    @Override
    public String toString() {
        return "ChatbotDingding{" +
                "msgtype=" + msgtype +
                ", text=" + text +
                ", at=" + at +
                ", link=" + link +
                ", markdown=" + markdown +
                ", actionCard=" + actionCard +
                ", links=" + links +
                '}';
    }

    public class CText {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "CText{" +
                    "content='" + content + '\'' +
                    '}';
        }
    }

    public class CAt {
        private String[] atMobiles;
        private boolean isAtAll;

        public String[] getAtMobiles() {
            return atMobiles;
        }

        public void setAtMobiles(String[] atMobiles) {
            this.atMobiles = atMobiles;
        }

        public boolean isAtAll() {
            return isAtAll;
        }

        public void setAtAll(boolean atAll) {
            isAtAll = atAll;
        }

        @Override
        public String toString() {
            return "CAt{" +
                    "atMobiles=" + Arrays.toString(atMobiles) +
                    ", isAtAll=" + isAtAll +
                    '}';
        }
    }

    public class CLink {
        private String text;
        private String title;
        private String picUrl;
        private String messageUrl;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getMessageUrl() {
            return messageUrl;
        }

        public void setMessageUrl(String messageUrl) {
            this.messageUrl = messageUrl;
        }

        @Override
        public String toString() {
            return "CLink{" +
                    "text='" + text + '\'' +
                    ", title='" + title + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", messageUrl='" + messageUrl + '\'' +
                    '}';
        }
    }

    public class CMarkdown {
        private String text;
        private String title;
        private String picUrl;
        private String messageUrl;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getMessageUrl() {
            return messageUrl;
        }

        public void setMessageUrl(String messageUrl) {
            this.messageUrl = messageUrl;
        }

        @Override
        public String toString() {
            return "CMarkdown{" +
                    "text='" + text + '\'' +
                    ", title='" + title + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", messageUrl='" + messageUrl + '\'' +
                    '}';
        }
    }

    public class CActionCard {
        private String title;
        private String text;
        private String hideAvatar;
        private String btnOrientation;
        private String singleTitle;
        private String singleURL;
        private CBtn[] btns;

        private String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getHideAvatar() {
            return hideAvatar;
        }

        public void setHideAvatar(String hideAvatar) {
            this.hideAvatar = hideAvatar;
        }

        public String getBtnOrientation() {
            return btnOrientation;
        }

        public void setBtnOrientation(String btnOrientation) {
            this.btnOrientation = btnOrientation;
        }

        public String getSingleTitle() {
            return singleTitle;
        }

        public void setSingleTitle(String singleTitle) {
            this.singleTitle = singleTitle;
        }

        public String getSingleURL() {
            return singleURL;
        }

        public void setSingleURL(String singleURL) {
            this.singleURL = singleURL;
        }

        public CBtn[] getBtns(int count) {
            if (count <= 0) return btns;
            if (btns != null && count != btns.length) {
                btns = new CBtn[count];
            }
            return btns;
        }

        @Override
        public String toString() {
            return "CActionCard{" +
                    "title='" + title + '\'' +
                    ", text='" + text + '\'' +
                    ", hideAvatar='" + hideAvatar + '\'' +
                    ", btnOrientation='" + btnOrientation + '\'' +
                    ", singleTitle='" + singleTitle + '\'' +
                    ", singleURL='" + singleURL + '\'' +
                    ", btns=" + Arrays.toString(btns) +
                    '}';
        }
    }

    public class CBtn {
        private String title;
        private String actionURL;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActionURL() {
            return actionURL;
        }

        public void setActionURL(String actionURL) {
            this.actionURL = actionURL;
        }

        @Override
        public String toString() {
            return "CBtn{" +
                    "title='" + title + '\'' +
                    ", actionURL='" + actionURL + '\'' +
                    '}';
        }
    }

    public class CFeedCard {
        private String title;
        private String messageURL;
        private String picURL;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessageURL() {
            return messageURL;
        }

        public void setMessageURL(String messageURL) {
            this.messageURL = messageURL;
        }

        public String getPicURL() {
            return picURL;
        }

        public void setPicURL(String picURL) {
            this.picURL = picURL;
        }

        @Override
        public String toString() {
            return "CFeedCard{" +
                    "title='" + title + '\'' +
                    ", messageURL='" + messageURL + '\'' +
                    ", picURL='" + picURL + '\'' +
                    '}';
        }
    }
}
