package com.example.popular_libraries_project.validations

enum class ResponseStatus(
    private val code: Int,
    private val text: String,
    private val isSuccess: Boolean
) {

    ERROR(0, "Неизвестная ошибка", false),

    LOGIN_SUCCESS(1, "", true),
    LOGIN_ERROR(2, "Логин или пароль неверен", false),
    LOGIN_USER_NOT_ACTIVE(3, "Пользователь не активен", false),

    LOGOUT_SUCCESS(11, "", true),

    REGISTRATION_SUCCESS(21, "Регистрация пользователя прошла успешно", true),
    REGISTRATION_NOT_LOGIN(22, "Введите логин", false),
    REGISTRATION_NOT_MORE_3_SYMBOLS(23, "Логин должен содержать больше 3 символов", false),
    REGISTRATION_LOGIN_NOT_ONLY_DIGITS(24, "Логин не должен состоять из одних чисел", false),
    REGISTRATION_PASSWORD_NOT_ONLY_DIGITS(25, "Пароль не должен состоять из одних чисел", false),
    REGISTRATION_PASSWORD_NOT_MELEE_6_SYMBOLS(26, "Пароль должен содержать не меньше 6 символов", false),
    REGISTRATION_DUBLICATE_LOGIN(27, "Пользователь с таким именем уже зарегистрирован в системе", false),

    FORGOT_PASSWORD_SUCCESS(31, "Пароль успешно отправлен на почту", true),
    FORGOT_PASSWORD_NOT_CORRECT_EMAIL(32, "Некорректный адрес электронной почты", false);

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