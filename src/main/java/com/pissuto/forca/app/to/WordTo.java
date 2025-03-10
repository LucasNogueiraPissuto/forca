package com.pissuto.forca.app.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordTo {

    @JsonProperty("palavra")
    private String palavra;

    @JsonProperty("dicas")
    private List<String> dicas;
}
