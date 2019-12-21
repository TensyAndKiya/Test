package com.clei.Y2019.M12.D15;

import com.clei.utils.FileUtil;

/**
 * 项目里前人用了太多了的%${keyword}%
 * 有SQL注入的风险
 * 一个个的改太难受了。。
 * @author KIyA
 * @since 2019-12-15
 */
public class LineStrReplaceTest {
    public static void main(String[] args) throws Exception {
        FileUtil.rewriteStr("C:\\Users\\super\\Desktop\\待办任务",".xml","'%${","%}");
    }
}
