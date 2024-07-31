package ru.astar.task.data.room

import androidx.room.TypeConverter

class TagsConverter {

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
            .replace("[", "")
            .replace("]", "")
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        return string.split(",")
            .map {
                it.replace("[", "")
                    .replace("]", "")
                    .replace("\"", "")
            }.toList()
    }
}