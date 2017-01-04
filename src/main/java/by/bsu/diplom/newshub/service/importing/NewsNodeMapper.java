package by.bsu.diplom.newshub.service.importing;

import by.bsu.diplom.newshub.domain.dto.AuthorDto;
import by.bsu.diplom.newshub.domain.dto.NewsDto;
import by.bsu.diplom.newshub.domain.dto.TagDto;
import com.epam.esm.task4.parser.exception.ParsingException;
import com.epam.esm.task4.parser.mapper.NodeMapper;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashSet;
import java.util.Set;

@Component
public class NewsNodeMapper implements NodeMapper<NewsDto> {
    private static final String NEWS = "news";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_BRIEF = "brief";
    private static final String NEWS_TEXT = "text";
    private static final String TAGS = "tags";
    private static final String TAG = "tag";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String AUTHORS = "authors";
    private static final String AUTHOR = "author";
    private static final String AUTHOR_FULL_NAME = "full_name";
    private static final String AUTHOR_ID = "id";

    @Override
    public NewsDto mapNode(XMLStreamReader reader) throws ParsingException {
        try {
            NewsDto newsDto = new NewsDto();
            while (reader.hasNext() && !(reader.isEndElement() && reader.getLocalName().equals(NEWS))) {
                if (reader.next() == XMLStreamConstants.START_ELEMENT) {
                    switch (reader.getLocalName()) {
                        case NEWS_TITLE:
                            newsDto.setTitle(reader.getElementText());
                            break;
                        case NEWS_BRIEF:
                            newsDto.setBrief(reader.getElementText());
                            break;
                        case NEWS_TEXT:
                            newsDto.setContent(reader.getElementText());
                            break;
                        case AUTHORS:
                            newsDto.setAuthors(buildAuthors(reader));
                            break;
                        case TAGS:
                            newsDto.setTags(buildTags(reader));
                            break;
                    }
                }
            }
            return newsDto;
        } catch (XMLStreamException ex) {
            throw new ParsingException("Error while entity parsing", ex);
        }
    }

    private Set<AuthorDto> buildAuthors(XMLStreamReader reader) throws XMLStreamException {
        Set<AuthorDto> authorsSet = new HashSet<>();
        while (reader.hasNext() && !(reader.isEndElement() && reader.getLocalName().equals(AUTHORS))) {
            if (reader.next() == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals(AUTHOR)) {
                AuthorDto authorDto = new AuthorDto();
                while (reader.hasNext() &&
                        !(reader.isEndElement() && reader.getLocalName().equals(AUTHOR))) {
                    if (reader.next() == XMLStreamConstants.START_ELEMENT) {
                        switch (reader.getLocalName()) {
                            case AUTHOR_ID:
                                authorDto.setId(Long.valueOf(reader.getElementText()));
                                break;
                            case AUTHOR_FULL_NAME:
                                authorDto.setFullName(reader.getElementText());
                                break;
                        }
                    }
                }
                authorsSet.add(authorDto);
                reader.next();
            }
        }
        return authorsSet;
    }

    private Set<TagDto> buildTags(XMLStreamReader reader) throws XMLStreamException {
        Set<TagDto> tagsSet = new HashSet<>();
        while (reader.hasNext() && !(reader.isEndElement() && reader.getLocalName().equals(TAGS))) {
            if (reader.next() == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals(TAG)) {
                TagDto tagDto = new TagDto();
                while (reader.hasNext() &&
                        !(reader.isEndElement() && reader.getLocalName().equals(TAG))) {
                    if (reader.next() == XMLStreamConstants.START_ELEMENT) {
                        switch (reader.getLocalName()) {
                            case TAG_ID:
                                tagDto.setId(Long.valueOf(reader.getElementText()));
                                break;
                            case TAG_NAME:
                                tagDto.setName(reader.getElementText());
                                break;
                        }
                    }
                }
                tagsSet.add(tagDto);
                reader.next();
            }
        }
        return tagsSet;
    }
}
