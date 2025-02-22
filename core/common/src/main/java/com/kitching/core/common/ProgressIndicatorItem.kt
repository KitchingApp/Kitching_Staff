package com.kitching.core.common

class ProgressIndicatorItem(val image: Int, val title: String) {
    companion object {
        private val salt = ProgressIndicatorItem(image = R.drawable.icon_salt, title = "소금 뿌리는 중..")
        private val bread = ProgressIndicatorItem(image = R.drawable.icon_whipper, title = "조리하는 중..")
        private val whipper = ProgressIndicatorItem(image = R.drawable.icon_bread, title = "빵 굽는 중..")

        fun getRandomItem() = listOf(salt, bread, whipper).random()
    }
}