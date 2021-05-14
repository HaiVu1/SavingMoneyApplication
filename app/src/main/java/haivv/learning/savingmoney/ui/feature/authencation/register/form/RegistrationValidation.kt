package haivv.learning.savingmoney.ui.feature.authencation.register.form

data class RegistrationValidation(
    var validateFirstName: Boolean = false,
    var validateLastName: Boolean = false,
    var validateDOB: Boolean = false,
    var validateEmail: Boolean = false,
    var validatePhone: Boolean = false
)