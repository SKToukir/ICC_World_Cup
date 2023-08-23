package com.walton.waltonicc.models.newsmodel

data class NewsModelItem(
    val context: String,
    val cover_image_caption: String,
    val cover_image_id: String,
    val cover_image_source: String,
    val created_at: String,
    val details: String,
    val id: Int,
    val image_id: String,
    val intro: String,
    val news_id: String,
    val published_time: String,
    val seo_headline: String,
    val source: String,
    val story_type: String,
    val title: String,
    val updated_at: String
)