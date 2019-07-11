package weking.lib.game.bean;

import java.util.List;

/**
 * 创建时间 2017/2/27.
 * 创建人 zhengb
 * 功能描述 进入房间，返回数据
 * 继承EndLiveResult：若进入结束的直播间则返回的是EndLiveResult
 */

public class EnterRoomResult extends EndLiveResult {
    // 自用，非api属性
    public String anchor_account; //主播的account
    public String authorHeadLowUrl;

    public String live_stream_id;
    // 自用，非api属性

    //**********************************//
//    public int live_id;           // 父类已经定义好-自用非api属性
//    public String audience_num;   // 父类已经定义好
    //**********************************//

    public String link_stream_id; // 连麦的流id
    public String link_account;     // 连麦者的account
    public int follow_state;
    public String banner_url;
    public int level;
    public boolean is_manager;
    public String banner_image_url;
    public String pic_head_low;
    public List<RoomPeopleItem> people_list;
    public String reminder;         // 禁黄等提醒
    public int total_ticket;
    public boolean is_banned; // 用户是否被禁言
    private String barrage_price;//"弹幕模式每条10钻",
    // 进场特效，0普同
    public int effect;
    public String anchor_nickname ; // 主播名称

   /* private int GC;  // 游戏的type

    public int getGC() {
        return GC;
    }

    public void setGC(int GC) {
        this.GC = GC;
    }*/

    public String getAnchor_nickname() {
        return anchor_nickname;
    }

    public void setAnchor_nickname(String anchor_nickname) {
        this.anchor_nickname = anchor_nickname;
    }

    public String getPic_head_low() {
        return pic_head_low;
    }

    public void setPic_head_low(String pic_head_low) {
        this.pic_head_low = pic_head_low;
    }

    public String getAnchor_account() {
        return anchor_account;
    }

    public void setAnchor_account(String anchor_account) {
        this.anchor_account = anchor_account;
    }

    public String getAuthorHeadLowUrl() {
        return authorHeadLowUrl;
    }

    public void setAuthorHeadLowUrl(String authorHeadLowUrl) {
        this.authorHeadLowUrl = authorHeadLowUrl;
    }

    public String getLive_stream_id() {
        return live_stream_id;
    }

    public void setLive_stream_id(String live_stream_id) {
        this.live_stream_id = live_stream_id;
    }

    public String getLink_stream_id() {
        return link_stream_id;
    }

    public void setLink_stream_id(String link_stream_id) {
        this.link_stream_id = link_stream_id;
    }

    public String getLink_account() {
        return link_account;
    }

    public void setLink_account(String link_account) {
        this.link_account = link_account;
    }

    public int getFollow_state() {
        return follow_state;
    }

    public void setFollow_state(int follow_state) {
        this.follow_state = follow_state;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean is_manager() {
        return is_manager;
    }

    public void setIs_manager(boolean is_manager) {
        this.is_manager = is_manager;
    }

    public String getBanner_image_url() {
        return banner_image_url;
    }

    public void setBanner_image_url(String banner_image_url) {
        this.banner_image_url = banner_image_url;
    }

    public List<RoomPeopleItem> getPeople_list() {
        return people_list;
    }

    public void setPeople_list(List<RoomPeopleItem> people_list) {
        this.people_list = people_list;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public int getTotal_ticket() {
        return total_ticket;
    }

    public void setTotal_ticket(int total_ticket) {
        this.total_ticket = total_ticket;
    }

    public boolean is_banned() {
        return is_banned;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public String getBarrage_price() {
        return barrage_price;
    }

    public void setBarrage_price(String barrage_price) {
        this.barrage_price = barrage_price;
    }


    public static class RoomPeopleItem {
        private int LA;
        private String AA;
        private String PB;


        public int getLevel() {
            return LA;
        }

        public void setLevel(int level) {
            this.LA = level;
        }

        public String getAccount() {
            return AA;
        }

        public void setAccount(String account) {
            this.AA = account;
        }

        public String getPic_head_low() {
            return PB;
        }

        public void setPic_head_low(String pic_head_low) {
            this.PB = pic_head_low;
        }
    }
}
