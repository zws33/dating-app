package me.zwsmith.datingapp.data

import com.squareup.moshi.Json

data class MatchesResponse(
    val data: List<Data>,
    val paging: Paging,
    @Json(name = "total_matches") val totalMatches: Int
)

data class Data(
    val age: Int,
    @Json(name = "city_name") val cityName: String,
    @Json(name = "country_code") val countryCode: String,
    @Json(name = "country_name") val countryName: String,
    val enemy: Int,
    val friend: Int,
    val gender: Int,
    @Json(name = "gender_tags") val genderTags: List<Any>,
    val is_online: Int,
    @Json(name = "last_contact_time") val lastContactTime: List<Int>,
    @Json(name = "last_login") val lastLogin: Int,
    val liked: Boolean,
    val location: Location,
    val match: Int,
    val orientation: Int,
    @Json(name = "orientation_tags") val orientationTags: List<Any>,
    val photo: Photo,
    val relative: Long,
    @Json(name = "state_code") val stateCode: String,
    @Json(name = "state_name") val stateName: String,
    @Json(name = "stoplight_color") val stoplightColor: String,
    val userid: String,
    val username: String
)

data class Location(
    @Json(name = "city_name") val cityName: String,
    @Json(name = "country_code") val countryCode: String,
    @Json(name = "country_name") val countryName: String,
    @Json(name = "state_code") val stateCode: String,
    @Json(name = "state_name") val stateName: String
)

data class Photo(
    @Json(name = "base_path") val basePath: String,
    val caption: String,
    @Json(name = "crop_rect") val cropRect: CropRect,
    @Json(name = "full_paths") val fullPaths: FullPaths,
    val id: String,
    val ordinal: Int,
    @Json(name = "original_size") val originalSize: OriginalSize,
    @Json(name = "thumb_paths") val thumbPaths: ThumbPaths
)

data class CropRect(
    val height: Int,
    val width: Int,
    val x: Int,
    val y: Int
)

data class FullPaths(
    val large: String,
    val medium: String,
    val original: String,
    val small: String
)

data class OriginalSize(
    val height: Int,
    val width: Int
)

data class ThumbPaths(
    @Json(name = "desktop_match") val desktopMatch: String,
    val large: String,
    val medium: String,
    val small: String
)

data class Paging(
    val cursors: Cursors
)

data class Cursors(
    val after: String,
    val before: String,
    val current: String
)