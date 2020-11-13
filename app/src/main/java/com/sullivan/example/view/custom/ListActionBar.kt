package com.sullivan.example.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.sullivan.example.R
import kotlinx.android.synthetic.main.view_list_action_bar.view.*

class ListActionBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private lateinit var title: String
    private lateinit var rightActionText: String
    private lateinit var leftActionText: String

    init {
        getAttrs(attrs)
        inflateView(context)
        setAttrs()
    }

    private fun getAttrs(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ListActionBar, 0, 0).apply {
            try {
                title = getString(R.styleable.ListActionBar_title) ?: ""
                rightActionText = getString(R.styleable.ListActionBar_rightActionText) ?: ""
                leftActionText = getString(R.styleable.ListActionBar_leftActionText) ?: ""
            } finally {
                recycle()
            }
        }
    }

    private fun inflateView(context: Context) {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.view_list_action_bar, this, false).also {
                addView(it)
            }
    }

    private fun setAttrs() {
        barTitle.text = title
        rightActionButton.text = rightActionText
        leftActionButton.text = leftActionText
    }

    inline fun setRightOnClickListener(crossinline action: () -> Unit){
        rightActionButton.setOnClickListener { action() }
    }

    inline fun setLeftActionOnClickListener(crossinline action: () -> Unit){
        leftActionButton.setOnClickListener { action() }
    }

    fun getTitle(): String = title

    fun setTitle(title: String) {
        this.title = title
        invalidate()
        requestLayout()
    }

    fun getActionText(): String = rightActionText

    fun setActionText(actionText: String) {
        this.rightActionText = actionText
        invalidate()
        requestLayout()
    }
}
