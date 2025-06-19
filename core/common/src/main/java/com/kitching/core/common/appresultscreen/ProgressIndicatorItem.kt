package com.kitching.core.common.appresultscreen

import com.kitching.core.common.R

class ProgressIndicatorItem(val image: Int, val title: Int) {
    companion object {
        private val salt = ProgressIndicatorItem(image = R.drawable.icon_salt, title = R.string.progress_indicator_message_salt)
        private val bread = ProgressIndicatorItem(image = R.drawable.icon_whipper, title = R.string.progress_indicator_message_bread)
        private val whipper = ProgressIndicatorItem(image = R.drawable.icon_bread, title = R.string.progress_indicator_message_whipper)

        fun getRandomItem() = listOf(salt, bread, whipper).random()
    }
}