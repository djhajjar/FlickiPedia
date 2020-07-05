package com.flickipedia.sql;

import javafx.scene.control.TextArea;

/**
 * Basic logging class to keep track of errors.
 */
public class Logger {
    private TextArea area;
    private static Logger instance;

    public static void init(TextArea area) {
        instance = new Logger(area);
    }

    public Logger(TextArea area) {
        this.area = area;
    }

    public Logger() {
        this.area = null;
    }

    public void clear() {
        try {
            this.area.clear();
        } catch (Exception e) {
        }
    }

    public void log(String s) {
        try {
            this.area.appendText(s);

            if (!s.endsWith("\n"))
                this.area.appendText("\n");
        } catch (Exception e) {
        }
    }

    public String getLog() {
        try {
            return this.area.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static Logger getInstance() {
        if(instance == null)
            instance = new Logger();
        
        return instance;
    }
}
