package com.example.facialexpressionchampionship.extension

import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

fun File.toByteArray(): ByteArray? {
    try {
        val inputStream = FileInputStream(this)
        val byteArrayOutputStream = ByteArrayOutputStream()

        // 8byteずつ読み込む
        val buffer = ByteArray(8)
        // データの読み込み
        var readSize = inputStream.read(buffer)

        // 全てのデータを読み込むまで繰り返す
        while (readSize != -1) {
            // 読み込んだデータを書き込む
            byteArrayOutputStream.write(buffer, 0, readSize)
            // データの更新
            readSize = inputStream.read(buffer)
        }
        val totalByteArray = byteArrayOutputStream.toByteArray()

        inputStream.close()
        byteArrayOutputStream.close()

        return totalByteArray

    } catch (e: Exception) {
        Timber.e("Byte変換エラー $e")
        return null
    }
}
