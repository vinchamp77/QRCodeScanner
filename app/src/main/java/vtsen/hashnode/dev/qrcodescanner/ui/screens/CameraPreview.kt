package vtsen.hashnode.dev.qrcodescanner.ui.screens

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import vtsen.hashnode.dev.qrcodescanner.QRCodeAnalyzer

@Composable
fun CameraPreview(urlCallback: (String) -> Unit) {

    val lifeCycleOwner = LocalLifecycleOwner.current
    //val context = LocalContext.current

    AndroidView(
        factory = { context ->
            val previewView = PreviewView(context)
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.Builder().build()

            preview.setSurfaceProvider(previewView.surfaceProvider)

            val imageAnalysis = ImageAnalysis.Builder().build()

            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(context),
                QRCodeAnalyzer { url ->
                    urlCallback(url)
                }
            )

            ProcessCameraProvider.getInstance(context).get().bindToLifecycle(
                lifeCycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )

            previewView
        }
    )
}

