package com.bakumenko.barley_break.barley_break.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionHelper implements Thread.UncaughtExceptionHandler {
    private final DateFormat _formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
    private final Thread.UncaughtExceptionHandler _prevHandler;

    private String _vName = "unknown";
    private String _vCode = "unknown";

    public static void bindReporter(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHelper.inContext(context));
    }

    private ExceptionHelper(Context context, boolean chained) {
        try {
            PackageInfo mPackInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            _vName = mPackInfo.versionName;
            _vCode = String.valueOf(mPackInfo.getLongVersionCode());
        } catch (PackageManager.NameNotFoundException ignore) {
        }

        if (chained)
            _prevHandler = Thread.getDefaultUncaughtExceptionHandler();
        else
            _prevHandler = null;
    }

    static ExceptionHelper inContext(Context context) {
        return new ExceptionHelper(context, true);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        final String state = Environment.getExternalStorageState();
        final Date dumpDate = new Date(System.currentTimeMillis());
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            StringBuilder reportBuilder = new StringBuilder();
            reportBuilder
                    .append("\n\n\n")
                    .append(_formatter.format(dumpDate)).append("\n")
                    .append(String.format("Version: %s (%s)\n", _vName, _vCode))
                    .append(thread.toString()).append("\n");
            processThrowable(exception, reportBuilder);
            addRecord(reportBuilder.toString());
        }

        if (_prevHandler != null)
            _prevHandler.uncaughtException(thread, exception);
    }

    private void addRecord(String record) {
        // TODO: Initialize Log Writer
//           LogWriter.e(reportBuilder.toString(), exception);

        // TODO: Initialize PathHelper

//        File stacktrace = new File(PathHelper.getErrorLogPath());
//        File dumpdir = stacktrace.getParentFile();
//        boolean dirReady = dumpdir.isDirectory() || dumpdir.mkdirs();
//        if (dirReady) {
//            try (FileWriter writer = new FileWriter(stacktrace, true)) {
//                writer.write(record);
//            } catch (IOException ignore) {
//
//            }
//        }
    }

    private void processThrowable(Throwable exception, StringBuilder builder) {
        if (exception == null)
            return;
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        builder
                .append("Exception: ").append(exception.getClass().getName()).append("\n")
                .append("Message: ").append(exception.getMessage()).append("\nStacktrace:\n");
        for (StackTraceElement element : stackTraceElements) {
            builder.append("at ").append(element.toString()).append(String.format("(line: %s)", String.valueOf(element.getLineNumber()))).append("\n");
        }
        processThrowable(exception.getCause(), builder);
    }
}
