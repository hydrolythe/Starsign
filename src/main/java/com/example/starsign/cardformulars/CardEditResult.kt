package com.example.starsign.cardformulars

import okhttp3.ResponseBody

class CardEditResult(val success: ResponseBody?=null, val exception: Exception?=null) {
}