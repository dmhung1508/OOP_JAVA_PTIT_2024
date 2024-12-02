
package com.mycompany.model;
import java.io.*;
import java.util.*;

public class Cinema {
    private String name;
    private List<String> showHours;

    public Cinema(String name, List<String> showHours) {
        this.name = name;
        this.showHours = showHours;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getShowHours() {
        return showHours;
    }

    public void setShowHours(List<String> showHours) {
        this.showHours = showHours;
    }
}

