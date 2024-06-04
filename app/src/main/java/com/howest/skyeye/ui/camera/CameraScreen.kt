package com.howest.skyeye.ui.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.howest.skyeye.ui.NavigationDestination
import howest.nma.skyeye.R
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object CameraDestination : NavigationDestination {
    override val route: String = "camera"
    override val title: String = "Camera"
}

@Composable
fun CameraScreen(navigateBack: () -> Unit) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    var camera by remember { mutableStateOf<Camera?>(null) }

    LaunchedEffect(lensFacing) {
        camera = initializeCamera(context, lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    val scaleGestureDetector = initializeScaleGestureDetector(context, camera)
    setTouchListener(previewView, scaleGestureDetector)

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView(
            { previewView },
            modifier = Modifier.fillMaxSize()
        )
        CameraTopBar(navigateBack)
        camera?.let { ZoomSlider(it.cameraControl) }
        InfoTypeToggle()
    }
}

private suspend fun initializeCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    cameraxSelector: CameraSelector,
    preview: Preview,
    imageCapture: ImageCapture
): Camera {
    val cameraProvider = context.getCameraProvider()
    cameraProvider.unbindAll()
    return cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
}

private fun initializeScaleGestureDetector(context: Context, camera: Camera?): ScaleGestureDetector {
    val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val currentZoomRatio = camera?.cameraInfo?.zoomState?.value?.zoomRatio ?: 0F
            val delta = detector.scaleFactor

            camera?.cameraControl?.setZoomRatio(currentZoomRatio * delta)
            return true
        }
    }
    return ScaleGestureDetector(context, listener)
}

private fun setTouchListener(view: View, scaleGestureDetector: ScaleGestureDetector) {
    view.setOnTouchListener { v, event ->
        val wasEventHandled = scaleGestureDetector.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_UP && !wasEventHandled) {
            v.performClick()
        }
        return@setOnTouchListener true
    }
}

@Composable
fun BoxScope.CameraTopBar(navigateBack: () -> Unit) {
    IconButton(
        onClick = navigateBack,
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(16.dp)
            .size(45.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = "close",
            modifier = Modifier.size(45.dp)
        )
    }
    Image(
        painter = painterResource(id = R.drawable.logowithname),
        contentDescription = "SkyEye",
        modifier = Modifier
            .align(Alignment.TopCenter)
            .offset(y = (-40).dp)
            .size(150.dp)
    )
}

@Composable
fun BoxScope.ZoomSlider(cameraControl: CameraControl) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Slider(
        value = sliderPosition,
        onValueChange = { newValue ->
            sliderPosition = newValue
            cameraControl.setLinearZoom(newValue)
        },
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .offset(x = (90).dp, y = (-115).dp)
            .rotate(270f)
            .width(250.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoTypeToggle() {
    val options = listOf("Overview", "Details")
    var selectedIndex by remember { mutableStateOf(0) }
    SingleChoiceSegmentedButtonRow (
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                border = BorderStroke(0.dp, Color.Transparent),
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size, baseShape = MaterialTheme.shapes.small),
                colors = SegmentedButtonDefaults.colors().copy(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    inactiveContainerColor = Color.White,
                    activeContentColor = Color.Black,
                    inactiveContentColor = Color.Black
                ),
                onClick = { selectedIndex = index },
                selected = index == selectedIndex
            ) {
                Text(label)
            }
        }
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

@Composable
fun OpenCamera(context: Context, navigateTo: (route: String) -> Unit, navigateBack: () -> Unit) {
    var permissionGranted by remember { mutableStateOf(false) }

    val cameraPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
    }

    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
            CameraScreen(navigateBack)
        } else -> {
        LaunchedEffect(Unit) {
            cameraPermissionRequest.launch(Manifest.permission.CAMERA)
        }
    }
    }

    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            navigateTo(CameraDestination.route)
        }
    }
}