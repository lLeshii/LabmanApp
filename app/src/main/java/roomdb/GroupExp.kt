package roomdb

data class GroupExp(
    var groupName: String?,
    var labAmount: Int?,
    var testAmount: Int?,
    var cwAmount: Int?,
    var students: Array<Student?>?
) {
}