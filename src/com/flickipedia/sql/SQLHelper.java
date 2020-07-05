package com.flickipedia.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.flickipedia.data.*;
import com.flickipedia.util.Util;

public class SQLHelper {
    private Connection con = null;

    public SQLHelper(String url, String user, String pass, String driver) {
        try {
            Class.forName(driver);

            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String queryMovies(boolean everything, String name, String genre, int start, int end, String actor,
            String writer, String director, String country) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String returnMsg = "";
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            String query = "SELECT MOVIE.TitleId, MOVIE.Country, MOVIE.AgeRating, MOVIE.Day, MOVIE.Month, MOVIE.Year, MOVIE.Duration, TITLE.Name "
                    + "FROM MOVIE, TITLE WHERE MOVIE.TitleId = TITLE.TitleId ";

            if (!everything) {
                if (name != null) {
                    query += "AND TITLE.name = \'" + name + "\' ";
                }

                if (start != 0 && end != 0) {
                    if (start == end) {
                        query += "AND MOVIE.Year = " + Integer.toString(start) + " ";
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

                if (country != null) {
                    query += "AND MOVIE.Country = \'";
                    query += country + "\' ";
                }
            }

            if (query.endsWith(" ")) {
                query = query.substring(0, query.length() - 1);
            }

            st = con.prepareStatement(query);
            rs = st.executeQuery();

            if (rs.next()) {
                do {
                    returnMsg += rs.getString(1);
                    returnMsg += " " + rs.getString(8) + "\n";

                    // construct the movie
                    movies.add(new Movie(rs.getInt(1), rs.getString(8)));
                    movies.get(movies.size() - 1).setCountry(rs.getString(2));
                    movies.get(movies.size() - 1).setAgeRating(rs.getString(3));
                    movies.get(movies.size() - 1).setReleaseDate(rs.getInt(4));
                    movies.get(movies.size() - 1).setReleaseMonth(rs.getInt(5));
                    movies.get(movies.size() - 1).setReleaseYear(rs.getInt(6));
                    movies.get(movies.size() - 1).setDuration(rs.getDouble(7));
                    this.addTheaters(movies.get(movies.size() - 1));
                } while (rs.next());

                // remove all of the movies not with the given participant
                ArrayList<Integer> ids = this.queryParticipants(actor, writer, director);
                for (int i = 0; i < movies.size(); i++) {
                    if (!ids.contains(movies.get(i).getId())) {
                        movies.remove(i);
                    }
                }

                for (Movie movie : movies) {
                    this.addStreams(movie);
                    this.addGenre(movie);
                    this.addShootLocations(movie);
                    this.addTrailers(movie);
                    this.addReviews(movie);
                }
            } else {
                returnMsg = "No Movies with that filter found!\n";
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
        String query = "";
        String returnMessage = "";
        ArrayList<TVShow> shows = new ArrayList<TVShow>();

        try {
            query = "SELECT TV_SHOW.TitleId, TV_SHOW.Country, TITLE.Name FROM TV_SHOW, "
                    + "TITLE WHERE TV_SHOW.TitleId = TITLE.TitleId ";

            if (!everything) {
                if (name != null) {
                    query += "AND TITLE.name = \'" + name + "\' ";
                }

                if (country != null) {
                    query += "AND TV_SHOW.Country = \'";
                    query += country + "\' ";
                }
            }

            st = con.prepareStatement(query);
            rs = st.executeQuery();

            if (rs.next()) {
                do {
                    shows.add(new TVShow(rs.getInt(1), rs.getString(3)));
                    this.addSeasons(shows.get(shows.size() - 1), start, end);
                    
                    if (shows.get(shows.size() - 1).getSeasons().size() == 0) {
                        shows.remove(shows.size() - 1);
                    }
                } while (rs.next());

                ArrayList<Integer> ids = this.queryParticipants(actor, writer, director);
                for (int i = 0; i < shows.size(); i++) {
                    if (!ids.contains(shows.get(i).getId())) {
                        shows.remove(i);
                    }
                }

                for (TVShow show : shows) {
                    this.addStreams(show);
                    this.addGenre(show);
                    this.addShootLocations(show);
                    this.addTrailers(show);
                    this.addReviews(show);
                }
            } else {
                returnMessage = "No TV Shows found with that filter.\n";
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

        return returnMessage;
    }

    private void addSeasons(TVShow show, int start, int end) {
        // TODO
    }

    private void addEpisodes(Season season, int start, int end) {
        // TODO
    }

    private void addGenre(Title title) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement("SELECT GENRE.Name, GENRE.Description FROM GENRE, BELONGS_TO WHERE "
                    + "BELONGS_TO.TitleId = ? AND BELONGS_TO.GenreName = GENRE.Name");
            st.setInt(1, title.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                title.addGenre(new Genre(rs.getString(1), rs.getString(2)));
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

    private void addStreams(Title title) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement(
                    "SELECT STREAM_SERVICE.Name, STREAM_SERVICE.URL FROM STREAMING_FROM, STREAM_SERVICE WHERE "
                            + "STREAMING_FROM.TitleId = ? AND STREAMING_FROM.ServiceName = STREAM_SERVICE.Name");
            st.setInt(1, title.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                title.addStream(new StreamService(rs.getString(1), rs.getString(2)));
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

    private void addShootLocations(Title title) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement("SELECT SHOOT_LOCATION.City, SHOOT_LOCATION.State, SHOOT_LOCATION.Country "
                    + "FROM SHOOT_LOCATION WHERE SHOOT_LOCATION.TitleId = ?");
            st.setInt(1, title.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                title.addShotAt(new ShotLocation(title, rs.getString(1), rs.getString(2), rs.getString(3)));
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

    private void addTrailers(Title title) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement("SELECT TRAILER.TrailerId, TRAILER.Name, TRAILER.Description, TRAILER.Duration "
                    + "FROM TRAILER WHERE TRAILER.TitleId = ?");
            st.setInt(1, title.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                title.addTrailer(new Trailer(rs.getInt(1), title));
                title.getTrailers().get(title.getTrailers().size() - 1).setName(rs.getString(2));
                title.getTrailers().get(title.getTrailers().size() - 1).setDesc(rs.getString(3));
                title.getTrailers().get(title.getTrailers().size() - 1).setDuration(rs.getDouble(4));
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

    private void addReviews(Title title) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement("SELECT USER.Email, REVIEW.StarRating, REVIEW.Description "
                    + "FROM REVIEW, USER WHERE REVIEW.TitleId = ? AND REVIEW.UserId = USER.UserId");
            st.setInt(1, title.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                title.addReview(new Review(title.getId(), rs.getString(1)));
                title.getReviews().get(title.getReviews().size() - 1).setStarRating(rs.getInt(2));
                title.getReviews().get(title.getReviews().size() - 1).setDescription(rs.getString(3));
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

    private void addTheaters(Movie movie) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement("SELECT THEATER.Name, THEATER.City, THEATER.Street, "
                    + "THEATER.State, THEATER.Country, THEATER.Zip FROM PLAYING_AT, THEATER "
                    + "WHERE PLAYING_AT.TitleId = ? AND PLAYING_AT.TheaterName = THEATER.Name "
                    + "AND PLAYING_AT.TheaterZip = THEATER.ZIP");
            st.setInt(1, movie.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                movie.addTheater(new Theater(rs.getString(1), rs.getString(6)));
                movie.getPlayingAt().get(movie.getPlayingAt().size() - 1).setCity(rs.getString(2));
                movie.getPlayingAt().get(movie.getPlayingAt().size() - 1).setStreet(rs.getString(3));
                movie.getPlayingAt().get(movie.getPlayingAt().size() - 1).setState(rs.getString(4));
                movie.getPlayingAt().get(movie.getPlayingAt().size() - 1).setCountry(rs.getString(5));
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

    private ArrayList<Integer> queryParticipants(String actor, String writer, String director) {
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

        Util.removeDuplicates(ids);
        return ids;
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
