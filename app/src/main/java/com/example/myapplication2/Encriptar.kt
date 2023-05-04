package com.example.myapplication2

import android.util.Log
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * encriptar y desencriptar
 */
class Encriptar {

    private val key = generateKey()

    /**
     * encriptar nombre
     */
    fun encriptarname(nombre:String): ByteArray {
        val plaintext = nombre.toByteArray()
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val ciphertext: ByteArray = cipher.doFinal(plaintext)
        val iv: ByteArray = cipher.iv
        return ciphertext + iv
    }
    /**
     * desencriptar nombre
     */
    fun desencriptarname(ciphertext: ByteArray): String {
        val iv = ciphertext.takeLast(16).toByteArray() // Extraer el vector de inicialización del cifrado
        val cipher1 = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher1.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        val plaintext: ByteArray = cipher1.doFinal(ciphertext.dropLast(16).toByteArray())
        return String(plaintext, Charsets.UTF_8)
    }

    private fun generateKey(): SecretKey {
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)
        return keygen.generateKey()
    }



}