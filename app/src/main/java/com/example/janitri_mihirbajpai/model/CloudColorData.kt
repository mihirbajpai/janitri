package com.example.janitri_mihirbajpai.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CloudColorData(
    var date: String = "", // Default value for deserialization
    var hex: String = "",  // Default value for deserialization
    var id: Int = 0        // Default value for deserialization
) {
    // No-argument constructor required for Firebase deserialization
    constructor() : this("", "", 0)
}
