package com.example.starsign.register


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.starsign.MainActivity
import com.example.starsign.R
import com.example.starsign.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        val viewModel: RegisterViewModel by viewModel()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.fragment_register)
        registratebutton.setOnClickListener{view:View ->
            viewModel.voegRegistratieToe(binding.usernamefield.text.toString(), binding.passwordfield.text.toString(), binding.repeatpasswordfield.text.toString())
        }
        viewModel.jwtResponse.observe(this, Observer{
            if(it.error!=null){
                errortext.text = it.error.message
                errortext.visibility = View.VISIBLE
            }
            if(it.success!=null){
                val sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE)
                val mEditor = sharedPreferences.edit()
                mEditor.putString("currentUser", it.success.jwtToken)
                mEditor.apply()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("hi", "HI")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        })
    }

}
