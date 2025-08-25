package com.example.finalproject.main

import android.Manifest
import android.app.SearchManager
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject.FourFragment
import com.example.finalproject.R
import com.example.finalproject.note.ThreeFragment
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.graph.GraphFragment
import com.example.finalproject.login.AuthActivity
import com.example.finalproject.login.MyApplication
import com.example.finalproject.search.OneFragment
import com.example.finalproject.setting.SettingsActivity
import com.example.finalproject.setting.SettingsFragment
import com.example.finalproject.welfare.TwoFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlin.jvm.java


class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
    val fragments: List<Fragment>

    init {
        fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment(), FourFragment(),GraphFragment())
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}

class MainActivity : AppCompatActivity() {

    val TAG = "25android"
    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreference: SharedPreferences

    // onCreate -> onStart -> onResume
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Firebase.messaging.token.addOnSuccessListener {
//            Log.d("25android", it) // it은 token
//        }

        setSupportActionBar(binding.toolbar)

        //MyApplication.auth


        val permissionLaucher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if(it.all { permission -> permission.value != true}){
                Toast.makeText(this,"permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(
                this,
                "android.permission.POST_NOTIFICATIONS"
            ) != PackageManager.PERMISSION_GRANTED){
                // 승인 요청
                permissionLaucher.launch(arrayOf("android.permission.POST_NOTIFICATIONS"))
            }
        }

        binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> "영상 검색"
                1 -> "복지 정보"
                2 -> "커뮤니티"
                3 -> "메모"
                4 -> "통계"
                else -> "탭${position + 1}"
            }
        }.attach()


        //  설정
        val btnSettings = findViewById<ImageButton>(R.id.btn_settings)

        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        applyUserSettings()  // 이걸로 통합

        val showToolbar = sharedPreference.getBoolean("show_toolbar_text", true)
        if (showToolbar && MyApplication.checkAuth()) {
            binding.toolbar.title = "${MyApplication.email} 님"
        } else {
            binding.toolbar.title = ""
        }

    }

    // 로그인 menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.menu_login -> {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.findItem(R.id.menu_login)
        val user = MyApplication.auth.currentUser
        if (user != null && user.isEmailVerified) {
            item?.title = "로그아웃"
        } else {
            item?.title = "로그인"
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()

        //로그인 안 되어 있으면 AuthActivity로 이동
        if (!MyApplication.checkAuth()) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        //  로그인 상태 갱신
        val userEmail = MyApplication.auth.currentUser?.email
        if (userEmail != null) {
            MyApplication.email = userEmail
        }
        val showToolbar = sharedPreference.getBoolean("show_toolbar_text", true)
        if (showToolbar && MyApplication.checkAuth()) {
            binding.toolbar.title = "${MyApplication.email} 님"
        } else {
            binding.toolbar.title = ""
        }

        // 옵션 메뉴 상태도 갱신
        invalidateOptionsMenu()

        // 사용자 설정 반영 (폰트, 색상 등)
        applyUserSettings()
    }




    private fun applyUserSettings() {
        val fontSizeString = sharedPreference.getString("font_size", "15.0")
        val fontSize = fontSizeString?.toFloatOrNull() ?: 15f

        val themeColor = sharedPreference.getString("theme_color", "#00008B") ?: "#00008B"
        val fontColor = sharedPreference.getString("font_color", "#FFFFFF") ?: "#FFFFFF"



        // 툴바 배경색 적용
        binding.toolbar.setBackgroundColor(Color.parseColor(themeColor))

        // 툴바 글자색 (글자 색상 적용)
        binding.toolbar.setTitleTextColor(Color.parseColor(fontColor))


        // 하단 탭 텍스트 글자 크기 및 색상 반영
        for (i in 0 until binding.tabs.tabCount) {
            val tab = binding.tabs.getTabAt(i)
            val textView = TextView(this).apply {
                text = tab?.text?.toString()
                textSize = fontSize
                setTextColor(Color.parseColor(fontColor))
                gravity = Gravity.CENTER
            }
            tab?.customView = textView
        }
    }



}


