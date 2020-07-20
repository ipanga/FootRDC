package com.tootiyesolutions.footrdc.database

import androidx.room.TypeConverter
import com.tootiyesolutions.footrdc.model.Content
import com.tootiyesolutions.footrdc.model.Title

class Converters {

    @TypeConverter
    fun fromContent(content: Content): String {
        return content.articleContent.toString()
    }

    @TypeConverter
    fun toContent(articleContent: String): Content {
        return Content(articleContent)
    }

    @TypeConverter
    fun fromTitle(title: Title): String {
        return title.articleTitle.toString()
    }

    @TypeConverter
    fun toTitle(articleTitle: String): Title {
        return Title(articleTitle)
    }
}