package com.bemo.medicalservices.classes

class RegistrationList {
    var name:String? = null
    var address :String? = null
    var phone :String? = null
    var city :String? = null
    var from :String? = null
    var to :String? = null
    constructor()
    constructor(name:String?,address :String?,Phone :String?,city :String?,from :String?,to :String?){
        this.name=name
        this.city=city
        this.address=address
        this.phone=Phone
        this.from=from
        this.to=to


    }
}