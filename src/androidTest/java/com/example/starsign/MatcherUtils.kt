package com.example.starsign

import android.os.IBinder
import android.view.View
import android.view.WindowManager
import android.widget.EditText
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

fun withText(position: Int, @NonNull number:Int, @NonNull string:String): Matcher<View?>? {
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
            val v = viewHolder.itemView.findViewById<EditText>(number) ?: return false
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