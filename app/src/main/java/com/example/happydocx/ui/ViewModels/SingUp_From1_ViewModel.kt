package com.example.happydocx.ui.ViewModels

import androidx.lifecycle.ViewModel
import com.example.happydocx.ui.uiStates.AddressType
import com.example.happydocx.ui.uiStates.BlooadGroupDropDown
import com.example.happydocx.ui.uiStates.ContactInformation
import com.example.happydocx.ui.uiStates.DepartmentDropDown
import com.example.happydocx.ui.uiStates.GenderDropDown
import com.example.happydocx.ui.uiStates.PersonalInformation
import com.example.happydocx.ui.uiStates.ProfessionalDetail
import com.example.happydocx.ui.uiStates.SalutationDropDownState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.exp

class SingUp_From1_ViewModel : ViewModel() {


    // Personal information state
    private var personalInformationState = MutableStateFlow(PersonalInformation())
    val _personalInformationState = personalInformationState.asStateFlow()

    // personal detail state
    private var professionalDetailState = MutableStateFlow(ProfessionalDetail())
    val _professionalDetailState = professionalDetailState.asStateFlow()

    // contact information state
    private var contactInformationState = MutableStateFlow(ContactInformation())
    val _contactInformationState = contactInformationState.asStateFlow()

    // salutation state
    private var salutationState = MutableStateFlow(SalutationDropDownState())
    val _salutationState = salutationState.asStateFlow()
    val salutationItemList = listOf("Dr.","Prof.","Mr.","Mrs","Ms.")

    //Gender state
    private var genderState = MutableStateFlow(GenderDropDown())
    val _genderState = genderState.asStateFlow()
    val genderList = listOf("Male","Female","Other")


    // Department state
    private var departmentState = MutableStateFlow(DepartmentDropDown())
    val _departmentState = departmentState.asStateFlow()
    val departmentList = listOf("Cardiology","Orthopedic","Radiology","Dermatology","General Surgery","Casuality","Neurology","Oncology","Gynecology","ENT(Ear,Nose,Throat)")


    // Blood Group state
    private var bloodGroupState = MutableStateFlow(BlooadGroupDropDown())
    val _bloodGroupState = bloodGroupState.asStateFlow()
    val bloodGroupList = listOf(  "A+","B+","A-","B-","AB+","AB-","O+","O-"  )

    // address state
    private var addressState = MutableStateFlow(AddressType())
    val _addressState = addressState.asStateFlow()
    val addressList = listOf("Permanent","Temporary")

    fun onFirstNameChanged(firstName:String){
       personalInformationState.update { it->
           it.copy(
               firstName = firstName
           )
       }
    }
    fun onMiddleNameChanged(middleName:String){
       personalInformationState.update{it->
           it.copy(
               middleName = middleName
           )
       }
    }
    fun onLastNameChanged(lastName:String){
       personalInformationState.update{it->
           it.copy(
               lastName = lastName
           )
       }
    }

    fun onContactNumberChanged(newPhoneNumber:String){
        professionalDetailState.update{it->
            it.copy(
                contactNumber = newPhoneNumber
            )
        }
    }

    fun onEmailChanged(newEmail:String){
        professionalDetailState.update{it->
            it.copy(
                email = newEmail
            )
        }
    }

    fun onAddressLineOneChanged(newAddress1:String){
        contactInformationState.update { it->
            it.copy(
                addressLineOne = newAddress1
            )
        }
    }

    fun onAddressLineTwoChanged(newAddress2:String){
       contactInformationState.update{it->
           it.copy(
               addressLineTwo = newAddress2
           )
       }
    }
    fun city(newCity:String){
        contactInformationState.update { it->
            it.copy(
                city = newCity
            )
        }
    }
    fun state(newState:String){
      contactInformationState.update {
          it.copy(
              state = newState
          )
      }
    }
    fun district(newDistrict:String){
     contactInformationState.update {
         it.copy(
             district = newDistrict
         )
     }
    }
    fun zipCode(newZipCode:String){
       contactInformationState.update {
           it.copy(
               zipCode = newZipCode
           )
       }
    }
    fun country(newCountry:String){
       contactInformationState.update {
           it.copy(
               country = newCountry
           )
       }
    }
    fun clinicLocationUrl(Url:String){
       contactInformationState.update {
           it.copy(
               clinicLocationUrl = Url
           )
       }
    }

    fun onSalutationDropDownPressed(newValue:Boolean){
        salutationState.update { it->
            it.copy(
                expandedState = newValue,
                optionList = salutationItemList
            )
        }
    }
    fun onSalutationItemClicked(newItem:String){
        salutationState.update { it->
            it.copy(
                selectedOptions = newItem,
                expandedState = false
            )
        }
    }

    fun onGenderDropDownPressed(newValue:Boolean){
        genderState.update { it->
            it.copy(
                expandedState = newValue,
                optionList = genderList
            )
        }
    }
    fun onGenderItemClicked(newItem:String){
        genderState.update { it->
            it.copy(
                selectedOptions = newItem,
                expandedState = false
            )
        }
    }

    fun onDepartmentDropDownPressed(newValue:Boolean){
        departmentState.update { it->
            it.copy(
                expandedState = newValue,
                optionList = departmentList
            )
        }
    }
    fun onDepartmentItemClicked(newItem:String){
        departmentState.update{it->
            it.copy(
                selectedOptions =  newItem,
                expandedState = false
            )
        }
    }

    fun onBloodGroupDropDownPressed(newValue:Boolean){
        bloodGroupState.update { it->
            it.copy(
                expandedState = newValue,
                optionList = bloodGroupList
            )
        }
    }

    fun onBloodGroupItemClicked(newItem:String){
        bloodGroupState.update { it->
            it.copy(
                selectedOptions = newItem,
                expandedState = false
            )
        }
    }


    fun onAddressDropDownPressed(newValue:Boolean){
        addressState.update { it->
            it.copy(
                expandedState = newValue,
                optionList = addressList
            )
        }
    }

    fun onAddressItemClicked(newItem:String){
        addressState.update { it->
            it.copy(
                selectedOptions = newItem,
                expandedState = false
            )
        }
    }
}