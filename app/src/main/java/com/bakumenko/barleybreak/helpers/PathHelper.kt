package com.bakumenko.barleybreak.helpers

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.bakumenko.barleybreak.MyApp
import org.apache.commons.io.FilenameUtils
import java.io.File

class PathHelper private constructor() {

    private var _state: String? = null
    private var _available: Boolean = false
    private var _writable: Boolean = false

    private var _pathStorage: String? = null
    private var _pathErrorLog: String? = null
    private var _pathLogcat: String? = null

    init {
        checkStorage()
    }

    val pathErrorLog: String
        get() {
            if (_pathErrorLog == null) {
                _pathErrorLog = FilenameUtils.concat(preparePathInStorage("Logs", false), "errorLog.txt")
                val file = File(_pathErrorLog)
                if (file.exists() && file.length() / 1024 / 1024 >= 5) {
                    file.delete()
                }
            }
            return _pathErrorLog ?: throw IllegalStateException("Something wrong with storage")
        }

    val pathLogcat: String
        get() {
            if (_pathLogcat == null) {
                _pathLogcat = FilenameUtils.concat(preparePathInStorage("Logs", false), "logcat.txt")
                val file = File(_pathLogcat)
                if (file.exists() && file.length() / 1024 / 1024 >= 5) {
                    file.delete()
                }
            }
            return _pathLogcat ?: throw IllegalStateException("Something wrong with storage")
        }

    private val pathStorage: String
        get() {
            if (_pathStorage == null) {
                checkStorage()
                if (_writable) {
                    val file = MyApp.instance.getExternalFilesDir(null)
                    if (file != null) {
                        if (!file.exists()) {
                            try {
                                file.mkdir()
                                _pathStorage = file.absolutePath
                            } catch (ignore: Exception) {

                            }
                        } else {
                            _pathStorage = file.absolutePath
                        }
                    }
                }
                if (_pathStorage == null) {
                    val file = MyApp.instance.filesDir
                    if (file != null) {
                        try {
                            file.mkdir()
                        } catch (ignore: Exception) {

                        }
                        _pathStorage = file.absolutePath
                    }

                }
            }

            return _pathStorage ?: throw IllegalStateException("Something wrong with storage")
        }

    private fun preparePathInStorage(dirName: String, isNoMedia: Boolean): String {
        val dir = File(pathStorage, dirName)
        if (!dir.exists() && !dir.mkdir()) {
            Log.e("Pathes", String.format("getPathInStorage - %s", dirName))
        }

        if (isNoMedia) {
            setNoMedia(dir)
        }

        return dir.absolutePath
    }

    private fun setNoMedia(dir: File?) {
        try {
            if (dir != null && dir.isDirectory && dir.exists()) {
                val noMediaFile = File(dir, MediaStore.MEDIA_IGNORE_FILENAME)
                if (!noMediaFile.exists()) {
                    if (noMediaFile.createNewFile()) {
                        MyApp.instance.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(noMediaFile)))
                    }
                }
            }
        } catch (exc: Exception) {
            Log.e(null, null, exc)
        }

    }

    private fun checkStorage() {
        val state = Environment.getExternalStorageState()

        if (_state != null && _state.equals(state, ignoreCase = true)) {
            return
        }

        when (state) {
            Environment.MEDIA_MOUNTED -> {
                _writable = true
                _available = true
            }
            Environment.MEDIA_MOUNTED_READ_ONLY -> {
                _available = true
                _writable = false
            }
            else -> {
                _writable = false
                _available = false
            }
        }

        _state = state
    }

    private object Holder {
        val Instance = PathHelper()
    }

    companion object {
        val Instance: PathHelper by lazy { Holder.Instance }
    }
}