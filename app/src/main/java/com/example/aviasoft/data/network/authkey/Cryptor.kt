package com.example.aviasoft.data.network.authkey

import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Cryptor {

    private var instanceKey: ByteArray = ByteArray(0)
    private var instanceIv: ByteArray = ByteArray(0)

    fun updateIV(newIv: String) {
        this.instanceIv = newIv.toByteArray(StandardCharsets.UTF_8)
    }

    fun updateKey(newKey: String) {
        this.instanceKey = newKey.toByteArray(StandardCharsets.UTF_8)
    }

    fun encrypt(string: String): String {
        val cipher = Cipher.getInstance(METHOD)
        val keySpec = SecretKeySpec(instanceKey, ALGORITHM_AES)
        val ivSpec = IvParameterSpec(instanceIv)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(string.toByteArray(StandardCharsets.UTF_8))
        return Base64.getEncoder().encodeToString(encrypted)
    }

    fun decrypt(encrypted: String): String {
        val cipher = Cipher.getInstance(METHOD)
        val keySpec = SecretKeySpec(instanceKey, ALGORITHM_AES)
        val ivSpec = IvParameterSpec(instanceIv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decoded = Base64.getDecoder().decode(encrypted)
        val decrypted = cipher.doFinal(decoded)
        return String(decrypted, StandardCharsets.UTF_8)
    }

    companion object {
        const val METHOD = "AES/CBC/PKCS5Padding"
        const val ALGORITHM_AES = "AES"
    }

}