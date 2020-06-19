package schoolmanagement.java.models

class Students {
    var id: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var mobileNumber: String? = null
    var email: String? = null
    var location: String? = null
    var gender: String? = null
    var registrationDate: String? = null
    var level: String? = null
    var department: String? = null
    var courseName: String? = null
    var amount: String? = null
    var balance: String? = null

    override fun toString(): String {
        return "Students{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", level='" + level + '\'' +
                ", department='" + department + '\'' +
                ", courseName='" + courseName + '\'' +
                ", amount='" + amount + '\'' +
                ", balance='" + balance + '\'' +
                '}'
    }
}