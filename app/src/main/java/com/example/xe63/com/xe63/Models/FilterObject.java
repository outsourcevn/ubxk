package com.example.xe63.com.xe63.Models;

import java.util.ArrayList;

/**
 * Created by DatNT on 6/28/2016.
 */
public class FilterObject {
    private String keyWord;
    private ArrayList<String> arrayCandidate;
    public FilterObject(String keyWord, ArrayList<String> arrayCandidate) {
        this.keyWord = keyWord;
        this.arrayCandidate = arrayCandidate;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public ArrayList<String> getArrayCandidate() {
        return arrayCandidate;
    }

    public void setArrayCandidate(ArrayList<String> arrayCandidate) {
        this.arrayCandidate = arrayCandidate;
    }
}
