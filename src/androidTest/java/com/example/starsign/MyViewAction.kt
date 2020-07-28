package com.example.starsign

import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher


object MyViewAction {
    fun typeOnViewWithId(id: Int, number: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun perform(uiController: UiController?, view: View?) {
                try {
                    val v = view?.findViewById<TextView>(id)
                    v?.text = number.toString()
                } catch(e:Exception){
                    throw e
                }
            }

            override fun getDescription(): String {
                return "Replace text of a child view with specified id."
            }

        }
    }
}