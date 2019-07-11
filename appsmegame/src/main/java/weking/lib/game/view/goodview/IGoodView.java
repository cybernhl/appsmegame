package weking.lib.game.view.goodview;/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Color;

/**
 * @author wk
 */
public interface IGoodView {

  int DISTANCE = 120;   // 默认移动距离

  int FROM_Y_DELTA = 0; // Y轴移动起始偏移量

  int TO_Y_DELTA = DISTANCE; // Y轴移动最终偏移量

  float FROM_ALPHA = 1.0f;    // 起始时透明度

  float TO_ALPHA = 0.2f;  // 结束时透明度

  int DURATION = 1200; // 动画时长

  String TEXT = ""; // 默认文本

  int TEXT_SIZE = 20; // 默认文本字体大小

  int TEXT_COLOR = Color.RED;   // 默认文本字体颜色
//    int TEXT_COLOR = R.color.theme_color;   // 默认文本字体颜色

}
