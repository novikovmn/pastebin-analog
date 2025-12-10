package by.novikov.mn.pastebin_analog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasteResponseDto {
    private String data;
    private boolean isPublic;
}
