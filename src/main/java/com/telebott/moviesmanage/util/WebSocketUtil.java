package com.telebott.moviesmanage.util;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketUtil {
    public static final int login = 100;
    public static final int login_success = 101;
    public static final int login_fail = 102;
    public static final int message_kefu_sending = 103;
    public static final int message_kefu_send_success = 104;
    public static final int message_kefu_send_fail = 105;
    public static final int message_kefu_recevie = 106;
    public static final int user_change = 107;
    public static final int user_change_fail = 108;
    public static final int user_change_success = 109;
    public static final int user_change_passwoed = 110;
    public static final int user_change_passwoed_fail = 111;
    public static final int user_change_passwoed_success = 112;
    public static InetSocketAddress getRemoteAddress(Session session) {
        if (session == null) {
            return null;
        }
        Async async = session.getAsyncRemote();

        //在Tomcat 8.0.x版本有效
//		InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async,"base#sos#socketWrapper#socket#sc#remoteAddress");
        //在Tomcat 8.5以上版本有效
        InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async,"base#socketWrapper#socket#sc#remoteAddress");
        return addr;
    }

    private static Object getFieldInstance(Object obj, String fieldPath) {
        String fields[] = fieldPath.split("#");
        for (String field : fields) {
            obj = getField(obj, obj.getClass(), field);
            if (obj == null) {
                return null;
            }
        }

        return obj;
    }

    private static Object getField(Object obj, Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field;
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e) {
            }
        }

        return null;
    }
}
