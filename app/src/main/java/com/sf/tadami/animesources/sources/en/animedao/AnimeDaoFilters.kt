package com.sf.tadami.animesources.sources.en.animedao

import com.sf.tadami.App
import com.sf.tadami.R
import com.sf.tadami.network.api.model.AnimeFilter
import com.sf.tadami.network.api.model.AnimeFilterList

object AnimeDaoFilters {
    open class QueryPartFilter(
        displayName: String,
        private val vals: Array<Pair<String, String>>,
    ) : AnimeFilter.Select(
        displayName,
        vals.map { it.first }.toTypedArray(),
    ) {
        fun toQueryPart() = vals[state].second
    }

    open class CheckBoxFilterList(name: String, values: List<CheckBox>) : AnimeFilter.CheckBoxGroup(name, values)
    private class CheckBoxVal(name: String, state: Boolean = false) : AnimeFilter.CheckBox(name, state)

    private inline fun <reified R> AnimeFilterList.asQueryPart(): String {
        return this.filterIsInstance<R>().joinToString("") {
            (it as QueryPartFilter).toQueryPart()
        }
    }

    private inline fun <reified R> AnimeFilterList.getFirst(): R {
        return this.filterIsInstance<R>().first()
    }

    private inline fun <reified R> AnimeFilterList.parseCheckbox(
        options: Array<Pair<String, String>>,
        name: String,
    ): String {
        return (this.getFirst<R>() as CheckBoxFilterList).state
            .mapNotNull { checkbox ->
                if (checkbox.state) {
                    options.find { it.first == checkbox.name }!!.second
                } else {
                    null
                }
            }.joinToString("&$name[]=").let {
                if (it.isBlank()) {
                    ""
                } else {
                    "$name[]=$it"
                }
            }
    }

    class GenreFilter : CheckBoxFilterList(
        "Genre",
        AnimeDaoFiltersData.GENRE.map { CheckBoxVal(it.first, false) },
    )

    class RatingFilter : CheckBoxFilterList(
        "Rating",
        AnimeDaoFiltersData.RATING.map { CheckBoxVal(it.first, false) },
    )

    class LetterFilter : CheckBoxFilterList(
        "Letter",
        AnimeDaoFiltersData.LETTER.map { CheckBoxVal(it.first, false) },
    )

    class YearFilter : CheckBoxFilterList(
        "Year",
        AnimeDaoFiltersData.YEAR.map { CheckBoxVal(it.first, false) },
    )

    class StatusFilter : QueryPartFilter("Status", AnimeDaoFiltersData.STATUS)

    class ScoreFilter : CheckBoxFilterList(
        "Score",
        AnimeDaoFiltersData.SCORE.map { CheckBoxVal(it.first, false) },
    )

    class OrderFilter : QueryPartFilter("Order", AnimeDaoFiltersData.ORDER)

    val FILTER_LIST get() = AnimeFilterList(
        AnimeFilter.Header(App.getAppContext()?.getString(R.string.discover_search_ingores_filters) ?: "Search ignores filters"),
        GenreFilter(),
        RatingFilter(),
        LetterFilter(),
        YearFilter(),
        ScoreFilter(),
        StatusFilter(),
        OrderFilter(),
    )

    data class FilterSearchParams(
        val genre: String = "",
        val rating: String = "",
        val letter: String = "",
        val year: String = "",
        val status: String = "",
        val score: String = "",
        val order: String = "",

    ){
        fun isBlank(): Boolean {
            return genre.isBlank() &&
                    rating.isBlank() &&
                    letter.isBlank() &&
                    year.isBlank() &&
                    status.isBlank() &&
                    score.isBlank() &&
                    order.isBlank()
        }
    }

    internal fun getSearchParameters(filters: AnimeFilterList): FilterSearchParams {
        if (filters.isEmpty()) return FilterSearchParams()

        return FilterSearchParams(
            filters.parseCheckbox<GenreFilter>(AnimeDaoFiltersData.GENRE, "genres"),
            filters.parseCheckbox<RatingFilter>(AnimeDaoFiltersData.RATING, "ratings"),
            filters.parseCheckbox<LetterFilter>(AnimeDaoFiltersData.LETTER, "letters"),
            filters.parseCheckbox<YearFilter>(AnimeDaoFiltersData.YEAR, "years"),
            filters.asQueryPart<StatusFilter>(),
            filters.parseCheckbox<ScoreFilter>(AnimeDaoFiltersData.SCORE, "score"),
            filters.asQueryPart<OrderFilter>(),
        )
    }

    private object AnimeDaoFiltersData {
        val GENRE = arrayOf(
            Pair("Action", "Action"),
            Pair("Adventure", "Adventure"),
            Pair("Chinese", "Chinese"),
            Pair("Comedy", "Comedy"),
            Pair("Detective", "Detective"),
            Pair("Drama", "Drama"),
            Pair("Ecchi", "Ecchi"),
            Pair("Fantasy", "Fantasy"),
            Pair("Gourmet", "Gourmet"),
            Pair("Harem", "Harem"),
            Pair("High Stakes Game", "High Stakes Game"),
            Pair("Historical", "Historical"),
            Pair("Horror", "Horror"),
            Pair("Isekai", "Isekai"),
            Pair("Iyashikei", "Iyashikei"),
            Pair("Josei", "Josei"),
            Pair("Kids", "Kids"),
            Pair("Martial Arts", "Martial Arts"),
            Pair("Mecha", "Mecha"),
            Pair("Military", "Military"),
            Pair("Music", "Music"),
            Pair("Mystery", "Mystery"),
            Pair("Mythology", "Mythology"),
            Pair("Parody", "Parody"),
            Pair("Psychological", "Psychological"),
            Pair("Racing", "Racing"),
            Pair("Reincarnation", "Reincarnation"),
            Pair("Romance", "Romance"),
            Pair("Samurai", "Samurai"),
            Pair("School", "School"),
            Pair("Sci-Fi", "Sci-Fi"),
            Pair("Seinen", "Seinen"),
            Pair("Shoujo", "Shoujo"),
            Pair("Shoujo Ai", "Shoujo Ai"),
            Pair("Shounen", "Shounen"),
            Pair("Shounen Ai", "Shounen Ai"),
            Pair("Slice of Life", "Slice of Life"),
            Pair("Space", "Space"),
            Pair("Sports", "Sports"),
            Pair("Strategy Game", "Strategy Game"),
            Pair("Super Power", "Super Power"),
            Pair("Supernatural", "Supernatural"),
            Pair("Survival", "Survival"),
            Pair("Suspense", "Suspense"),
            Pair("Team Sports", "Team Sports"),
            Pair("Time Travel", "Time Travel"),
            Pair("Vampire", "Vampire"),
            Pair("Video Game", "Video Game"),
        )

        val RATING = arrayOf(
            Pair("G - All Ages", "all-ages"),
            Pair("PG - Children", "children"),
            Pair("PG-13 - Teens 13 or older", "pg13"),
            Pair("R - 17+ (violence & profanity)", "r17"),
            Pair("R+ - Mild Nudity", "rplus"),
        )

        val LETTER = arrayOf(
            Pair("A", "A"),
            Pair("B", "B"),
            Pair("C", "C"),
            Pair("D", "D"),
            Pair("E", "E"),
            Pair("F", "F"),
            Pair("G", "G"),
            Pair("H", "H"),
            Pair("I", "I"),
            Pair("J", "J"),
            Pair("K", "K"),
            Pair("L", "L"),
            Pair("M", "M"),
            Pair("N", "N"),
            Pair("O", "O"),
            Pair("P", "P"),
            Pair("Q", "Q"),
            Pair("R", "R"),
            Pair("S", "S"),
            Pair("T", "T"),
            Pair("U", "U"),
            Pair("V", "V"),
            Pair("W", "W"),
            Pair("X", "X"),
            Pair("Y", "Y"),
            Pair("Z", "Z"),
        )

        val YEAR = arrayOf(
            Pair("2023", "2023"),
            Pair("2022", "2022"),
            Pair("2021", "2021"),
            Pair("2020", "2020"),
            Pair("2019", "2019"),
            Pair("2018", "2018"),
            Pair("2017", "2017"),
            Pair("2016", "2016"),
            Pair("2015", "2015"),
            Pair("2014", "2014"),
            Pair("2013", "2013"),
            Pair("2012", "2012"),
            Pair("2011", "2011"),
            Pair("2010", "2010"),
            Pair("2009", "2009"),
            Pair("2008", "2008"),
            Pair("2007", "2007"),
            Pair("2006", "2006"),
            Pair("2005", "2005"),
            Pair("2004", "2004"),
            Pair("2003", "2003"),
            Pair("2002", "2002"),
            Pair("2001", "2001"),
            Pair("2000", "2000"),
            Pair("1990 - 1999", "1990"),
            Pair("1980 - 1989", "1980"),
            Pair("1970 - 1979", "1970"),
            Pair("1960 - 1969", "1960"),
        )

        val STATUS = arrayOf(
            Pair(App.getAppContext()?.getString(R.string.discover_search_screen_filters_group_selected_text) ?: "select",""),
            Pair("Ongoing", "Ongoing"),
            Pair("Completed", "Completed"),
        )

        val SCORE = arrayOf(
            Pair("Outstanding (9+)", "outstanding"),
            Pair("Excellent (8+)", "excellent"),
            Pair("Very Good (7+)", "verygood"),
            Pair("Good (6+)", "good"),
            Pair("Average (5+)", "average"),
            Pair("Poor (4+)", "poor"),
            Pair("Bad (3+)", "bad"),
            Pair("Dont Watch (2+)", "dontwatch"),
        )

        val ORDER = arrayOf(
            Pair(App.getAppContext()?.getString(R.string.discover_search_screen_filters_group_selected_text) ?: "select",""),
            Pair("Name A -> Z", "nameaz"),
            Pair("Name Z -> A", "nameza"),
            Pair("Date New -> Old", "datenewold"),
            Pair("Date Old -> New", "dateoldnew"),
            Pair("Score", "score"),
            Pair("Most Watched", "mostwatched"),
        )
    }
}