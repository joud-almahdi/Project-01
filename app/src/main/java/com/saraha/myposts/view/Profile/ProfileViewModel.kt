package com.saraha.myposts.View.Profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.myposts.model.User
import com.saraha.myposts.repository.UserRepository

class ProfileViewModel: ViewModel() {

    var currentUserLiveData = MutableLiveData<User>()


fun getUserProfileInfo()
{
UserRepository().getUserAccount().observeForever {
    if (it.first != null){ currentUserLiveData.postValue(it.first!!) }

}
}






}