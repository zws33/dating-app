package me.zwsmith.datingapp.domain

import me.zwsmith.datingapp.data.Location
import me.zwsmith.datingapp.data.Photo

data class Match(
    val age: Int,
    val cityName: String,
    val countryCode: String,
    val countryName: String,
    val enemy: Int,
    val friend: Int,
    val gender: Int,
    val genderTags: List<Any>,
    val is_online: Int,
    val lastContactTime: List<Int>,
    val lastLogin: Int,
    val liked: Boolean,
    val location: Location,
    val match: Int,
    val orientation: Int,
    val orientationTags: List<Any>,
    val photo: Photo,
    val relative: Long,
    val stateCode: String,
    val stateName: String,
    val stoplightColor: String,
    val userid: String,
    val username: String
)