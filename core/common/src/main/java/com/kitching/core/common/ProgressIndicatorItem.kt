package com.kitching.core.common

class ProgressIndicatorItem(val image: Int, val title: Int) {
    companion object {
        private val salt = ProgressIndicatorItem(image = R.drawable.icon_salt, title = R.string.progress_indicator_message_salt)
        private val bread = ProgressIndicatorItem(image = R.drawable.icon_whipper, title = R.string.progress_indicator_message_whipper)
        private val whipper = ProgressIndicatorItem(image = R.drawable.icon_bread, title = R.string.progress_indicator_message_bread)

        fun getRandomItem() = listOf(salt, bread, whipper).random()
    }
}