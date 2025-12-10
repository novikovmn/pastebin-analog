package by.novikov.mn.pastebin_analog.service;

import by.novikov.mn.pastebin_analog.dto.PasteRequestDto;
import by.novikov.mn.pastebin_analog.dto.PasteResponseDto;
import by.novikov.mn.pastebin_analog.dto.PasteUrlResponseDto;
import by.novikov.mn.pastebin_analog.dto.Status;
import by.novikov.mn.pastebin_analog.entity.Paste;
import by.novikov.mn.pastebin_analog.repository.PastebinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PastebinServiceImpl implements PastebinService {

    private final PastebinRepository pastebinRepository;
    private int idGenerator = 0;

    @Value("${app.host}")
    private String host;

    @Value("${app.public_list_size}")
    private int publicListSize;

    @Override
    public PasteResponseDto getByHash(String hash) {
        Paste paste = pastebinRepository.getByHash(hash);
        return new PasteResponseDto(
                paste.getData(),
                paste.isPublic()
        );
    }

    @Override
    public List<PasteResponseDto> getListOfPublicAndAlivePastes() {
        List<Paste> pastes = pastebinRepository.getListOfPublicAndAlivePastes(publicListSize);
        return pastes.stream()
                .map(paste -> new PasteResponseDto(paste.getData(), paste.isPublic()))
                .collect(Collectors.toList());

    }

    @Override
    public PasteUrlResponseDto create(PasteRequestDto pasteRequestDto) {
        // задаем id
        int id = generateId();
        // собираем entity
        Paste paste = new Paste();
        paste.setId(id);
        paste.setData(pasteRequestDto.getData());
        paste.setHash(Integer.toHexString(id));
        paste.setPublic(pasteRequestDto.getStatus() == Status.PUBLIC);
        paste.setLifetime(LocalDateTime.now().plusSeconds(pasteRequestDto.getExpirationTimeInSeconds()));
        // сохраним в бд/мапу
        pastebinRepository.create(paste);
        // вернем url
        return new PasteUrlResponseDto(host + "/" + paste.getHash());
    }

    private int generateId() {
        return idGenerator++;
    }
}
