package by.novikov.mn.pastebin_analog.controller;

import by.novikov.mn.pastebin_analog.dto.PasteRequestDto;
import by.novikov.mn.pastebin_analog.dto.PasteResponseDto;
import by.novikov.mn.pastebin_analog.dto.PasteUrlResponseDto;
import by.novikov.mn.pastebin_analog.service.PastebinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PastebinController {

    private final PastebinService pastebinService;

   @GetMapping("/")
    public List<PasteResponseDto> getLatestPublicPastes() {
        return pastebinService.getListOfPublicAndAlivePastes();
    }

    @GetMapping("/{hash}")
    public PasteResponseDto getByHash(@PathVariable("hash") String hash) {
       return pastebinService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteUrlResponseDto addPaste(@RequestBody PasteRequestDto pasteRequestDto) {
        return pastebinService.create(pasteRequestDto);
    }
}
