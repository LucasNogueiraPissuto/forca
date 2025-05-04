package com.pissuto.forca.app.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PalpiteTo {

    @JsonProperty("email")
    private String email;

    @JsonProperty("palpite")
    private String palpite;

}
