package com.satdev.prueba_ceiba.featureList.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "geo")
data class Geo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "geo_id")
    var geoId : Int = 0,
    @SerializedName("lat" ) var lat : String? = null,
    @SerializedName("lng" ) var lng : String? = null
)
