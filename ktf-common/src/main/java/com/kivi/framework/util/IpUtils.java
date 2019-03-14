/**
 * 通用工具类
 */
package com.kivi.framework.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能:通用的静态工具函数
 * <p>
 * Description: 通用工具类
 * </p>
 */
@Slf4j
public class IpUtils {

    public static String geLocaltHostIp() {
        return geLocaltHostIp(null);
    }

    public static String geLocaltHostIp(String prefix) {
        String ip = "";
        List<String> ips = new ArrayList<String>();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() &&
                            inetAddress.isSiteLocalAddress()) {
                        String local_ip = inetAddress.getHostAddress().toString();
                        if (!"127.0.0.1".equals(local_ip)) {
                            if (prefix != null && !local_ip.startsWith(prefix)) {
                                continue;
                            }
                            ips.add(local_ip);
                        }

                    }

                }
            }

        }
        catch (SocketException ex) {
            log.error("获取IP异常", ex);
        }
        if (ips.isEmpty()) {
            InetAddress netAddress = getInetAddress();
            if (null == netAddress) {
                return null;
            }

            ip = netAddress.getHostAddress();
        }
        else {
            ip = ips.get(0);
        }

        return ip;
    }

    public static String getLocalHostName() {
        InetAddress netAddress = getInetAddress();
        if (null == netAddress) {
            return null;
        }
        String name = netAddress.getHostName(); // get the host address
        return name;
    }

    private static InetAddress getInetAddress() {

        try {
            return InetAddress.getLocalHost();
        }
        catch (UnknownHostException e) {
            log.error("unknown host!", e);
        }
        return null;

    }

}
