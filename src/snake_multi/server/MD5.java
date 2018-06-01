/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi.server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author TranCamTu
 */
public class MD5
{
    public static String encrypt(String str)
    {
        MessageDigest message;

        try
        {
            message = MessageDigest.getInstance("MD5");
            message.update(str.getBytes(), 0, str.length());
            return new BigInteger(1, message.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException ex)
        {
            System.out.println(ex.getCause());
            return null;
        }
    }
}
