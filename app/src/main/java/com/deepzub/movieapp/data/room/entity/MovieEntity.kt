package com.deepzub.movieapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deepzub.movieapp.util.Constants.TABLE_NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TABLE_NAME)
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Int = 0,

    @SerialName("poster")
    val poster: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("year")
    val year: String = "",

    @SerialName("imdbID")
    val imdbID: String = ""

    )