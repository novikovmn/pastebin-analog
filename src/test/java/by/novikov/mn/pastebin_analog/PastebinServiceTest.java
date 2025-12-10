package by.novikov.mn.pastebin_analog;

import by.novikov.mn.pastebin_analog.dto.PasteResponseDto;
import by.novikov.mn.pastebin_analog.entity.Paste;
import by.novikov.mn.pastebin_analog.exception.NotFoundEntityException;
import by.novikov.mn.pastebin_analog.repository.PastebinRepository;
import by.novikov.mn.pastebin_analog.service.PastebinService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PastebinServiceTest {

    @Autowired
    private PastebinService pastebinService;

    @MockitoBean
    private PastebinRepository pastebinRepository;

    @Test
    public void throwExceptionIfHashNotExists(){

        when(pastebinRepository.getByHash(anyString())).thenThrow(NotFoundEntityException.class);

        Assertions.assertThrows(NotFoundEntityException.class,
                () -> pastebinService.getByHash("not_exist_hash"));
    }

    @Test
    public void shouldReturnPasteByHash(){
        Paste paste = new Paste();
        paste.setHash("1");
        paste.setData("data");
        paste.setPublic(true);

        when(pastebinRepository.getByHash("1")).thenReturn(paste);

        PasteResponseDto actual = pastebinService.getByHash("1");
        PasteResponseDto expected = new PasteResponseDto("data", true);

        Assertions.assertEquals(expected, actual);


    }
}
