package by.novikov.mn.pastebin_analog.repository;

import by.novikov.mn.pastebin_analog.entity.Paste;
import by.novikov.mn.pastebin_analog.exception.NotFoundEntityException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PasteRepositoryMap implements PastebinRepository {

    // вместо БД
    private final Map<String, Paste> pasteMap = new HashMap<>();

    // получить Paste по хешу
    @Override
    public Paste getByHash(String hash) {
        Paste paste = pasteMap.get(hash);
        if (paste == null) {
            throw new NotFoundEntityException("Paste not found with hash: " + hash);
        }
        return paste;
    }

    @Override
    public List<Paste> getListOfPublicAndAlivePastes(int amount) {
        LocalDateTime now = LocalDateTime.now();
        return pasteMap.values().stream()
                // только с открытым доступом
                .filter(Paste::isPublic)
                // у которых не истек "срок годности"
                .filter(paste -> paste.getLifetime().isAfter(now))
                // сорт. по id в обр. порядке (т.к. в мапе все разбросано и нужно amount последних)
                .sorted(Comparator.comparing(Paste::getId).reversed())
                // оставляем amount последних
                .limit(amount)
                // собираем в List
                .collect(Collectors.toList());
    }

    @Override
    public void create(Paste paste) {
        pasteMap.put(paste.getHash(), paste);
    }
}
