package com.satdev.prueba_ceiba.featureList.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "address")
data class Address(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "address_id")
    var addressId  : Int = 0,
    @SerializedName("street"  ) var street  : String? = null,
    @SerializedName("suite"   ) var suite   : String? = null,
    @SerializedName("city"    ) var city    : String? = null,
    @SerializedName("zipcode" ) var zipcode : String? = null,
    @Embedded
    @SerializedName("geo"     ) var geo     : Geo?    = Geo()
)
