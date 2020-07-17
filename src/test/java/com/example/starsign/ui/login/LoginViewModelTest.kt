package com.example.starsign.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import JwtEncoder
import com.example.starsign.LiveDataTestUtil.getValue
import com.example.starsign.MainCoroutineRule
import com.example.starsign.mockRepository.FakeUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.hamcrest.core.Is.`is`
import org.junit.*

@ExperimentalCoroutinesApi
class LoginViewModelTest {
    private lateinit var userRepository: FakeUserRepository
    private lateinit var loginViewModel: LoginViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        userRepository = FakeUserRepository()
        loginViewModel = LoginViewModel(userRepository)
    }

    @ExperimentalStdlibApi
    @Test
    fun login_Werkt_GeeftSleutelTerug() {
        val username= "Nedl"
        loginViewModel.login(username,"Globoesporte")
        assertThat(getValue(loginViewModel.loginResult).success?.jwtToken, `is`(JwtEncoder.encode(username)))
    }

    @Test
    @Throws
    fun login_Foutgegeven_GeeftFoutmelding(){
        loginViewModel.login("ejfojf", "ofna")
        assertThat(getValue(loginViewModel.loginResult).error, not(nullValue()))
    }
}