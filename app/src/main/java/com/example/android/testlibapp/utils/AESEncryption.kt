package com.example.android.testlibapp.utils

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class AESEncryption {

    //Step 1: Secret Key Generation
    //Generate a random secret key
    fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256) // You can also use 128 or 192 bits
        return keyGenerator.generateKey()
    }

    //Step 2: IV (Initialization Vector) Generation
    fun generateIV(): IvParameterSpec {
        val iv = ByteArray(16)
        val secureRandom = SecureRandom()
        secureRandom.nextBytes(iv)
        return IvParameterSpec(iv)
    }

    fun encrypt(
        textToEncrypt: String,
        secretKey: SecretKey,
        iv: IvParameterSpec
    ): String {

        /* val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
        val key = generateKey(keyEncryption)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherText = cipher.doFinal(plainText)
        ivValue = cipher.iv
        println("encrypt_ivValue: $ivValue")*/
        val plainText = textToEncrypt.toByteArray()

        val cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)

        val encrypt = cipher.doFinal(plainText)
        return Base64.encodeToString(encrypt, Base64.DEFAULT)
    }

    fun decrypt(
        encryptedText: String,
        secretKey: SecretKey,
        iv: IvParameterSpec
    ): String {
        val textToDecrypt = Base64.decode(encryptedText, Base64.DEFAULT)
        val cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)
        val decrypt = cipher.doFinal(textToDecrypt)
        return String(decrypt)
    }

}