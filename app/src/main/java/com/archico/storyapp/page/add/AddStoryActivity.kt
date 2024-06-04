package com.archico.storyapp.page.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.archico.storyapp.R
import com.archico.storyapp.data.ResultState
import com.archico.storyapp.databinding.ActivityAddStoryBinding
import com.archico.storyapp.page.ViewModelFactory
import com.archico.storyapp.page.BaseClass
import com.archico.storyapp.page.camera.CameraActivity
import com.archico.storyapp.page.camera.CameraActivity.Companion.CAMERAX_RESULT
import com.archico.storyapp.page.home.MainActivity
import com.archico.storyapp.utils.reduceFileImage
import com.archico.storyapp.utils.uriToFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryActivity : BaseClass() , OnMapReadyCallback {

    private lateinit var addStoryBinding: ActivityAddStoryBinding
    private lateinit var mMap: GoogleMap
    private var currentImageUri: Uri? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapFragment: SupportMapFragment
    private var lat:Float? = null
    private var lon:Float? = null

    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val addStoryViewModel by viewModels<AddStoryViewModel> {
        viewModelFactory
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission approved", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addStoryBinding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(addStoryBinding.root)
        setActionBar()

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@AddStoryActivity)
        mapFragment.view?.visibility = android.view.View.GONE
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        addStoryBinding.apply {
            btnGallery.setOnClickListener { startGallery() }
            btnCamera.setOnClickListener {
                startCamera()
            }
            btnSubmit.setOnClickListener {
                if(etDescription.text.toString().isEmpty()){
                    showToast(getString(R.string.empty_description_warning))
                    return@setOnClickListener
                }
                postStory()
            }
            cboxLocation.setOnClickListener {
                if(cboxLocation.isChecked){
                    getMyLocation()
                }else{
                    mapFragment.view?.visibility = android.view.View.GONE
                }
            }
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialog_loading)
        val dialog: AlertDialog = builder.create()
        val intentHome = Intent(this,MainActivity::class.java)
        addStoryViewModel.responseResult.observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    dialog.show()
                }
                is ResultState.Success -> {
                    dialog.dismiss()
                    intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intentHome)
                    finish()
                }
                is ResultState.Error -> {
                    dialog.dismiss()
                    showToast(result.error)
                }
            }
        }

    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            addStoryBinding.imgPreview.setImageURI(it)
        }
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun postStory() {
        currentImageUri?.let { uri ->
            val imgFile = uriToFile(uri, this).reduceFileImage()
            val description = addStoryBinding.etDescription.text.toString()
            val requestBodyDescription = description.toRequestBody("text/plain".toMediaType())
            val latRequestBody = lat?.toString()?.toRequestBody("text/plain".toMediaType())
            val lonRequestBody = lon?.toString()?.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imgFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imgFile.name,
                requestImageFile
            )
            addStoryViewModel.addStory(multipartBody,requestBodyDescription,latRequestBody,lonRequestBody)
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val myHome = LatLng(2.9755181, 99.0750768)
        mMap.addMarker(
            MarkerOptions()
                .position(myHome)
                .title("Archico's Headquarter")
                .snippet("Pematangsiantar, Sumatera Utara")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myHome, 15f))
    }

    private val requestPermissionLauncherLocation =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }
    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            mapFragment.view?.visibility = android.view.View.VISIBLE
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    lat = location.latitude.toFloat()
                    lon = location.longitude.toFloat()
                } else {
                    Toast.makeText(
                        this@AddStoryActivity,
                        "Location Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncherLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val CAMERA_X_RESULT = 200
    }
}