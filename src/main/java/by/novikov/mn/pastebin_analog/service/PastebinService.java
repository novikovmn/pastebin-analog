package by.novikov.mn.pastebin_analog.service;

import by.novikov.mn.pastebin_analog.dto.PasteRequestDto;
import by.novikov.mn.pastebin_analog.dto.PasteResponseDto;
import by.novikov.mn.pastebin_analog.dto.PasteUrlResponseDto;

import java.util.List;

public interface PastebinService {

    PasteResponseDto getByHash(String hash);
    List<PasteResponseDto> getListOfPublicAndAlivePastes();
    PasteUrlResponseDto create (PasteRequestDto pasteRequestDto);
}
