package weking.lib.game.bean;

import weking.lib.rxretrofit.api.BaseResultEntity;
/**
 * Api Login返回的结果封装
 */
public class StartLiveResult
        extends BaseResultEntity {
    public String barrage_price;//"弹幕模式每条10钻"
    public String reminder;
    private String LD;
    public int total_ticket;
    public int live_id;

    public String getBarrage_price() {
        return this.barrage_price;
    }

    public void setBarrage_price(String barrage_price) {
        this.barrage_price = barrage_price;
    }

    public String getReminder() {
        return this.reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getLive_stream_id() {
        return this.LD;
    }

    public void setLive_stream_id(String live_stream_id) {
        this.LD = live_stream_id;
    }

    public int getTotal_ticket() {
        return this.total_ticket;
    }

    public void setTotal_ticket(int total_ticket) {
        this.total_ticket = total_ticket;
    }

    public int getLive_id() {
        return this.live_id;
    }

    public void setLive_id(int live_id) {
        this.live_id = live_id;
    }

    public class GameBaen {
        private int IA;
        public float RB;

        public GameBaen() {
        }

        public float getRadix() {
            return this.RB;
        }

        public void setRadix(float radix) {
            this.RB = radix;
        }

        public int getId() {
            return this.IA;
        }

        public void setId(int id) {
            this.IA = id;
        }
    }
}


