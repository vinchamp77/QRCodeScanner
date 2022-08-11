package vtsen.hashnode.dev.qrcodescanner

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QRCodeAnalyzer(
    private val urlCallback: (String) -> Unit
) : ImageAnalysis.Analyzer  {

    override fun analyze(image: ImageProxy) {
        val bytes = image.planes.first().buffer.toByteArray()

        val source = PlanarYUVLuminanceSource(
            bytes,
            image.width,
            image.height,
            0,
            0,
            image.width,
            image.height,
            false
        )

        val binaryBmp = BinaryBitmap(HybridBinarizer(source))
        val multiFormatReader = MultiFormatReader()
        multiFormatReader.setHints ( mapOf(
                DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
            )
        )

        try {
            val result = multiFormatReader.decode(binaryBmp)
            urlCallback(result.text)

        } catch (e: Exception) {

        } finally {
            image.close()
        }
    }
}

private fun ByteBuffer.toByteArray(): ByteArray {
    val byteArray = ByteArray(remaining())
    get(byteArray)
    return byteArray
}