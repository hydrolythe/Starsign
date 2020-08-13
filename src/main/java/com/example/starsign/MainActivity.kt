package com.example.starsign

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.starsign.databinding.ActivityMainBinding
import com.example.starsign.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var username: String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*        val sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        if(sharedPreferences.contains("currentUser")) {
            val key = sharedPreferences.getString("currentUser", "")
            val expiration = JwtUtils.getExpiracyDate(key ?: "")
            val mEditor = sharedPreferences.edit()
            if (expiration.before(Date())) {
                mEditor.remove("currentUser")
                mEditor.apply()
                startLogin()
            }
            username = JwtUtils.getUsername(key ?: "")
        }
        else{
            startLogin()
        }*/
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    fun startLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("hi", "HI")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
