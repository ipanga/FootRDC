package com.tootiyesolutions.footrdc.adapter

import android.view.View
import com.tootiyesolutions.footrdc.model.Article

interface NewsClickListener {
    fun onArticleClicked(v: View, article: Article)
}