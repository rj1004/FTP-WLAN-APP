package com.example.ftpwlan;

import android.util.Log;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public static String getDeviceIpAddress( ) {
        String deviceIpAddress = "###.###.###.###";

        try {
            for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();) {
                NetworkInterface networkInterface = enumeration.nextElement();

                for (Enumeration<InetAddress> enumerationIpAddr = networkInterface.getInetAddresses(); enumerationIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumerationIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress() && inetAddress.getAddress().length == 4)
                    {
                        deviceIpAddress = inetAddress.getHostAddress();

                        Log.e("hi", "deviceIpAddress: " + deviceIpAddress);
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("hi", "SocketException:" + e.getMessage());
        }

        return deviceIpAddress;
    }
}