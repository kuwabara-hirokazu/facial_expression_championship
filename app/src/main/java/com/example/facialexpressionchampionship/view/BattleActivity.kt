package com.example.facialexpressionchampionship.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.ActivityBattleBinding
import com.example.facialexpressionchampionship.extension.showFragment
import com.example.facialexpressionchampionship.extension.showToast
import com.example.facialexpressionchampionship.viewmodel.BattleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BattleActivity : AppCompatActivity() {

    companion object {
        // 必要なパーミッションのリスト
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val themeList = listOf("怒り", "軽蔑", "嫌悪", "恐れ", "幸せ", "通常", "悲しみ", "驚き")
    }

    private lateinit var binding: ActivityBattleBinding

    private val viewModel : BattleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_battle)

        viewModel.battleTheme.set(themeList.shuffled()[0])

        if (allPermissionsGranted()) {
            CameraFragment().showFragment(supportFragmentManager, binding.battleLayout.id, false)
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    // 全てのパーミッションが許可されているか
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                CameraFragment().showFragment(supportFragmentManager, binding.battleLayout.id, false)
            } else {
                showToast(R.string.camera_permission_message)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // データが消えないようにデフォルトの戻るボタンで戻れないようにする
    override fun onBackPressed() {}
}
