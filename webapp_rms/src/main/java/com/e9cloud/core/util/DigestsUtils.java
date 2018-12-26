package com.e9cloud.core.util;

import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.UserAdmin;

/**
 * 加密工具类
 */
public class DigestsUtils {

    private static final int SALT_SIZE = 8;
    private static final int HASH_INTERATIONS = 1024;

    /**
     * 得到8位私盐
     * @return salt
     */
    public static String getSalt(){
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        return Encodes.encodeHex(salt);
    }

    /**
     * 对密码进行sha散列运算
     * @return shaPassword 散列后的密码
     */
    public static String encryptionPassword(String password, String salt){
        byte[] shaPassword = Digests.sha1(password, Encodes.decodeHex(salt), HASH_INTERATIONS);
        return Encodes.encodeHex(shaPassword);
    }

    /**
     * 密码加密。
     *
     * @param user
     *            要加密的用户。
     * @return 返回加密后的用户对象。
     */
    public static void encryption(UserAdmin user) throws Exception {

		/* 对用户密码进行加密 */
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));
        byte[] shaPassword = Digests.sha1(user.getPwd(), salt, HASH_INTERATIONS);
        user.setPwd(Encodes.encodeHex(shaPassword));
    }

    /**
     * 校验密码是否正确。
     *
     * @param user
     *            要加密的用户。
     * @param pwd
     *            密码
     * @return 返回加密后的用户对象。
     */
    public static boolean checkpwd(UserAdmin user, String pwd) {

		/* 对用户密码进行加密 */
        byte[] shaPassword = Digests.sha1(pwd, Encodes.decodeHex(user.getSalt()), HASH_INTERATIONS);
        return user.getPwd().equals((Encodes.encodeHex(shaPassword)));
    }
}
