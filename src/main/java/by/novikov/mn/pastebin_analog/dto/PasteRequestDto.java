package by.novikov.mn.pastebin_analog.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasteRequestDto {
    private String data;
    private long expirationTimeInSeconds;
    private Status status;
}
