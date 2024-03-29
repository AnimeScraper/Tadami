package com.sf.tadami.data.providers

import android.content.Context
import com.sf.tadami.R
import java.io.File

class AndroidFoldersProvider(
    private val context: Context,
)  {

    fun backupDir(): File {
        return File(
            context.filesDir.absolutePath + File.separator + context.getString(R.string.app_name),
            "backup",
        )
    }

    fun backupPath(): String {
        return backupDir().absolutePath
    }
}
