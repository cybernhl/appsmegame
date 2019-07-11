package weking.lib.game.bean;

/**
 * 推送的庄家上庄信息
 * <p>
 * Created by zhengb on 2016/9/2.
 */
public class NextBenkerPush extends BaseGamePush {
    /**
     * LB : 23708
     * BG : 文艺范巡水的鱼
     * BH : avatar/20170925/t2uiwif1dbqli2pu.jpeg
     * BI : 100
     * MB : 文艺范巡水的鱼成功上庄
     * <p>
     * //    public static final String im_code = "IC";
     * //    public static final String live_id = "LB";
     * //    public static final String banker_nickname = "BG";
     * //    public static final String banker_header = "BH";
     * //    public static final String banker_diamond = "BI";
     * //    public static final String msg = "MB";
     */

    // liveId
    private int LB;
    //庄家的名称
    private String BG;
    //庄家的头像
    private String BH;
    //庄家的Account
    private String BJ;

    private int BI;

    private String MB;

    public String getBanker_account() {
        return BJ;
    }

    public void setAccount(String banker_account) {
        this.BJ = banker_account;
    }

    public int getLive_id() {
        return LB;
    }

    public void setLive_id(int llive_id) {
        this.LB = llive_id;
    }

    public String getBanker_nickname() {
        return BG;
    }

    public void setBanker_nickname(String banker_nickname) {
        this.BG = banker_nickname;
    }

    public String getBanker_header() {
        return BH;
    }

    public void setBanker_header(String banker_header) {
        this.BH = banker_header;
    }

    public int getBanker_diamond() {
        return BI;
    }

    public void setBanker_diamond(int banker_diamond) {
        this.BI = BI;
    }

    public String getBanekrMsg() {
        return MB;
    }
}
