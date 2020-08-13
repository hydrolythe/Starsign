package com.example.starsign.register

import com.example.starsign.network.JwtResponse

data class JwtTokenResult(val success: JwtResponse?=null, val error: Exception?=null)