package com.jason.mvvm.common

import kotlin.system.exitProcess


/**
 * @author Jason
 * @description->未捕获异常通用处理逻辑
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    fun init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread?, ex: Throwable) {
        exitProcess(0)
    }
}