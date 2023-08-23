package com.walton.waltonicc.models.schedule

data class ScheduleModelItem(
    val created_at: String,
    val end_date: String,
    val id: Int,
    val match_desc: String,
    val match_format: String,
    val series_name: String,
    val start_date: String,
    val team1_image_id: String,
    val team1_name: String,
    val team1_sname: String,
    val team2_image_id: String,
    val team2_name: String,
    val team2_sname: String,
    val updated_at: String,
    val venue_city: String,
    val venue_ground: String,
    val venue_timezone: String
)