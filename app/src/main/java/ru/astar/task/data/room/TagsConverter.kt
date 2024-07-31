package ru.astar.task.data.room

import android.util.Log
import androidx.room.TypeConverter

class TagsConverter {

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(prefix = "[", separator = ", ", postfix = "]")
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        val prepared = string.removeSurrounding("[", "]")
        if (prepared.isBlank()) return emptyList()

        val list = prepared
            .split(", ")
            .map { it.trim('"') }

        Log.i("Converter", "to list: $list")
        return list
    }
}