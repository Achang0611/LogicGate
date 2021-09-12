package com.vm.logicgate.minecraft.utils

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


object JsonManager {

    private val gson by lazy {
        GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()
    }

    fun Any?.toJson(): String = gson.toJson(this)

    fun <T> String.fromJson(type: TypeToken<T>): T = gson.fromJson(this, type.type)

    inline fun <reified T> getTypeToken(): TypeToken<T> = object : TypeToken<T>() {}

}