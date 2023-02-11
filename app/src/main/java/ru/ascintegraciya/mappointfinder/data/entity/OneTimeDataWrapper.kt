package ru.ascintegraciya.mappointfinder.data.entity

class OneTimeDataWrapper<T>(private val content: T) {

    // Из вне разрешён только для чтения.
    var hasBeenHandled = false
        private set

    // Возвращает содержимое и предотвращает его повторное использование.
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    // Возвращает содержимое, даже если оно уже было обработано.
    fun peekContent(): T = content
}