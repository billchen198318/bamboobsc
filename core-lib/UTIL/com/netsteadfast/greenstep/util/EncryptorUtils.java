/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.netsteadfast.greenstep.base.Constants;

/**
 * from network resource
 * http://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt-example
 *
 */
public class EncryptorUtils {
	
	public static String encrypt(String key1, String iv1, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(iv1.getBytes(Constants.BASE_ENCODING));
            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes(Constants.BASE_ENCODING), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            //System.out.println("encrypted string:" + Base64.encodeBase64String(encrypted));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String key1, String iv1, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(iv1.getBytes(Constants.BASE_ENCODING));
            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes(Constants.BASE_ENCODING), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        String key1 = Constants.ENCRYPTOR_KEY1; // 128 bit key
        String key2 = Constants.ENCRYPTOR_KEY2;
        String str = "b4da1c48-4b6e-40cf-8232-aa23611b3cf7;1A530637289A03B07199A44E8D531427;admin";
        String encData = "";
        System.out.println("encrypt=" + ( encData=encrypt(key1, key2, str) ) );
        System.out.println("decrypt=" + decrypt(key1, key2, encData) );       
        System.out.println("encrypt toHex=" + SimpleUtils.toHex(encData) );        
        //System.out.println(decrypt(key1, key2, encrypt(key1, key2, "Hello World")));
    }
    
}
