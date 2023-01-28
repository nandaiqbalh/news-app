package com.nandaiqbalh.newsapp.data.local.database.converters

import androidx.room.TypeConverter
import com.nandaiqbalh.newsapp.data.network.models.news.Source

class Converters {

	@TypeConverter
	fun fromSource(source: Source): String {
		return source.name
	}

	@TypeConverter
	fun toSource(name: String): Source {
		return Source(name, name)
	}
}