package com.satdev.prueba_ceiba.featureList.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "company")
data class Company(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "company_id")
    var companyId :Int = 0,
    @ColumnInfo(name = "company_name")
    @SerializedName("name") var name        : String? = null,
    @SerializedName("catchPhrase") var catchPhrase : String? = null,
    @SerializedName("bs") var bs          : String? = null
)
