package com.bemo.medicalservices.classes

class MedicalOrderClass {
    var medicineName:String? = null
    var userAddress :String? = null
    var phone :String? = null
    var city :String? = null
    constructor()
    constructor(name:String?,address :String?,Phone :String?,city :String?){
        this.medicineName=name
        this.city=city
        this.userAddress=address
        this.phone=Phone


    }
}