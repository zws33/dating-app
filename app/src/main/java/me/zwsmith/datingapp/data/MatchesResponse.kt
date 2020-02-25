package me.zwsmith.datingapp.data

data class MatchesResponse(
    val data: List<Data>,
    val paging: Paging,
    val total_matches: Int
)

data class Data(
    val age: Int,
    val city_name: String,
    val country_code: String,
    val country_name: String,
    val enemy: Int,
    val friend: Int,
    val gender: Int,
    val gender_tags: List<Any>,
    val is_online: Int,
    val last_contact_time: List<Int>,
    val last_login: Int,
    val liked: Boolean,
    val location: Location,
    val match: Int,
    val orientation: Int,
    val orientation_tags: List<Any>,
    val photo: Photo,
    val relative: Long,
    val state_code: String,
    val state_name: String,
    val stoplight_color: String,
    val userid: String,
    val username: String
)

data class Location(
    val city_name: String,
    val country_code: String,
    val country_name: String,
    val state_code: String,
    val state_name: String
)

data class Photo(
    val base_path: String,
    val caption: String,
    val crop_rect: CropRect,
    val full_paths: FullPaths,
    val id: String,
    val ordinal: Int,
    val original_size: OriginalSize,
    val thumb_paths: ThumbPaths
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
    val desktop_match: String,
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