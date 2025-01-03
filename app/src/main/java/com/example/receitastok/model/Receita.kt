package com.example.receitastok.model

import android.os.Parcel
import android.os.Parcelable

data class Receita(
    var id: String="",
    val titulo: String = "",
    val descricao: String = "",
    val tags: List<String> = listOf(),
    val videoName: String = "",
    var likes: ArrayList<String> = arrayListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: listOf(),
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(titulo)
        parcel.writeString(descricao)
        parcel.writeStringList(tags)
        parcel.writeString(videoName)
        parcel.writeStringList(likes)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Receita> {
        override fun createFromParcel(parcel: Parcel): Receita = Receita(parcel)
        override fun newArray(size: Int): Array<Receita?> = arrayOfNulls(size)
    }
}
