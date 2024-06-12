package com.dico.noteroom.ui.insert

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dico.noteroom.R
import com.dico.noteroom.database.Note
import com.dico.noteroom.databinding.ActivityNoteAddUpdateBinding
import com.dico.noteroom.helper.DateHelper
import com.dico.noteroom.helper.ViewModelFactory

class NoteAddUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit: Boolean = false
    private var note: Note? = null

    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel
    private lateinit var binding: ActivityNoteAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAddUpdateViewModel = obtainViewModel(this@NoteAddUpdateActivity)

        note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_NOTE, Note::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_NOTE)
        }

        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }

        val actionBarTitle: String
        val buttonTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            buttonTitle = getString(R.string.change)
            if (note != null) {
                note?.let { note ->
                    binding.edtTitle.setText(note.title)
                    binding.edtDescription.setText(note.description)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            buttonTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnSubmit.text = buttonTitle

        binding.btnSubmit.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding.edtTitle.error = getString(R.string.empty)
                }

                description.isEmpty() -> {
                    binding.edtDescription.error = getString(R.string.empty)
                }

                else -> {
                    note.let { note ->
                        note?.title = title
                        note?.description = description

                        if (note != null) {
                            if (isEdit) {
                                noteAddUpdateViewModel.update(note)
                                showToast(getString(R.string.changed))
                            } else {
                                note.date = DateHelper.getCurrentDate()
                                noteAddUpdateViewModel.insert(note)
                                showToast(getString(R.string.added))
                            }
                        }
                    }
                    finish()
                }
            }
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory)[NoteAddUpdateViewModel::class.java]
    }
}