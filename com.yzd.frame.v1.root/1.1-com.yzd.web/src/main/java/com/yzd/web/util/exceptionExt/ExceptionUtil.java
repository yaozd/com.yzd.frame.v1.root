package com.yzd.web.util.exceptionExt;

import java.io.PrintWriter;

/***
 * 针对应用程序内部的异常-打印堆栈信息-方法来自dubbo
 */
public class ExceptionUtil {
    //方法取自：dubbo的StringUtils-目前暂时推荐使用此方法
    public static String exceptionToString(Throwable e) {
        UnsafeStringWriter w = new UnsafeStringWriter();
        PrintWriter p = new PrintWriter(w);
        p.print(e.getClass().getName());
        if (e.getMessage() != null) {
            p.print(": " + e.getMessage());
        }
        p.println();
        String var3;
        try {
            e.printStackTrace(p);
            var3 = w.toString();
        } finally {
            p.close();
        }
        return var3;
    }
    //方法取自：dubbo的StringUtils-目前暂时推荐使用此方法
    public static String exceptionToString(String msg, Throwable e) {
        UnsafeStringWriter w = new UnsafeStringWriter();
        w.write(msg + "\n");
        PrintWriter p = new PrintWriter(w);
        String var4;
        try {
            e.printStackTrace(p);
            var4 = w.toString();
        } finally {
            p.close();
        }

        return var4;
    }
}
