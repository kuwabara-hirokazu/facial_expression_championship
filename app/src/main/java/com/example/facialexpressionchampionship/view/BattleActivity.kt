package com.example.facialexpressionchampionship.view

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.databinding.ActivityBattleBinding
import com.example.facialexpressionchampionship.extension.hasPermission
import com.example.facialexpressionchampionship.extension.showError
import com.example.facialexpressionchampionship.extension.showFragment
import com.example.facialexpressionchampionship.extension.showToast
import com.example.facialexpressionchampionship.viewmodel.BattleViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class BattleActivity : AppCompatActivity() {

    companion object {
        // 必要なパーミッションのリスト
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private lateinit var binding: ActivityBattleBinding

    private val viewModel : BattleViewModel by viewModels()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_battle)

        viewModel.setup()

        viewModel.decidedTheme
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                // 全てのパーミッションが許可されているか
                if (REQUIRED_PERMISSIONS.all { hasPermission(it) }) {
                    CameraFragment().showFragment(supportFragmentManager, binding.battleLayout.id, false)
                } else {
                    ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
                }
            }
            .addTo(disposable)

        viewModel.error
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { binding.root.showError(it) }
            .addTo(disposable)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (REQUIRED_PERMISSIONS.all { hasPermission(it) }) {
                CameraFragment().showFragment(supportFragmentManager, binding.battleLayout.id, false)
            } else {
                showToast(R.string.permission_error_message)
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

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
