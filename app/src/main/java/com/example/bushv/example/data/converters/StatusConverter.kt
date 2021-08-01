package com.example.bushv.example.data.converters

import androidx.room.TypeConverter
import com.example.bushv.example.domain.entity.Status

class StatusConverter {

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

}