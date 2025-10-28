package com.example.happydocx.ui.uiStates

data class PersonalInformation(
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",

    )

data class ProfessionalDetail(
    val contactNumber: String = "",
    val email: String = "",

    )

data class ContactInformation(
    val addressLineOne: String = "",
    val addressLineTwo: String = "",
    val city: String = "",
    val state: String = "",
    val district: String = "",
    val zipCode: String = "",
    val country: String = "",
    val clinicLocationUrl: String = ""
)

data class SalutationDropDownState(
    val expandedState:Boolean = false,
    val optionList:List<String> = emptyList(),
    val selectedOptions:String=""
)

data class GenderDropDown(
    val expandedState:Boolean = false,
    val optionList:List<String> = emptyList(),
    val selectedOptions:String=""
)

data class DepartmentDropDown(
    val expandedState:Boolean = false,
    val optionList:List<String> = emptyList(),
    val selectedOptions:String=""
)

data class BlooadGroupDropDown(
    val expandedState:Boolean = false,
    val optionList:List<String> = emptyList(),
    val selectedOptions:String=""
)

data class AddressType(
    val expandedState:Boolean = false,
    val optionList:List<String> = emptyList(),
    val selectedOptions:String=""
)

