package ru.fnkr.drivenextapp.presentation.auth.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.R
import ru.fnkr.drivenextapp.common.utils.launchNoConnectionIfNeeded
import ru.fnkr.drivenextapp.databinding.SignUp3Binding
import ru.fnkr.drivenextapp.presentation.auth.common.SignUpData
import java.io.File
import java.util.Calendar

class SignUp3Activity : AppCompatActivity() {

    private lateinit var binding: SignUp3Binding
    private val vm: SignUpViewModel by viewModels()

    private var data: SignUpData = SignUpData()

    private enum class DocType { PROFILE, PASSPORT, LICENSE }
    private var currentTarget: DocType? = null
    private var tempCameraUri: Uri? = null

    private val pickImagePhoto =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            handleGalleryResult(uri)
        }

    private val pickImageOpenDoc =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                contentResolver.takePersistableUriPermission(
                    it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            handleGalleryResult(uri)
        }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
        if (ok) {
            handleCameraResult(tempCameraUri)
        } else {
            tempCameraUri = null
        }
    }

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) openCameraForCurrentTarget() else {
            Snackbar.make(binding.root, R.string.camera_permission_denied, Snackbar.LENGTH_LONG)
                .setAction(R.string.open_settings) {
                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.parse("package:$packageName")
                    })
                }.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUp3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState?.getSerializable(EXTRA_SIGN_UP_DATA, SignUpData::class.java)
                ?: intent.getSerializableExtra(EXTRA_SIGN_UP_DATA, SignUpData::class.java)
                ?: SignUpData()
        } else {
            @Suppress("DEPRECATION")
            savedInstanceState?.getSerializable(EXTRA_SIGN_UP_DATA) as? SignUpData
                ?: @Suppress("DEPRECATION")
                intent.getSerializableExtra(EXTRA_SIGN_UP_DATA) as? SignUpData
                ?: SignUpData()
        }

        restoreImagesFromData()

        binding.imgProfilePlus.setOnClickListener { chooseSource(DocType.PROFILE) }
        binding.llUploadPassport.setOnClickListener { chooseSource(DocType.PASSPORT) }
        binding.llUploadLicense.setOnClickListener { chooseSource(DocType.LICENSE) }

        binding.ilEditLicense.setText(data.licenseNumber.orEmpty())
        binding.ilEditDate.setText(data.licenseDate.orEmpty())

        binding.imgBack.setOnClickListener {
            val i = Intent(this@SignUp3Activity, SignUp2Activity::class.java)
                .putExtra(EXTRA_SIGN_UP_DATA, data)
            startActivity(i)
        }

        binding.btnContinue.setOnClickListener {
            launchNoConnectionIfNeeded()
            if (data.passportPhotoUri == null || data.licensePhotoUri == null) {
                Snackbar.make(binding.root, R.string.upload_docs_required, Snackbar.LENGTH_SHORT)
                    .show()
            }

            val licenseNumber = binding.ilEditLicense.text?.toString().orEmpty()
            val licenseDate = binding.ilEditDate.text?.toString().orEmpty()

            vm.submitThird(licenseNumber, licenseDate)
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui3.collect { s ->
                    binding.ilLicense.error = s.licenseNumberError
                    binding.ilDate.error = s.licenseDateError

                    if (s.generalError != null)
                        Snackbar.make(binding.root, s.generalError, Snackbar.LENGTH_SHORT).show()

                    if (s.isAuthorized) {
                        data = data.copy(
                            licenseNumber = binding.ilEditLicense.text?.toString(),
                            licenseDate = binding.ilEditDate.text?.toString(),
                        )
                        val i = Intent(this@SignUp3Activity, SignUpSuccessActivity::class.java)
                            .putExtra(EXTRA_SIGN_UP_DATA, data)
                        startActivity(i)
                    }
                }
            }
        }

        binding.ilDate.setStartIconOnClickListener {
            showDatePicker()
        }

        binding.ilEditDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val formatted = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.ilEditDate.setText(formatted)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }

    /** ----- Helpers ----- */

    private fun chooseSource(target: DocType) {
        currentTarget = target
        val items = arrayOf(getString(R.string.take_photo), getString(R.string.choose_from_gallery))
        AlertDialog.Builder(this)
            .setTitle(
                when (target) {
                    DocType.PROFILE -> getString(R.string.profile_photo)
                    DocType.PASSPORT -> getString(R.string.passport_photo)
                    DocType.LICENSE -> getString(R.string.license_photo)
                }
            )
            .setItems(items) { _, which ->
                when (which) {
                    0 -> openCameraForCurrentTarget()
                    1 -> openGalleryForCurrentTarget()
                }
            }
            .show()

    }

    private fun openGalleryForCurrentTarget() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Импортируй PickVisualMediaRequest!
            pickImagePhoto.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            pickImageOpenDoc.launch(arrayOf("image/*"))
        }
    }

    private fun openCameraForCurrentTarget() {
        // На части устройств без явного разрешения камера Intent не откроется
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            checkSelfPermission(android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            val uri = createImageUri("${currentTarget?.name?.lowercase()}_${System.currentTimeMillis()}.jpg")
            tempCameraUri = uri
            takePicture.launch(uri)
        } else {
            requestCameraPermission.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun createImageUri(fileName: String): Uri {
        // сохраняем в app-specific Pictures (видно только твоему приложению)
        val imagesDir = getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
            ?: filesDir
        val imageFile = File(imagesDir, fileName)
        return FileProvider.getUriForFile(this, "$packageName.fileprovider", imageFile)
    }

    private fun handleGalleryResult(uri: Uri?) {
        uri ?: return
        when (currentTarget) {
            DocType.PROFILE -> {
                data = data.copy(profilePhotoUri = uri.toString())
                Glide.with(this).load(uri).into(binding.imgProfile)
            }
            DocType.PASSPORT -> {
                data = data.copy(passportPhotoUri = uri.toString())
                Glide.with(this).load(uri).into(binding.ivPossport)
                binding.tvUploadPassport.text = getString(R.string.uploaded)
            }
            DocType.LICENSE -> {
                data = data.copy(licensePhotoUri = uri.toString())
                Glide.with(this).load(uri).into(binding.ivLicense)
                binding.tvUploadLicense.text = getString(R.string.uploaded)
            }
            else -> Unit
        }
    }

    private fun handleCameraResult(uri: Uri?) {
        uri ?: return
        when (currentTarget) {
            DocType.PROFILE -> {
                data = data.copy(profilePhotoUri = uri.toString())
                Glide.with(this).load(uri).into(binding.imgProfile)
            }
            DocType.PASSPORT -> {
                data = data.copy(passportPhotoUri = uri.toString())
                Glide.with(this).load(uri).into(binding.ivPossport)
                binding.tvUploadPassport.text = getString(R.string.uploaded)
            }
            DocType.LICENSE -> {
                data = data.copy(licensePhotoUri = uri.toString())
                Glide.with(this).load(uri).into(binding.ivLicense)
                binding.tvUploadLicense.text = getString(R.string.uploaded)
            }
            else -> Unit
        }
        tempCameraUri = null
    }

    private fun restoreImagesFromData() {
        data.profilePhotoUri?.let {
            Glide.with(this).load(Uri.parse(it)).into(binding.imgProfile)
        }
        data.passportPhotoUri?.let {
            Glide.with(this).load(Uri.parse(it)).into(binding.ivPossport)
            binding.tvUploadPassport.text = getString(R.string.uploaded)
        }
        data.licensePhotoUri?.let {
            Glide.with(this).load(Uri.parse(it)).into(binding.ivLicense)
            binding.tvUploadLicense.text = getString(R.string.uploaded)
        }
    }

}
