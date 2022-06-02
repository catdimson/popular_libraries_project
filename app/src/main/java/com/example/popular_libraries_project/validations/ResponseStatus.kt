package com.example.popular_libraries_project.validations

enum class ResponseStatus(
    private val code: Int,
    private val text: String,
    private val isSuccess: Boolean
) {

    ERROR(0, "Неизвестная ошибка", false),

    LOGIN_SUCCESS(1, "", true),
    LOGIN_ERROR(2, "Логин или пароль неверен", false),
    LOGIN_USER_NOT_ACTIVE(3, "Пользователь не активен", false);

    @JvmName("getCode1")
    private fun getCode(): Int {
        return code
    }

    @JvmName("getText1")
    public fun getText(): String {
        return text
    }

    @JvmName("isSuccess1")
    public fun isSuccess(): Boolean {
        return isSuccess
    }

    companion object {
        fun getStatusByCode(code: Int): ResponseStatus {
            for (status in values()) {
                if (status.code == code) {
                    return status
                }
            }
            return ERROR
        }
    }
}