package weking.lib.game.bean;

import weking.lib.rxretrofit.api.BaseResultEntity;
/**
 * Api Adv返回的结果封装
 */
public class SendGiftResult
        extends BaseResultEntity {
    // code=2003,余额不足，code=2004，发送礼物失败
    public long send_time;
    public int my_diamonds;
    public int anchor_tickets;
    public int gift_id;
}

