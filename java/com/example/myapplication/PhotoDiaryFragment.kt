package com.example.myapplication

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentPhotoDiaryBinding
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.activityViewModels


class PhotoDiaryFragment : Fragment() {

    private var _binding: FragmentPhotoDiaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageCapture: ImageCapture
    private var cameraProvider: ProcessCameraProvider? = null

    private lateinit var spinnerPlace: Spinner
    private val viewModel: PlaceViewModel by activityViewModels()
    private val diaryViewModel: PhotoDiaryViewModel by activityViewModels()


    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.all { it.value }
        binding.button.isEnabled = granted
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermissions()

        spinnerPlace = binding.spinnerPlace

// 從 ViewModel 取得景點資料並設到 Spinner
        viewModel.places.observe(viewLifecycleOwner) { places ->
            val placeNames = places.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, placeNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPlace.adapter = adapter
        }


        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
        }, ContextCompat.getMainExecutor(requireContext()))

        binding.button.setOnClickListener {
            startCameraPreview()
            binding.button2.isEnabled = true
            binding.button3.isEnabled = true
        }

        binding.button2.setOnClickListener {
            stopCameraPreview()
        }

        binding.button3.setOnClickListener {
            takePhoto()
        }
        val imgTween=binding.imageView

        val btnAlpha=binding.btnAlpha
        val btnScale=binding.btnScale
        val btnTranslate=binding.btnTranslate
        val btnRotate=binding.btnRotate




        btnAlpha.setOnClickListener {
            val anim= AlphaAnimation(1.0f,0.2f)//起始與結束透明度
            anim.duration=1000//動畫一秒
            imgTween.startAnimation(anim)//執行動畫
        }
        btnScale.setOnClickListener {
            val anim= ScaleAnimation(1.0f,1.5f,1.0f,1.5f)
            //X起始 結束比例 Y起始 結束比例
            anim.duration=1000
            imgTween.startAnimation(anim)
        }
        btnTranslate.setOnClickListener {
            val anim= TranslateAnimation(0f,100f,0f,-100f)
            //X起點 終點 Y起點 終點
            anim.duration=1000
            imgTween.startAnimation(anim)
        }
        btnRotate.setOnClickListener {
            val anim= RotateAnimation(
                0f,360f,//起始 結束角度
                RotateAnimation.RELATIVE_TO_SELF,//X以自身位置旋轉
                0.5f,//X旋轉中心點
                RotateAnimation.RELATIVE_TO_SELF,//Y以自身位置旋轉
                0.5f//Y旋轉中心點
            )
            anim.duration=1000
            imgTween.startAnimation(anim)
        }
    }

    private fun requestPermissions() {
        val permissions = mutableListOf(Manifest.permission.CAMERA)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        cameraPermissionLauncher.launch(permissions.toTypedArray())
    }

    private fun startCameraPreview() {
        val cameraProvider = cameraProvider ?: return

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(binding.previewView.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder()
            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            .build()

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview, imageCapture)
    }

    private fun stopCameraPreview() {
        cameraProvider?.unbindAll()
        binding.button2.isEnabled = false
        binding.button3.isEnabled = false
    }

    private fun takePhoto() {
        val name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
            .format(System.currentTimeMillis()) + ".jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            requireContext().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e("PhotoDiary", "拍照失敗：${exception.message}", exception)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Log.d("PhotoDiary", "圖片儲存成功：${output.savedUri}")
                    binding.imageView.setImageURI(output.savedUri)

                    // 取得目前 Spinner 選的景點名稱
                    val placeName = spinnerPlace.selectedItem as? String ?: "未知景點"

                    // 取得現在日期字串
                    val dateStr = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(Date())

                    // 建立一筆日記
                    // 建立一筆日記資料
                    val newEntry = PhotoDiaryEntry(
                        imageUri = output.savedUri.toString(),
                        placeName = placeName,
                        date = dateStr
                    )

                    // 新增到 ViewModel
                    diaryViewModel.addEntry(newEntry)

                    // 你也可以在這裡用 Toast 提示成功
                    Toast.makeText(requireContext(), "照片日記已新增", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
