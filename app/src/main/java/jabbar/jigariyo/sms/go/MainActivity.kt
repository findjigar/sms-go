package jabbar.jigariyo.sms.go

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    companion object {
        private const val TAG = "SMS GO"
        private const val REQUEST_CODE = 767
    }

    private val permissions =
        arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendSmsButton.setOnClickListener {
            val permissionsGiven = checkOrAskPermission()
            if (permissionsGiven) {
                Snackbar.make(sendSmsButton, getString(R.string.send_sms), Snackbar.LENGTH_SHORT).show()
            }
        }
        checkOrAskPermission()
    }

    private fun hasBothPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, *permissions)
    }

    @AfterPermissionGranted(REQUEST_CODE)
    private fun checkOrAskPermission(): Boolean {
        if (hasBothPermissions()) {
            Snackbar.make(sendSmsButton, getString(R.string.required_perm_given), Snackbar.LENGTH_SHORT).show()
            return true
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.please_give_permission),
                REQUEST_CODE,
                *permissions
            )
        }
        return false
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:$requestCode:$perms.size")

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:$requestCode:$perms.size")
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, "onRationaleDenied:$requestCode")
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, "onRationaleAccepted:$requestCode")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // User returned from app settings screen
            if (hasBothPermissions()) {
                Snackbar.make(sendSmsButton, getString(R.string.returned_with_all_permissions), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}