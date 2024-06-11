package com.kolor.utility

import com.kolor.R
import com.kolor.data.constants.Colors

class ColorResolver {
    companion object {
        fun resolve(colorId : Int) : Int?{
            return when(colorId) {
                Colors.COLOR_LIGHT_BLUE -> R.color.light_blue_click
                Colors.COLOR_GOLD -> R.color.gold_progress
                Colors.COLOR_LIGHT_RED-> R.color.light_pink
                Colors.COLOR_LIGHT_PURPLE -> R.color.purple_click
                Colors.COLOR_LIGHT_GREEN-> R.color.green_click
                Colors.COLOR_GEM_BLUE-> R.color.gem_progress
                Colors.COLOR_TRANSPARENT -> R.color.transparent
                Colors.COLOR_BACKGROUND_BLACK -> R.color.main_black
                else -> null
            }
        }
    }

}