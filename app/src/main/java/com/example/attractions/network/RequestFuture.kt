package com.example.attractions.network

import android.os.SystemClock
import retrofit2.Call
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class RequestFuture<T>: Future<T> {
    private var mResultReceived = false
    private var mResult: T? = null
    private var mException: VolleyError? = null

    companion object {
        fun <E> newFuture(): RequestFuture<E>? {
            return RequestFuture()
        }
    }

    override fun cancel(p0: Boolean): Boolean {
        return false
    }

    override fun isCancelled(): Boolean {
        return false
    }

    override fun isDone(): Boolean {
        return mResultReceived || mException != null || isCancelled
    }

    override fun get(): T {
        return try {
            doGet( /* timeoutMs= */null) as T
        } catch (e: TimeoutException) {
            throw AssertionError(e)
        }
    }

    override fun get(timeout: Long, unit: TimeUnit?): T {
        return doGet(TimeUnit.MILLISECONDS.convert(timeout, unit)) as T
    }

    @Synchronized
    @Throws(
        InterruptedException::class,
        ExecutionException::class,
        TimeoutException::class
    )
    private fun doGet(timeoutMs: Long?): T {
        if (mException != null) {
            throw ExecutionException(mException)
        }
        if (mResultReceived) {
            return mResult!!
        }
        if (timeoutMs == null) {
            while (!isDone) {
//                wait(0)
            }
        } else if (timeoutMs > 0) {
            var nowMs = SystemClock.uptimeMillis()
            val deadlineMs = nowMs + timeoutMs
            while (!isDone && nowMs < deadlineMs) {
//                wait(deadlineMs - nowMs)
                nowMs = SystemClock.uptimeMillis()
            }
        }
        if (mException != null) {
            throw ExecutionException(mException)
        }
        if (!mResultReceived) {
            throw TimeoutException()
        }
        return mResult!!
    }

    @Synchronized
    fun onResponse(response: T) {
        mResultReceived = true
        mResult = response
//        notifyAll()
    }

    @Synchronized
    fun onErrorResponse(call: Call<T>, t: Throwable) {
        if (call.isCanceled) {
            mException = VolleyError(t.message, true)
        } else {
            mException = VolleyError(t.message)
        }
//        notifyAll()
    }
}