package com.suresh.dailywidget.utils

import android.content.Context
import android.content.Intent
import com.suresh.dailywidget.R

class AppUtils {
    companion object {
        fun shareQuote(context: Context, quoteMessage: String) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, quoteMessage)
                type = "text/plain"
            }

            val shareIntent =
                Intent.createChooser(sendIntent, context.getString(R.string.share_title))
            context.startActivity(shareIntent)
        }
    }
}