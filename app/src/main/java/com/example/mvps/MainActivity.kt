package com.example.mvps

import android.Manifest
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.mvps.base.BaseActivity
import com.example.mvps.data.CityData
import com.example.mvps.interfaces.IHome
import com.example.mvps.jpush.TestJPushActivity
import com.example.mvps.presenter.HomePreserenter
import com.example.mvps.ui.CoordinatorActivity
import com.example.mvps.ui.IndexActivity
import com.example.mvps.ui.TongpaoActivity
import com.example.mvps.ui.easemob.EaseMobActivity
import com.example.mvps.ui.map.MapActivity
import com.github.dfqin.grantor.PermissionListener
import com.github.dfqin.grantor.PermissionsUtil

class MainActivity : BaseActivity<HomePreserenter?>(), IHome.View, View.OnClickListener {
    private var homePreserenter: HomePreserenter? = null
    private var btn: Button? = null
    private var btn_index: Button? = null
    private var btn_coord: Button? = null
    private var btn_jphan: Button? = null
    private var btn_map: Button? = null
    private var btn_nv: Button? = null
    override fun createPersenter(): HomePreserenter {
        return HomePreserenter(this)
    }

    override fun initData() {
        homePreserenter = HomePreserenter(this)
//        homePreserenter!!.getCity()
    }

    override fun initView() {
        PermissionsUtil.requestPermission(this@MainActivity, object : PermissionListener {
            override fun permissionGranted(permission: Array<String>) {
                Toast.makeText(this@MainActivity, "授权成功", Toast.LENGTH_SHORT).show()
            }

            override fun permissionDenied(permission: Array<String>) {
                finish()
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        btn = findViewById<View>(R.id.btn) as Button
        btn_index = findViewById<View>(R.id.btn_index) as Button
        btn_coord = findViewById<View>(R.id.btn_coord) as Button
        btn_jphan = findViewById<View>(R.id.btn_jphan) as Button
        btn_map = findViewById<View>(R.id.btn_map) as Button
        btn_nv = findViewById<View>(R.id.btn_nv) as Button
        btn!!.setOnClickListener(this)
        btn_index!!.setOnClickListener(this)
        btn_coord!!.setOnClickListener(this)
        btn_jphan!!.setOnClickListener(this)
        btn_map!!.setOnClickListener(this)
        btn_nv!!.setOnClickListener(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun fila(error: String) {}
    override fun getCityReturn(result: CityData) {
        Toast.makeText(this, result.result[0].city, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn -> startActivity(Intent(this, TongpaoActivity::class.java))
            R.id.btn_index -> startActivity(Intent(this, IndexActivity::class.java))
            R.id.btn_coord -> startActivity(Intent(this, CoordinatorActivity::class.java))
            R.id.btn_jphan -> startActivity(Intent(this, TestJPushActivity::class.java))
            R.id.btn_map -> startActivity(Intent(this, MapActivity::class.java))
            R.id.btn_nv -> startActivity(Intent(this, EaseMobActivity::class.java))
        }
    }
}
