package com.pissuto.forca.app.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WordTo {

    @JsonProperty("palavra")
    private String palavra;

    @JsonProperty("dicas")
    private List<String> dicas;
}
