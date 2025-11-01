package com.example.movieticks;

public class UserInfo {



        String userName,userMobileNumber,user_EmailId,userLocation,userUID;
        public UserInfo(){

}

public UserInfo(String userName,String userMobileNumber,String user_EmailId,String userLocation,String userUid){
            this.userName=userName;
            this.userMobileNumber=userMobileNumber;
            this.user_EmailId=user_EmailId;
            this.userLocation=userLocation;
            this.userUID=userUid;
}


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

    public String getUser_EmailId() {
        return user_EmailId;
    }

    public void setUser_EmailId(String user_EmailId) {
        this.user_EmailId = user_EmailId;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
