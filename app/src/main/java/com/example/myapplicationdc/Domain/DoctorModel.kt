package com.example.myapplicationdc.Domain

import android.os.Parcel
import android.os.Parcelable

data class DoctorModel (
    val Address: String = "",
    val Biography: String = "",
    val Id: Int = 0,
    val Name: String = "",
    val Picture: String = "",
    val Special: String = "",
    val Experience: Int = 0,
    val Location: String = "",
    val Mobile: String = "",
    val Patients: String = "",
    val Rating: Double = 0.0,
    val Site: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Address)
        parcel.writeString(Biography)
        parcel.writeInt(Id)
        parcel.writeString(Name)
        parcel.writeString(Picture)
        parcel.writeString(Special)
        parcel.writeInt(Experience)
        parcel.writeString(Location)
        parcel.writeString(Mobile)
        parcel.writeString(Patients)
        parcel.writeDouble(Rating)
        parcel.writeString(Site)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<DoctorModel> {
        override fun createFromParcel(parcel: Parcel): DoctorModel = DoctorModel(parcel)

        override fun newArray(size: Int): Array<DoctorModel?> = arrayOfNulls(size)
    }
}