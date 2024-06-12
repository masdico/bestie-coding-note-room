package com.dico.noteroom.helper

import androidx.recyclerview.widget.DiffUtil
import com.dico.noteroom.database.Note

class NoteDiffCallback(val oldNoteList: List<Note>, val newNoteList: List<Note>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size

    override fun getNewListSize(): Int = newNoteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.title == newNote.title && oldNote.description == newNote.description
    }
}