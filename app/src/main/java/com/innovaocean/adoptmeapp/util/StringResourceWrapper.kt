package com.innovaocean.adoptmeapp.util

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface StringResourceWrapper {
    fun getString(@StringRes stringRes: Int): String
    fun getString(stringRes: Int, vararg formatArgs: Any): String
    fun getQuantityString(@PluralsRes pluralsRes: Int, quantity: Int, vararg formatArgs: Any): String
    fun getStringArray(stringArrayRes: Int): Array<String>
}

class StringResourceWrapperAndroid(
    private val context: Context
) : StringResourceWrapper {

    override fun getString(stringRes: Int): String {
        return context.getString(stringRes)
    }

    override fun getString(stringRes: Int, vararg formatArgs: Any): String {
        return context.getString(stringRes, *formatArgs)
    }

    override fun getQuantityString(pluralsRes: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(pluralsRes, quantity, *formatArgs)
    }

    override fun getStringArray(stringArrayRes: Int): Array<String> {
        return context.resources.getStringArray(stringArrayRes)
    }

}