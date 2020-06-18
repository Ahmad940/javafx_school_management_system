package schoolmanagement.java.models

class Users {
    var id: String? = null
    var userName: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var mobileNumber: String? = null
    var role: String? = null
    var password: String? = null

    constructor() {}
    constructor(id: String?, userName: String?, firstName: String?, lastName: String?, email: String?, mobileNumber: String?, role: String?, password: String?) {
        this.id = id
        this.userName = userName
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.mobileNumber = mobileNumber
        this.role = role
        this.password = password
    }

    override fun toString(): String {
        return "Users{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}'
    }
}