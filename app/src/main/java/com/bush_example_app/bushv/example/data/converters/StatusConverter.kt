package com.bush_example_app.bushv.example.data.converters

import androidx.room.TypeConverter
import com.bush_example_app.bushv.example.domain.entity.Status


class StatusConverter {

    @TypeConverter
    fun toStatus(position: Int): Status {
        return Status.values()[position]
    }

    @TypeConverter
    fun toInt(status: Status): Int {
        return status.ordinal
    }

}