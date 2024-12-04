package com.example.android.testlibapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.testlibapp.utils.AESEncryption
import com.wizarpos.idealink.testlibapp.R
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    private lateinit var cipherTextNew: ByteArray
    private lateinit var ivValue: ByteArray

    private var keyEncryption = "Test@123"

    //test1
    private lateinit var secretKey: SecretKey
    private lateinit var iv: IvParameterSpec
    private var result = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val btnEncryption = findViewById<Button>(R.id.btnEncryp)
        val btnDecryption = findViewById<Button>(R.id.btnDeEncryp)
        val tvShow = findViewById<TextView>(R.id.tvShow)

        secretKey = AESEncryption().generateSecretKey()
        iv = AESEncryption().generateIV()

        btnEncryption.setOnClickListener {
            // cipherTextNew = encrypt("SreylengChhean")
            result = AESEncryption().encrypt(
                textToEncrypt = "SreylengChhean",
                secretKey = secretKey,
                iv = iv
            )
            tvShow.text = "encryption : $result"
        }

        btnDecryption.setOnClickListener {
//           decrypt(cipherTextNew)
            result = AESEncryption().decrypt(
                result, secretKey, iv
            )

            tvShow.text = "decryption: $result"
        }
    }

    private fun encrypt(strToEncrypt: String): ByteArray {
        val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
        val key = generateKey(keyEncryption)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherText = cipher.doFinal(plainText)
        ivValue = cipher.iv
        println("encrypt_ivValue: $ivValue")
        return cipherText
    }

    private fun decrypt(dataToDecrypt: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val key = generateKey(keyEncryption)
        println("decrypt_ivValue: $ivValue")
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(ivValue))
        val cipherText = cipher.doFinal(dataToDecrypt)
        buildString(cipherText, "decrypt")
        return cipherText
    }

    private fun generateKey(password: String): SecretKeySpec {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray()
        digest.update(bytes, 0, bytes.size)
        val key = digest.digest()
        val secretKeySpec = SecretKeySpec(key, "AES")
        return secretKeySpec
    }

    private fun buildString(text: ByteArray, status: String): String {
        val sb = StringBuilder()
        for (char in text) {
            sb.append(char.toInt().toChar())
        }
        println("Result: $sb")
        return sb.toString()
    }

}