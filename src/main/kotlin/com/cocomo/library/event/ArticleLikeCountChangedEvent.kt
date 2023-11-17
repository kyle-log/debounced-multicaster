package com.cocomo.library.event

import com.cocomo.library.debounce.DebouncedEvent

data class ArticleLikeCountChangedEvent(
    val articleId: Long,
    override val debounceKey: String = "ArticleLikeCountChangedEvent(articleId=$articleId)",
    override val debounceTtlMillis: Long = 5000L,
    override val debounced: Boolean = false,
) : DebouncedEvent {
    override fun debounce(): DebouncedEvent = this.copy(debounced = true)
}