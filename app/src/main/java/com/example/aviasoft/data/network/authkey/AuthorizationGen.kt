package com.example.aviasoft.data.network.authkey

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object AuthorizationGen {

    fun genkey(rts: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC)
        val ctime = formatter.format(Instant.ofEpochSecond(rts))

        val cryptor = Cryptor()

        val sourceA = String.format(
            "%sTEST%sTASK%s",
            (ctime[13].code + 1) * (ctime[15].code + 1),
            ctime.substring(0, 17),
            ctime.substring(0, 17)
        )

        val sourceB = String.format(
            "DOIT%sPLS%s%s",
            ctime.substring(12, 17),
            ctime.substring(12, 17),
            (ctime[13].code + 1) * (ctime[15].code + 3)
        )

        val key = MessageDigest.getInstance("SHA-512")
            .digest(sourceA.toByteArray(StandardCharsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
            .substring(0, 32)
            .uppercase()

        val iv = MessageDigest.getInstance("SHA-512")
            .digest(sourceB.toByteArray(StandardCharsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
            .substring(16, 32)
            .uppercase()

        cryptor.updateKey(key)
        cryptor.updateIV(iv)

        val encryptedKey = cryptor.encrypt(key.trim())
        val encryptedPhrase = cryptor.encrypt("justfortesttask")
        val encryptedIv = cryptor.encrypt(iv.trim())

        return "$encryptedKey$encryptedPhrase$encryptedIv"
    }

}