package com.android.networktools.ping;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class IPTools {
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    private IPTools() {
    }

    public static boolean isIPv4Address(String address) {
        return address != null && IPV4_PATTERN.matcher(address).matches();
    }

    public static boolean isIPv6StdAddress(String address) {
        return address != null && IPV6_STD_PATTERN.matcher(address).matches();
    }

    public static boolean isIPv6HexCompressedAddress(String address) {
        return address != null && IPV6_HEX_COMPRESSED_PATTERN.matcher(address).matches();
    }

    public static boolean isIPv6Address(String address) {
        return address != null && (isIPv6StdAddress(address) || isIPv6HexCompressedAddress(address));
    }

    public static InetAddress getLocalIPv4Address() {
        ArrayList<InetAddress> localAddresses = getLocalIPv4Addresses();
        return localAddresses.size() > 0 ? (InetAddress)localAddresses.get(0) : null;
    }

    public static ArrayList<InetAddress> getLocalIPv4Addresses() {
        ArrayList foundAddresses = new ArrayList();

        try {
            Enumeration ifaces = NetworkInterface.getNetworkInterfaces();

            while(ifaces.hasMoreElements()) {
                NetworkInterface iface = (NetworkInterface)ifaces.nextElement();
                Enumeration addresses = iface.getInetAddresses();

                while(addresses.hasMoreElements()) {
                    InetAddress addr = (InetAddress)addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        foundAddresses.add(addr);
                    }
                }
            }
        } catch (SocketException var5) {
            var5.printStackTrace();
        }

        return foundAddresses;
    }

    public static boolean isIpAddressLocalhost(InetAddress addr) {
        if (addr == null) {
            return false;
        } else if (!addr.isAnyLocalAddress() && !addr.isLoopbackAddress()) {
            try {
                return NetworkInterface.getByInetAddress(addr) != null;
            } catch (SocketException var2) {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isIpAddressLocalNetwork(InetAddress addr) {
        return addr != null && addr.isSiteLocalAddress();
    }
}
