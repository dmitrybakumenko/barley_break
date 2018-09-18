package com.bakumenko.barleybreak.helpers

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ExceptionHandler(c: Context) : Thread.UncaughtExceptionHandler {

    private var context: Context = c

    private var _versionName = "unknown"
    private var _versionCode = "unknown"

    init {
        val packageManager = c.packageManager
        val packInfo: PackageInfo
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0)
            _versionName = packInfo.versionName
            _versionCode = packInfo.longVersionCode.toString()
        } catch (ignore: PackageManager.NameNotFoundException) {

        }
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        val state = Environment.getExternalStorageState()
        val dumpDate = Date(System.currentTimeMillis())

        if (state == Environment.MEDIA_MOUNTED) {
            val reportBuilder = StringBuilder()

            reportBuilder.append("\n\n\n")
                    .append(SimpleDateFormat("dd.MM.yy HH:mm", Locale.US).format(dumpDate)).append("\n")
                    .append(String.format("Version: %s (%s)\n", _versionName, _versionCode))
                    .append(t.toString()).append("\n")

            processThrowable(e, reportBuilder)
            addRecord(reportBuilder.toString())
        }
    }

    private fun addRecord(record: String) {
        val stacktrace = File(PathHelper.Instance.pathErrorLog)
        val dumpDir = stacktrace.parentFile
        val dirReady = dumpDir.isDirectory || dumpDir.mkdirs()
        if (dirReady) {
            var writer: FileWriter? = null
            try {
                writer = FileWriter(stacktrace, true)
                writer.write(record)
            } catch (ignore: Exception) {
            } finally {
                try {
                    writer?.close()
                } catch (ignore: IOException) {
                }
            }
        }
    }

    private fun processThrowable(e: Throwable?, builder: StringBuilder) {
        if (e == null)
            return

        val stackTraceElements = e.stackTrace

        builder
                .append("Exception: ")
                .append(e.javaClass.name).append("\n")
                .append("Message: ")
                .append(e.message)
                .append("\nStacktrace:\n")

        for (element in stackTraceElements) {
            builder
                    .append("at ")
                    .append(element.toString())
                    .append(String.format("(line: %s)", element.lineNumber.toString()))
                    .append("\n")
        }

        processThrowable(e.cause, builder)
    }

    companion object {
        fun bind(c: Context) {
            Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(c))
        }
    }
}