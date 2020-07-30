package com.example.starsign

import android.graphics.drawable.ColorDrawable
import android.os.IBinder
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


class ToastMatcher : TypeSafeMatcher<Root?>() {
    override fun describeTo(description: Description) {
        description.appendText("is toast")
    }

    override fun matchesSafely(root: Root?): Boolean {
        val type: Int = root?.getWindowLayoutParams()?.get()?.type!!
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken: IBinder = root.getDecorView().getWindowToken()
            val appToken: IBinder = root.getDecorView().getApplicationWindowToken()
            if (windowToken === appToken) {
                return true
            }
        }
        return false
    }
}

fun withRecyclerViewText(position: Int, @NonNull number:Int, @NonNull string:String): Matcher<View?>? {
    checkNotNull(string)
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("contains text at position $position: ")
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder =
                view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
            val v = viewHolder.itemView.findViewById<TextView>(number) ?: return false
            return v.text.matches(string.toRegex())
        }
    }
}

fun hasNoText(): Matcher<View?>? {
    return object : BoundedMatcher<View?, EditText>(EditText::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has no error text: ")
        }

        override fun matchesSafely(view: EditText): Boolean {
            return view.error == null
        }
    }
}

fun isEditTextValueEqualTo(content: String): Matcher<View?>? {
    return object : TypeSafeMatcher<View?>() {
        override fun describeTo(description: Description) {
            description.appendText("Match Edit Text Value with View ID Value : :  $content")
        }

        override fun matchesSafely(view: View?): Boolean {
            if (view !is TextView && view !is EditText) {
                return false
            }
            if (view != null) {
                val text: String
                text = if (view is TextView) {
                    view.text.toString()
                } else {
                    (view as EditText).text.toString()
                }
                return text.equals(content, ignoreCase = true)
            }
            return false
        }
    }
}

fun withRecyclerViewCheck(position: Int, @NonNull number:Int, @NonNull bool:Boolean): Matcher<View?>? {
    checkNotNull(bool)
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("is checked at position $position: ")
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder =
                view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
            val v = viewHolder.itemView.findViewById<RadioButton>(number) ?: return false
            return v.isChecked == bool
        }
    }
}

fun withRecyclerViewColorAnalysis(position: Int, @NonNull number:Int, @NonNull color: ColorDrawable): Matcher<View?>? {
    checkNotNull(color)
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has color at position $position: ")
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder =
                view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
            val v = viewHolder.itemView.findViewById<LinearLayout>(number) ?: return false
            return v.background == color
        }
    }
}