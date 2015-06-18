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

/**
 *
 * @author matta
 */
public class NetUtils {

    private static InetAddress _ip;
    private static InetAddress[] _ips;

    static {
        try {
            _ip = getIP();
        } catch (SocketException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(NetUtils.class).warn(null, ex);
        }
    }

    private NetUtils() {
    }

    public static InetAddress getIP() throws SocketException {
        if (_ip != null) {
            return _ip;
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
                        _ip = ia.getAddress();
                        return _ip;
                    }
                }
            }
        }
        return null;
    }

    public static InetAddress[] getIPs() throws SocketException {
        if (_ips != null) {
            return _ips;
        }

        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        NetworkInterface ni;
        ArrayList<InetAddress> ret = new ArrayList<InetAddress>();
        while (nis.hasMoreElements()) {
            ni = nis.nextElement();
            if (!ni.isLoopback()/*not loopback*/ && ni.isUp()/*it works now*/) {
                for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                    //filter for ipv4/ipv6
                    if (ia.getAddress().getAddress().length == 4) {
                        //4 for ipv4, 16 for ipv6
                        _ip = ia.getAddress();
                        ret.add(_ip);
//                        return _ip;
                    }
                }
            }
        }
        _ips = ret.toArray(new InetAddress[]{});
        return _ips;
    }
}
