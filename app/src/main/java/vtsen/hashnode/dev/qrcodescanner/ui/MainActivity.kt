package vtsen.hashnode.dev.qrcodescanner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import vtsen.hashnode.dev.qrcodescanner.ui.screens.QRCodeScannerScreen
import vtsen.hashnode.dev.qrcodescanner.ui.theme.QRCodeScannerTheme

const val URL_KEY: String = "UrlKey"

class MainActivity : ComponentActivity() {

    private var urlText by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                urlText = urlText,
                onUrlTextUpdate = {
                    urlText = it
                }
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(URL_KEY, urlText)
        super.onSaveInstanceState(outState) // need to be called last
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoreUrlText = savedInstanceState.getString(URL_KEY)

        if(restoreUrlText != null) urlText = restoreUrlText
    }
}

@Composable
fun MainScreen(
    useSystemUIController: Boolean = true,
    urlText:String,
    onUrlTextUpdate: (String) -> Unit) {

    QRCodeScannerTheme(useSystemUIController = useSystemUIController) {
        QRCodeScannerScreen(urlText, onUrlTextUpdate)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(useSystemUIController = false, urlText= "", onUrlTextUpdate = {})
}