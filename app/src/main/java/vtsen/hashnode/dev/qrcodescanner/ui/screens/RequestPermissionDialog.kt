package vtsen.hashnode.dev.qrcodescanner.ui.screens

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay

@Composable
fun RequestPermissionDialog(
    permission: String,
    onResult: (Boolean) -> Unit,
) {
    var launchEffectKey by remember {
        mutableStateOf(false)
    }

    var requestPermissionDelay by remember {
        mutableStateOf(0L)
    }

    val managedActivityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->

            onResult(isGranted)

            if(!isGranted) {
                requestPermissionDelay = 1000
                launchEffectKey = !launchEffectKey
            }

        }
    )

    LaunchedEffect(launchEffectKey) {
        delay(requestPermissionDelay)
        managedActivityResultLauncher.launch(permission)
    }
}

fun isPermissionGranted(context: Context, permission: String) : Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}


