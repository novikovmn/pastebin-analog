package by.novikov.mn.pastebin_analog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paste {

    private int id;
    private String data;
    private String hash;
    private LocalDateTime lifetime;
    private boolean isPublic;

}
