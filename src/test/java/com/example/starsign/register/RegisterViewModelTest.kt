package com.example.starsign.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import JwtEncoder
import com.example.starsign.LiveDataTestUtil.getValue
import com.example.starsign.MainCoroutineRule
import com.example.starsign.mockRepository.FakeUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    private lateinit var userRepository: FakeUserRepository
    private lateinit var registerViewModel: RegisterViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        userRepository = FakeUserRepository()
        registerViewModel = RegisterViewModel(userRepository)
    }

    @After
    fun tearDown() {
    }

    @ExperimentalStdlibApi
    @Test
    fun voegRegistratieToe_IsCorrect_RegistreertGebruiker() {
        val username = "delenda"
        registerViewModel.voegRegistratieToe(username, "Nederland1-", "Nederland1-")
        assertThat(getValue(registerViewModel.jwtResponse).success?.jwtToken, `is`(JwtEncoder.encode(username)))
    }

    @Throws
    @Test
    fun voegRegistratieToe_IsVerkeerd_GeeftFoutMelding(){
        registerViewModel.voegRegistratieToe("abc", "Deze", "Dit")
        assertThat(getValue(registerViewModel.jwtResponse).error, not(nullValue()))
    }
}