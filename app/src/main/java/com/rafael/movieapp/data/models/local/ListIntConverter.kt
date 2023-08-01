package com.rafael.movieapp.data.models.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListIntConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromListInt(list: List<Int>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toListInt(json: String?): List<Int>? {
        val type = object : TypeToken<List<Int>?>() {}.type
        return gson.fromJson(json, type)
    }
}
