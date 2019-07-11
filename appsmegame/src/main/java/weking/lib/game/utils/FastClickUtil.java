package weking.lib.game.utils;

public class FastClickUtil {
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * 判断是否在规定时间内调用
     *
     * @param between 两次之间的毫秒数
     * @return
     */
    public synchronized static boolean isFastClick(int between) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < between) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    private static long lastSendTime;

    public synchronized static boolean isFastSend(int between) {
        long time = System.currentTimeMillis();
        if (time - lastSendTime < between) {
            return true;
        }
        lastSendTime = time;
        return false;
    }

    // 获取热门直播列表专用
    private static long lastGetHotLiveList;

    public synchronized static boolean isFastGetHotLive(int between) {
        long time = System.currentTimeMillis();
        if (time - lastGetHotLiveList < between) {
            return true;
        }
        lastGetHotLiveList = time;
        return false;
    }

    // 获取跟踪直播列表专用
    private static long lastGetFollowLiveList;

    public synchronized static boolean isFastGetFollowLive(int between) {
        long time = System.currentTimeMillis();
        if (time - lastGetFollowLiveList < between) {
            return true;
        }
        lastGetFollowLiveList = time;
        return false;
    }

    // 获取最新直播列表专用
    private static long lastGetNewLiveList;

    public synchronized static boolean isFastGetNewLive(int between) {
        long time = System.currentTimeMillis();
        if (time - lastGetNewLiveList < between) {
            return true;
        }
        lastGetNewLiveList = time;
        return false;
    }
    // 获取附近直播列表专用
    private static long lastGetNearbyLiveList;

    public synchronized static boolean isFastGetNearbyLive(int between) {
        long time = System.currentTimeMillis();
        if (time - lastGetNearbyLiveList < between) {
            return true;
        }
        lastGetNearbyLiveList = time;
        return false;
    }

    // 获取跟踪主播列表专用
    private static long lastGetPublishLiveList;

    public static boolean isFastGetPublishLive(int between) {
        long time = System.currentTimeMillis();
        if (time - lastGetPublishLiveList < between) {
            return true;
        }
        lastGetPublishLiveList = time;
        return false;
    }

    // 获取跟踪活动列表专用
    private static long lastGetPartyLiveList;

    public static boolean isFastGetPartyLive(int between) {
        long time = System.currentTimeMillis();
        if (time - lastGetPartyLiveList < between) {
            return true;
        }
        lastGetPartyLiveList = time;
        return false;

    }
    // 获取跟踪活动列表专用
    private static long lastGetGeTuiOnLine;

    public static boolean isFastGeTuiOnLine(int between) {
        long time = System.currentTimeMillis();
        if (time - lastGetGeTuiOnLine < between) {
            return true;
        }
        lastGetGeTuiOnLine = time;
        return false;
    }

    public static void setLastGetFollowLiveList(long lastGetFollowLiveList) {
        FastClickUtil.lastGetFollowLiveList = lastGetFollowLiveList;
    }

    public static void setLastGetHotLiveList(long lastGetHotLiveList) {
        FastClickUtil.lastGetHotLiveList = lastGetHotLiveList;
    }

    public static void clearLastTime() {
        lastGetFollowLiveList=0;
        lastGetHotLiveList=0;
        lastGetPartyLiveList=0;
        lastGetPublishLiveList=0;
    }
}
