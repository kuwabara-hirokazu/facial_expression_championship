package com.example.facialexpressionchampionship.extension

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.showToast(@StringRes resourceId: Int) {
    Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
}

fun Context.hasPermission(@NonNull permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.getPathFromUri(uri: Uri): String? {
    if (DocumentsContract.isDocumentUri(this, uri)) {
        if ("com.android.providers.media.documents" == uri.authority) {
            // クエリ作成
            val contentUri =
                MediaStore.Files.getContentUri("external")  // content://media/external/file
            val selection = "_id=?"
            val docId = DocumentsContract.getDocumentId(uri)    // "image:1688"
            val split = docId.split(":".toRegex()).toTypedArray()   // {"image", "1688"}
            val selectionArgs = arrayOf(split[1])   // {"1688"}

            return getDataColumn(contentUri, selection, selectionArgs)
        }
    }
    return null
}

// MediaStoreにアクセスしてファイルパスを取得する
fun Context.getDataColumn(
    uri: Uri,
    selection: String?,
    selectionArgs: Array<String>?
): String? {
    val projection = arrayOf(MediaStore.Files.FileColumns.DATA) // {"_data"}

    return contentResolver.query(uri, projection, selection, selectionArgs, null)
        ?.use { cursor ->
            // 取得結果から先頭レコードのファイルパスを取得する
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(projection[0])
                cursor.getString(index)
            } else {
                null
            }
        }
}
