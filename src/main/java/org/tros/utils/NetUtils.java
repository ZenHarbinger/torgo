/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.tros.utils.logging.Logging;

/**
 * Provides to net level functionality.
 *
 * @author matta
 */
public final class NetUtils {

    private static InetAddress ip;
    private static InetAddress[] ips;

    /**
     * Static Constructor.
     */
    static {
        try {
            ip = getIP();
        } catch (SocketException ex) {
            Logging.getLogFactory().getLogger(NetUtils.class).warn(null, ex);
        }
    }

    /**
     * Hidden Constructor.
     */
    @CoverageIgnore
    private NetUtils() {
    }

    /**
     * Get an IP.
     *
     * @return the first IP address available.
     * @throws SocketException on network error.
     */
    public static InetAddress getIP() throws SocketException {
        if (ip != null) {
            return ip;
        }

        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        NetworkInterface ni;
        while (nis.hasMoreElements()) {
            ni = nis.nextElement();
            if (!ni.isLoopback()/*not loopback*/ && ni.isUp()/*it works now*/) {
                for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                    //filter for ipv4/ipv6
                    if (ia.getAddress().getAddress().length == 4) {
                        //4 for ipv4, 16 for ipv6
                        ip = ia.getAddress();
                        break;
                    }
                }
            }
        }
        return ip;
    }

    /**
     * Get all IPs.
     *
     * @return all IP addresses available.
     * @throws SocketException on network error.
     */
    public static InetAddress[] getIPs() throws SocketException {
        if (ips != null) {
            return ips;
        }

        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        NetworkInterface ni;
        List<InetAddress> ret = new ArrayList<>();
        while (nis.hasMoreElements()) {
            ni = nis.nextElement();
            if (!ni.isLoopback()/*not loopback*/ && ni.isUp()/*it works now*/) {
                for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                    //filter for ipv4/ipv6
                    if (ia.getAddress().getAddress().length == 4) {
                        //4 for ipv4, 16 for ipv6
                        ip = ia.getAddress();
                        ret.add(ip);
//                        return _ip;
                    }
                }
            }
        }
        ips = ret.toArray(new InetAddress[]{});
        return ips;
    }
}
