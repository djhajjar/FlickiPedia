package com.flickipedia.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.flickipedia.util.Util;

public class SQLHelper {
    private Connection con = null;

    public SQLHelper(String url, String user, String pass, String driver) {
        try {
            Class.forName(driver);

            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            Logger.getInstance().log(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            Logger.getInstance().log(e.getMessage());
            e.printStackTrace();
        }
    }

    public String queryAll() {
        return queryMovies(true, null, null, 0, 0, null, null, null, null);
    }

    public String queryMovies(boolean everything, String name, String genre, int start, int end, String actor,
            String writer, String director, String country) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String returnMsg = "";

        try {
            String query = "SELECT MOVIE.TitleId, MOVIE.Country, MOVIE.AgeRating, MOVIE.Day, MOVIE.Month, "
                    + "MOVIE.Year, TITLE.Name, PLAYING_AT.TheaterName, PLAYING_AT.TheaterZip, STREAMING_FROM.ServiceName, "
                    + "REVIEW.StarRating, REVIEW.Description, USER.Email, PARTICIPANT.FirstName, "
                    + "PARTICIPANT.MiddleName, PARTICIPANT.LastName, BELONGS_TO.GenreName, TRAILER.TrailerId, "
                    + "SHOOT_LOCATION.City, SHOOT_LOCATION.State, SHOOT_LOCATION.Country "
                    + "FROM MOVIE, TITLE, PLAYING_AT, STREAMING_FROM, REVIEW, PARTICIPATES_IN, PARTICIPANT, BELONGS_TO, "
                    + "TRAILER, SHOOT_LOCATION, USER "
                    + "WHERE MOVIE.TitleId = TITLE.TitleId AND MOVIE.TitleId = PLAYING_AT.TitleId AND "
                    + "MOVIE.TitleId = STREAMING_FROM.TitleId AND MOVIE.TitleId = REVIEW.TitleId "
                    + "AND MOVIE.TitleId = PARTICIPATES_IN.TitleId AND PARTICIPATES_IN.ParticipantId = PARTICIPANT.ParticipantId "
                    + "AND MOVIE.TitleId = BELONGS_TO.TitleId AND MOVIE.TitleId = TRAILER.TitleId "
                    + "AND MOVIE.TitleId = SHOOT_LOCATION.TitleId AND REVIEW.UserId = USER.UserId ";

            if (!everything) {
                if (name != null) {
                    query += "MOVIE.name = " + name + " ";
                }

                if (genre != null) {
                    query += "BELONGS_TO.GenreName = " + genre + " ";
                }

                if (start != 0 && end != 0) {
                    if (start == end) {
                        query += "MOVIE.Year = ? ";
                    } else {
                        query += "(";

                        for (int i = start; i < end; i++) {
                            query += "MOVIE.Year = ";
                            query += Integer.toString(i);
                            query += " OR ";
                        }

                        query += "MOVIE.Year = " + Integer.toString(end) + ") ";
                    }
                }

                query += this.queryParticipants(actor, writer, director);

                if (country != null) {
                    query += "MOVIE.Country = \"";
                    query += country + "\"";
                }
            }

            if (query.endsWith(" ")) {
                query = query.substring(0, query.length() - 1);
            }

            st = con.prepareStatement(query);
            rs = st.executeQuery();

            if (rs.next()) {
                do {
                    returnMsg += rs.getString(7);
                    returnMsg += "\n";
                } while (rs.next());
            } else {
                returnMsg = "No Movies with that filter found!";
            }
        } catch (SQLException e) {
            Logger.getInstance().log(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    Logger.getInstance().log(e.getMessage());
                }
            }

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    Logger.getInstance().log(e.getMessage());
                }
            }
        }

        return returnMsg;
    }

    public String queryTVShow(boolean everything, String name, String genre, int start, int end, String actor,
            String writer, String director, String country) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String returnMsg = "";

        try { // TODO fix query
            String query = "SELECT TV_SHOW.TitleId, TV_SHOW.Country, TITLE.Name, PLAYING_AT.TheaterName,"
                    + "PLAYING_AT.TheaterZip, STREAMING_FROM.ServiceName, STREAMING_FROM.url,"
                    + "REVIEW.StarRating, REVIEW.Description, USER.Email, PARTICIPANT.FirstName, "
                    + "PARTICIPANT.MiddleName, PARTICIPANT.LastName, BELONGS_TO.GenreName, TRAILER.TrailerId, "
                    + "SHOOT_LOCATION.City, SHOOT_LOCATION.State, SHOOT_LOCATION.Country, SEASON.SeasonNumber, EPISODE.SeasonNumber, "
                    + "EPISODE.EpisodeNumber, EPISODE.Name, EPISODE.Year"
                    + "FROM TV_SHOW, TITLE, PLAYING_AT, STREAMING_FROM, REVIEW, PARTICIPATES_IN, PARTICIPANT, BELONGS_TO, "
                    + "TRAILER, SHOOT_LOCATION, USER, SEASON, EPISODE "
                    + "WHERE TV_SHOW.TitleId = TITLE.TitleId AND TV_SHOW.TitleId = PLAYING_AT.TitleId AND "
                    + "TV_SHOW.TitleId = STREAMING_FROM.TitleId AND TV_SHOW.TitleId = REVIEW.TitleId "
                    + "AND TV_SHOW.TitleId = PARTICIPATES_IN.TitleId AND PARTICIPANTS_IN.ParticipantId = PARTICIPANT.ParticipantId "
                    + "AND TV_SHOW.TitleId = BELONGS_TO.TitleId AND TV_SHOW.TitleId = TRAILER.TitleId "
                    + "AND TV_SHOW.TitleId = SHOOT_LOCATION.TitleId REVIEW.UserId = USER.UserId ";

            if (!everything) {
                if (name != null) {
                    query += "TV_SHOW.name = " + name + " ";
                }

                if (genre != null) {
                    query += "TV_SHOW.GenreName = " + genre + " ";
                }

                if (start != 0 && end != 0) {
                    if (start == end) {
                        query += "TV_SHOW.Year = ? ";
                    } else {
                        query += "(";

                        for (int i = start; i < end; i++) {
                            query += "TV_SHOW.Year = ";
                            query += Integer.toString(i);
                            query += " OR ";
                        }

                        query += "TV_SHOW.Year = " + Integer.toString(end) + ") ";
                    }
                }

                query += this.queryParticipants(actor, writer, director);

                if (country != null) {
                    query += "TV_SHOW.Country = \"";
                    query += country + "\"";
                }
            }

            if (query.endsWith(" ")) {
                query = query.substring(0, query.length() - 1);
            }

            st = con.prepareStatement(query);
            rs = st.executeQuery();

            if (rs.next()) {
                // TODO
            } else {
                returnMsg = "No TV Show with that filter found!";
            }
        } catch (SQLException e) {
            Logger.getInstance().log(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    Logger.getInstance().log(e.getMessage());
                }
            }

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    Logger.getInstance().log(e.getMessage());
                }
            }
        }

        return returnMsg;
    }

    private String queryParticipants(String actor, String writer, String director) {
        String query = "";
        ArrayList<Integer> ids = new ArrayList<Integer>();

        PreparedStatement st = null;
        ResultSet rs = null;

        if (actor != null) {
            query = "SELECT TitleId FROM PARTICIPATES_IN, PARTICIPANT "
                    + "WHERE PARTICIPATES_IN.ParticipantId = PARTICIPANT.ParticipantId ";

            try {
                String[] s = actor.split(" ");

                if (s.length == 1) {
                    query += "AND PARTICIPANT.FirstName = " + s[0] + " ";
                    query += "AND PARTICIATES_IN.isActor = 1 ";
                } else if (s.length == 2) {
                    query += "AND PARTICIPANT.FirstName = " + s[0] + " ";
                    query += "AND PARTICIPANT.LastName = " + s[1] + " ";
                    query += "AND PARTICIATES_IN.isActor = 1 ";
                } else if (s.length == 3) {
                    query += "AND PARTICIPANT.FirstName = " + s[0] + " ";
                    query += "AND PARTICIPANT.MiddleName = " + s[1] + " ";
                    query += "AND PARTICIPANT.LastName = " + s[2] + " ";
                    query += "AND PARTICIATES_IN.isActor = 1 ";
                } else {
                    Logger.getInstance().log("Invalid Actor Name, query may return unexpected results.");
                }

                if (query.endsWith(" ")) {
                    query = query.substring(0, query.length() - 1);
                }

                st = this.con.prepareStatement(query);
                rs = st.executeQuery();

                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            } catch (SQLException e) {
                Logger.getInstance().log(e.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        Logger.getInstance().log(e.getMessage());
                    }
                }

                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        Logger.getInstance().log(e.getMessage());
                    }
                }
            }
        }

        st = null;
        rs = null;

        if (writer != null) {
            query = "SELECT TitleId FROM PARTICIPATES_IN, PARTICIPANT "
                    + "WHERE PARTICIPATES_IN.ParticipantId = PARTICIPANT.ParticipantId ";

            try {
                String[] s = writer.split(" ");

                if (s.length == 1) {
                    query += "AND PARTICIPANT.FirstName = " + s[0] + " ";
                    query += "AND PARTICIATES_IN.isWriter = 1 ";
                } else if (s.length == 2) {
                    query += "AND PARTICIPANT.FirstName = " + s[0] + " ";
                    query += "AND PARTICIPANT.LastName = " + s[1] + " ";
                    query += "AND PARTICIATES_IN.isWriter = 1 ";
                } else if (s.length == 3) {
                    query += "AND PARTICIPANT.FirstName = " + s[0] + " ";
                    query += "AND PARTICIPANT.MiddleName = " + s[1] + " ";
                    query += "AND PARTICIPANT.LastName = " + s[2] + " ";
                    query += "AND PARTICIATES_IN.isWriter = 1 ";
                } else {
                    Logger.getInstance().log("Invalid Writer Name, query may return unexpected results.");
                }

                if (query.endsWith(" ")) {
                    query = query.substring(0, query.length() - 1);
                }

                st = this.con.prepareStatement(query);
                rs = st.executeQuery();

                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            } catch (SQLException e) {
                Logger.getInstance().log(e.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        Logger.getInstance().log(e.getMessage());
                    }
                }

                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        Logger.getInstance().log(e.getMessage());
                    }
                }
            }
        }

        st = null;
        rs = null;

        if (director != null) {
            query = "SELECT TitleId FROM PARTICIPATES_IN, PARTICIPANT "
                    + "WHERE PARTICIPATES_IN.ParticipantId = PARTICIPANT.ParticipantId ";

            try {
                String[] s = director.split(" ");

                if (s.length == 1) {
                    query += "AND PARTICIPANT.FirstName = " + s[0] + " ";
                    query += "AND PARTICIATES_IN.isDirector = 1 ";
                } else if (s.length == 2) {
                    query += "AND PARTICIPANT.FirstName = " + s[0] + " ";
                    query += "AND PARTICIPANT.LastName = " + s[1] + " ";
                    query += "AND PARTICIATES_IN.isDirector = 1 ";
                } else if (s.length == 3) {
                    query += "AND PARTICIPANT.FirstName = " + s[0] + " ";
                    query += "AND PARTICIPANT.MiddleName = " + s[1] + " ";
                    query += "AND PARTICIPANT.LastName = " + s[2] + " ";
                    query += "AND PARTICIATES_IN.isDirector = 1 ";
                } else {
                    Logger.getInstance().log("Invalid Director Name, query may return unexpected results.");
                }

                if (query.endsWith(" ")) {
                    query = query.substring(0, query.length() - 1);
                }

                st = this.con.prepareStatement(query);
                rs = st.executeQuery();

                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            } catch (SQLException e) {
                Logger.getInstance().log(e.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        Logger.getInstance().log(e.getMessage());
                    }
                }

                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        Logger.getInstance().log(e.getMessage());
                    }
                }
            }
        }

        String returnQuery = "";

        if (!ids.isEmpty()) {
            ids = Util.removeDuplicates(ids);

            // all but last id
            if (ids.size() > 1) {
                for (int i = 0; i < ids.size() - 1; i++) {
                    returnQuery += "PARTICIPATES_IN.TitleId = ";
                    returnQuery += Integer.toString(ids.get(i));
                    returnQuery += " AND ";
                }
            }

            // last id
            returnQuery += "PARTICIPATES_IN.TitleId = ";
            returnQuery += Integer.toString(ids.get(ids.size() - 1));
        }

        return returnQuery;
    }

    public void exit() {
        if (this.con != null) {
            try {
                this.con.close();
            } catch (SQLException e) {
                System.out.println("Unable to close connection!");
            }
        }
    }
}
