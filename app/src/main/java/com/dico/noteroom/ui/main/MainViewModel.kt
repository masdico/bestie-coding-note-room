package com.dico.noteroom.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dico.noteroom.database.Note
import com.dico.noteroom.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val noteRepo = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> = noteRepo.getAllNotes()
}