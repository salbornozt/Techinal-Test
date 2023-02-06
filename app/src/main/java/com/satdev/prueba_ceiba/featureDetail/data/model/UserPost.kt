package com.satdev.prueba_ceiba.featureDetail.data.model

import com.google.gson.annotations.SerializedName

data class UserPost(
    @SerializedName("userId" ) var userId : Int?    = null,
    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("title"  ) var title  : String? = null,
    @SerializedName("body"   ) var body   : String? = null
)
