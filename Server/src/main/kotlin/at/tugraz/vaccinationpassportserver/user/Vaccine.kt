package at.tugraz.vaccinationpassportserver.user

data class Vaccine (val name: String, val date: String)
{
    companion object {
        fun from(map: Map<String, String>) = object {
            val name by map
            val date by map

            val data = Vaccine(name, date)
        }.data
    }
}
