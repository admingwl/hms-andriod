package com.example.happydocx.ui.ViewModels

import androidx.lifecycle.ViewModel
import com.example.happydocx.ui.uiStates.FormInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SingUp_From1_ViewModel : ViewModel() {

    val salutationItemList = listOf("Dr.","Prof.","Mr.","Mrs","Ms.")
    val genderList = listOf("Male","Female","Other")
    val bloodGroupList = listOf(  "A+","B+","A-","B-","AB+","AB-","O+","O-"  )
    val departmentList = listOf("Cardiology","Orthopedic","Radiology","Dermatology","General Surgery","Casuality","Neurology","Oncology","Gynecology","ENT(Ear,Nose,Throat)")
    val addressList = listOf("Permanent","Temporary")

    // form state
    private var formState = MutableStateFlow(FormInformation())
    val _formState = formState.asStateFlow()


    fun onFirstNameChanged(firstName:String){
        formState.update { it->
           it.copy(
               firstName = firstName
           )
       }
    }
    fun onMiddleNameChanged(middleName:String){
        formState.update{it->
           it.copy(
               middleName = middleName
           )
       }
    }
    fun onLastNameChanged(lastName:String){
        formState.update{it->
           it.copy(
               lastName = lastName
           )
       }
    }

    fun onContactNumberChanged(newPhoneNumber:String){
        formState.update{it->
            it.copy(
                contactNumber = newPhoneNumber
            )
        }
    }

    fun onEmailChanged(newEmail:String){
        formState.update{it->
            it.copy(
                email = newEmail
            )
        }
    }

    fun onAddressLineOneChanged(newAddress1:String){
        formState.update { it->
            it.copy(
                addressLineOne = newAddress1
            )
        }
    }

    fun onAddressLineTwoChanged(newAddress2:String){
        formState.update{it->
           it.copy(
               addressLineTwo = newAddress2
           )
       }
    }
    fun city(newCity:String){
        formState.update { it->
            it.copy(
                city = newCity
            )
        }
    }
    fun state(newState:String){
        formState.update {
          it.copy(
              state = newState
          )
      }
    }
    fun district(newDistrict:String){
        formState.update {
         it.copy(
             district = newDistrict
         )
     }
    }
    fun zipCode(newZipCode:String){
        formState.update {
           it.copy(
               zipCode = newZipCode
           )
       }
    }
    fun country(newCountry:String){
        formState.update {
           it.copy(
               country = newCountry
           )
       }
    }
    fun clinicLocationUrl(Url:String){
        formState.update {
           it.copy(
               clinicLocationUrl = Url
           )
       }
    }

    fun onSalutationDropDownPressed(newValue:Boolean){
        formState.update { it->
            it.copy(
                salutationDropDownexpandedState = newValue,
                salutationoptionList = salutationItemList
            )
        }
    }
    fun onSalutationItemClicked(newItem:String){
        formState.update { it->
            it.copy(
                salutationselectedOptions = newItem,
                salutationDropDownexpandedState = false
            )
        }
    }

    fun onGenderDropDownPressed(newValue:Boolean){
        formState.update { it->
            it.copy(
                genderexpandedState = newValue,
                genderoptionList = genderList
            )
        }
    }
    fun onGenderItemClicked(newItem:String){
        formState.update {it->
            it.copy(
                genderselectedOptions = newItem,
                genderexpandedState = false
            )
        }
    }

    fun onDepartmentDropDownPressed(newValue:Boolean){
        formState.update { it->
            it.copy(
                departmentexpandedState = newValue,
                departmentoptionList = departmentList
            )
        }
    }
    fun onDepartmentItemClicked(newItem:String){
        formState.update{it->
            it.copy(
                departmentselectedOptions =  newItem,
                departmentexpandedState = false
            )
        }
    }

    fun onBloodGroupDropDownPressed(newValue:Boolean){
        formState.update { it->
            it.copy(
                bloodGroupexpandedState = newValue,
                bloodGroupoptionList = bloodGroupList
            )
        }
    }

    fun onBloodGroupItemClicked(newItem:String){
        formState.update { it->
            it.copy(
                bloodGroupselectedOptions = newItem,
                bloodGroupexpandedState = false
            )
        }
    }


    fun onAddressDropDownPressed(newValue:Boolean){
        formState.update { it->
            it.copy(
                addressexpandedState = newValue,
                addressoptionList = addressList
            )
        }
    }

    fun onAddressItemClicked(newItem:String){
        formState.update { it->
            it.copy(
                addressselectedOptions = newItem,
                addressexpandedState = false
            )
        }
    }

    // function to update the date fields
    fun onDateOfBirthChange(newDateOfBirth:String){
        formState.update { it->
            it.copy(
             dateOfBirth = newDateOfBirth
            )
        }
    }

    fun onDateOfJoiningChange(newDateOfJoining:String){
        formState.update { it->
            it.copy(
                dateOfJoining = newDateOfJoining
            )
        }
    }
}