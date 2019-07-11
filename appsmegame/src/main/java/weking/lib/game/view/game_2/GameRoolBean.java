package weking.lib.game.view.game_2;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 创建时间 2017/6/15.
 * 创建人 frs
 * 功能描述  抓娃娃游戏 的数据
 */
public class GameRoolBean {
    public ImageView BG;  //礼物图片
    public ImageView GIFT;  //礼物图片
    public ImageView HEAD;  //礼物图片
    public TextView TV;  //礼物金额
    public View VIEW;//item对象
    public int position;//位置


    //打飞机
    public float boundWidth =80;  //随机位子
    public ObjectAnimator animator;  //随机位子
    public int id;    //怪物id
    public float radix;    //怪物倍数
    public int res;  // 怪物本地图片
    public int res_border;  // 怪物本地图片<点击一闪>

}
