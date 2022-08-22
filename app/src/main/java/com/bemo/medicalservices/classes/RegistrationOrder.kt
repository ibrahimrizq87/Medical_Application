package com.bemo.medicalservices.classes

class RegistrationOrder {
    var name:String? = null
    var age :String? = null
    var phone :String? = null
    var placeName :String? = null

    constructor()
    constructor(name:String?,age :String?,Phone :String?,placeName :String?){
        this.name=name
        this.age=age
        this.placeName=placeName
        this.phone=Phone



    }
}