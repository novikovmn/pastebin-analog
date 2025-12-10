package by.novikov.mn.pastebin_analog.repository;

import by.novikov.mn.pastebin_analog.dto.PasteRequestDto;
import by.novikov.mn.pastebin_analog.entity.Paste;

import java.util.List;

public interface PastebinRepository {

    Paste getByHash(String hash);
    List<Paste> getListOfPublicAndAlivePastes(int amount);
    void create (Paste paste);
}
