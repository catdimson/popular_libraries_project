package com.example.popular_libraries_project.validations

class ValidationResult {

    private var validations: MutableList<Validation> = mutableListOf()
    private var generalStatus: Boolean = true
    private var generalTextError: String = ""

    fun add(validation: Validation) {
        validations.add(validation)
        checkValidations()
        generateGeneralTextError()
    }

    fun getResultValidations() = validations

    fun isValid() = generalStatus

    fun getTextError() = generalTextError

    private fun generateGeneralTextError() {
        generalTextError = ""
        validations.forEach {
            if (!it.ivValid) {
                generalTextError += it.textError + "\n"
            }
        }
    }

    private fun checkValidations(): Boolean {
        validations.forEach {
            if (!it.ivValid) {
                generalStatus = false
                return@forEach
            }
        }
        return true
    }
}