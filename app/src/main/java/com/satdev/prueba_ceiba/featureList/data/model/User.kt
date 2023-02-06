package com.satdev.prueba_ceiba.featureList.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User (
    @PrimaryKey
    @SerializedName("id"       ) var id       : Int?     = null,
    @SerializedName("name"     ) var name     : String?  = null,
    @SerializedName("username" ) var username : String?  = null,
    @SerializedName("email"    ) var email    : String?  = null,
    @Embedded
    @SerializedName("address"  ) var address  : Address? = Address(),
    @SerializedName("phone"    ) var phone    : String?  = null,
    @SerializedName("website"  ) var website  : String?  = null,

    @Embedded(prefix = "user_company")
    @SerializedName("company"  ) var company  : Company? = Company()
        )