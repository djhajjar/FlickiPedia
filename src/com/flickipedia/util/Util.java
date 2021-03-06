package com.flickipedia.util;

import java.util.ArrayList;

public class Util {

    public static String monthToString(int month) {
        if (month == 1)
            return "Jan";
        else if (month == 2)
            return "Feb";
        else if (month == 3)
            return "Mar";
        else if (month == 4)
            return "Apr";
        else if (month == 5)
            return "May";
        else if (month == 6)
            return "Jun";
        else if (month == 7)
            return "Jul";
        else if (month == 8)
            return "Aug";
        else if (month == 9)
            return "Sep";
        else if (month == 10)
            return "Oct";
        else if (month == 11)
            return "Nov";
        else if (month == 12)
            return "Dec";
        else
            return "Invalid Month";
    }

    public static String formatDate(int month, int day, int year) {
        String date = monthToString(month);
        date += " " + day;
        date += ", " + year;

        return date;
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<T>();

        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        return newList;
    }
}
